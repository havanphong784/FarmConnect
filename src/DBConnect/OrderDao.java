package DBConnect;

import Model.Order;
import Model.OrderItem;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static UI.LoginFrame.userid;

public class OrderDao {

    public static final String SQL_GET_ORDERS = """
        SELECT o.OrderId, o.UserID, o.OrderTime, o.CustomerName, o.CustomerSdt, o.TotalAmount,
               (SELECT COUNT(*) FROM OrderItem oi WHERE oi.OrderId = o.OrderId) as ItemCount
        FROM [Order] o
        WHERE o.UserID = ?
        ORDER BY o.OrderTime DESC
        """;

    public static final String SQL_GET_ORDER_ITEMS = """
        SELECT oi.ItemId, oi.OrderId, oi.ProId, p.ProName, oi.Quantity, oi.UnitPrice
        FROM OrderItem oi
        JOIN Product p ON oi.ProId = p.ProId
        WHERE oi.OrderId = ?
        ORDER BY oi.ItemId
        """;

    public static final String SQL_INSERT_ORDER = 
        "INSERT INTO [Order] (UserID, CustomerName, CustomerSdt, TotalAmount) VALUES (?, ?, ?, ?)";

    public static final String SQL_UPDATE_ORDER_TOTAL = 
        "UPDATE [Order] SET TotalAmount = ? WHERE OrderId = ?";

    // Legacy query for backward compatibility with statistics
    public static final String SQL_GET_ORDER_ITEMS_ALL = """
        SELECT oi.Quantity, oi.UnitPrice, o.OrderTime
        FROM OrderItem oi
        JOIN [Order] o ON oi.OrderId = o.OrderId
        WHERE o.UserID = ?
        """;

    /**
     * Create a new order and return the generated OrderId
     */
    public static int createOrder(int userId, String customerName, String customerSdt, BigDecimal totalAmount) {
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setString(2, customerName);
            ps.setString(3, customerSdt);
            ps.setBigDecimal(4, totalAmount);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Create order with items (transaction)
     * @param userId User ID
     * @param items List of order items
     * @param customerName Customer name (can be null or empty)
     * @param customerSdt Customer phone (can be null or empty)
     */
    public static int createOrderWithItems(int userId, List<OrderItem> items, String customerName, String customerSdt) {
        Connection con = null;
        try {
            con = DBConnect.getConnection();
            con.setAutoCommit(false);

            // Calculate total
            BigDecimal total = BigDecimal.ZERO;
            for (OrderItem item : items) {
                total = total.add(item.getSubtotal());
            }

            // Insert order with customer info
            PreparedStatement psOrder = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, userId);
            psOrder.setString(2, customerName != null && !customerName.isEmpty() ? customerName : null);
            psOrder.setString(3, customerSdt != null && !customerSdt.isEmpty() ? customerSdt : null);
            psOrder.setBigDecimal(4, total);
            psOrder.executeUpdate();

            ResultSet rs = psOrder.getGeneratedKeys();
            if (!rs.next()) {
                con.rollback();
                return -1;
            }
            int orderId = rs.getInt(1);

            // Insert order items
            PreparedStatement psItem = con.prepareStatement(OrderItemDao.SQL_INSERT_ITEM);
            for (OrderItem item : items) {
                psItem.setInt(1, orderId);
                psItem.setInt(2, item.getProId());
                psItem.setInt(3, item.getQuantity());
                psItem.setBigDecimal(4, item.getUnitPrice());
                psItem.addBatch();
            }
            psItem.executeBatch();

            con.commit();
            return orderId;

        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return -1;
        } finally {
            if (con != null) {
                try { 
                    con.setAutoCommit(true);
                    con.close(); 
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * Get all orders for current user
     */
    public static List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_GET_ORDERS)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("OrderId"),
                    rs.getInt("UserID"),
                    rs.getTimestamp("OrderTime"),
                    rs.getString("CustomerName"),
                    rs.getString("CustomerSdt"),
                    rs.getBigDecimal("TotalAmount")
                );
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Get order items for a specific order
     */
    public static List<OrderItem> getOrderItems(int orderId) {
        return OrderItemDao.getOrderItemsByOrderId(orderId);
    }

    /**
     * Get all order items for statistics (legacy support)
     */
    public static List<Object[]> getAllOrderItemsForStats() {
        List<Object[]> items = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_GET_ORDER_ITEMS_ALL)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(new Object[]{
                    rs.getInt("Quantity"),
                    rs.getBigDecimal("UnitPrice"),
                    rs.getTimestamp("OrderTime")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
