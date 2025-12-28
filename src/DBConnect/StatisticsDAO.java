package DBConnect;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static UI.LoginFrame.userid;

/**
 * Statistics DAO - Data queries for charts and reports
 * Updated to use OrderItem table for multi-product orders
 */
public class StatisticsDAO {

    // Revenue by date (last 7 days) - using OrderItem
    private static final String SQL_REVENUE_BY_DATE = """
        SELECT CAST(o.OrderTime AS DATE) AS NgayBan,
               SUM(oi.Quantity * oi.UnitPrice) AS TongDoanhThu
        FROM [Order] o
        JOIN OrderItem oi ON o.OrderId = oi.OrderId
        WHERE o.UserID = ?
        AND o.OrderTime >= DATEADD(DAY, -7, GETDATE())
        GROUP BY CAST(o.OrderTime AS DATE)
        ORDER BY NgayBan ASC
    """;

    // Top selling products - using OrderItem
    private static final String SQL_TOP_SELLING = """
        SELECT TOP(?) p.ProName, SUM(oi.Quantity) AS TongBan
        FROM OrderItem oi
        JOIN [Order] o ON oi.OrderId = o.OrderId
        JOIN Product p ON oi.ProId = p.ProId
        WHERE o.UserID = ?
        GROUP BY p.ProName
        ORDER BY TongBan DESC
    """;

    // Product count by type
    private static final String SQL_PRODUCT_BY_TYPE = """
        SELECT [Type], COUNT(*) AS SoLuong
        FROM Product
        WHERE UserID = ? AND [Type] IS NOT NULL
        GROUP BY [Type]
    """;

    // Low stock products
    private static final String SQL_LOW_STOCK = """
        SELECT TOP(?) ProName, Quantity
        FROM Product
        WHERE UserID = ? AND Quantity > 0
        ORDER BY Quantity ASC
    """;

    // Total revenue - using OrderItem
    private static final String SQL_TOTAL_REVENUE = """
        SELECT COALESCE(SUM(oi.Quantity * oi.UnitPrice), 0) AS TongDoanhThu
        FROM [Order] o
        JOIN OrderItem oi ON o.OrderId = oi.OrderId
        WHERE o.UserID = ?
    """;

    // Total orders
    private static final String SQL_TOTAL_ORDERS = """
        SELECT COUNT(*) AS TongDon
        FROM [Order]
        WHERE UserID = ?
    """;

    // Total products in stock
    private static final String SQL_TOTAL_PRODUCTS = """
        SELECT COUNT(*) AS TongSP
        FROM Product
        WHERE UserID = ? AND Quantity > 0
    """;

    // Expired and expiring soon products (within 3 days)
    private static final String SQL_EXPIRED_PRODUCTS = """
        SELECT ProName, Quantity, Unit, ExpirationDate
        FROM Product
        WHERE UserID = ? AND ExpirationDate <= DATEADD(DAY, 3, GETDATE())
        ORDER BY ExpirationDate ASC
    """;

    /**
     * Get revenue by date (last 7 days)
     */
    public static Map<String, BigDecimal> getRevenueByDate() {
        Map<String, BigDecimal> result = new LinkedHashMap<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_REVENUE_BY_DATE)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String date = rs.getString("NgayBan");
                BigDecimal revenue = rs.getBigDecimal("TongDoanhThu");
                result.put(date, revenue != null ? revenue : BigDecimal.ZERO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get top selling products
     */
    public static Map<String, Integer> getTopSellingProducts(int limit) {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_TOP_SELLING)) {
            ps.setInt(1, limit);
            ps.setInt(2, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("ProName"), rs.getInt("TongBan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get product count by type
     */
    public static Map<String, Integer> getProductCountByType() {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_PRODUCT_BY_TYPE)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                if (type != null && !type.isEmpty()) {
                    result.put(type, rs.getInt("SoLuong"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get low stock products
     */
    public static Map<String, Integer> getLowStockProducts(int limit) {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_LOW_STOCK)) {
            ps.setInt(1, limit);
            ps.setInt(2, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("ProName"), rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get total revenue
     */
    public static BigDecimal getTotalRevenue() {
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_TOTAL_REVENUE)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("TongDoanhThu");
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    /**
     * Get total orders count
     */
    public static int getTotalOrders() {
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_TOTAL_ORDERS)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TongDon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get total products count
     */
    public static int getTotalProducts() {
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_TOTAL_PRODUCTS)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TongSP");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get expired products for table
     */
    public static Object[][] getExpiredProducts() {
        ArrayList<Object[]> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_EXPIRED_PRODUCTS)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            while (rs.next()) {
                String proName = rs.getString("ProName");
                int quantity = rs.getInt("Quantity");
                String unit = rs.getString("Unit");
                Timestamp expDate = rs.getTimestamp("ExpirationDate");
                String formattedDate = expDate != null ?
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(expDate) : "N/A";
                // Determine status: "expired" if past, "expiring" if within 3 days
                String status = "expiring";
                if (expDate != null && expDate.before(now)) {
                    status = "expired";
                }
                list.add(new Object[]{proName, quantity, unit, formattedDate, status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new Object[0][]);
    }
}
