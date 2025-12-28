package Server;

import DBConnect.OrderDao;
import DBConnect.ProductsDAO;
import Model.Order;
import Model.OrderItem;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OrderServer {

    /**
     * Create a new order with multiple items
     * Also updates product quantities in inventory
     * @param userId User ID
     * @param items List of order items
     * @param customerName Customer name
     * @param customerSdt Customer phone number
     */
    public static int createOrder(int userId, List<OrderItem> items, String customerName, String customerSdt) {
        // First verify all items have valid quantities
        for (OrderItem item : items) {
            int currentQty = ProductsDAO.getProductQuantityById(item.getProId());
            if (currentQty < item.getQuantity()) {
                return -1; // Not enough stock
            }
        }

        // Create order with items
        int orderId = OrderDao.createOrderWithItems(userId, items, customerName, customerSdt);

        if (orderId > 0) {
            // Update product quantities
            for (OrderItem item : items) {
                int currentQty = ProductsDAO.getProductQuantityById(item.getProId());
                ProductsServer.updateCell(currentQty - item.getQuantity(), item.getProId());
            }
        }

        return orderId;
    }

    /**
     * Get all orders for table display
     * Returns: OrderId, Thời gian, Khách hàng, Số sản phẩm, Tổng tiền
     */
    public static Object[][] ordersToTable() {
        List<Order> orders = OrderDao.getOrders();
        Object[][] data = new Object[orders.size()][5];
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            // Count items for this order
            List<OrderItem> items = OrderDao.getOrderItems(o.getOrderId());
            data[i] = new Object[]{
                o.getOrderId(),
                o.getOrderTime(),
                o.getCustomerName() != null && !o.getCustomerName().isEmpty() ? o.getCustomerName() : "Khách lẻ",
                items.size(),
                o.getTotalAmount() != null ? o.getTotalAmount() : BigDecimal.ZERO
            };
        }
        return data;
    }

    /**
     * Get order items for detail table
     */
    public static Object[][] orderItemsToTable(int orderId) {
        List<OrderItem> items = OrderDao.getOrderItems(orderId);
        Object[][] data = new Object[items.size()][4];
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            data[i] = new Object[]{
                item.getProName(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.getSubtotal()
            };
        }
        return data;
    }

    /**
     * Get revenue by month for statistics
     */
    public static BigDecimal[] getRevenue() {
        List<Object[]> items = OrderDao.getAllOrderItemsForStats();
        
        BigDecimal[] list = new BigDecimal[12];
        for (int i = 0; i < 12; i++) list[i] = BigDecimal.ZERO;

        Timestamp tJan = Timestamp.valueOf("2025-01-01 00:00:00");
        Timestamp tFeb = Timestamp.valueOf("2025-02-01 00:00:00");
        Timestamp tMar = Timestamp.valueOf("2025-03-01 00:00:00");
        Timestamp tApr = Timestamp.valueOf("2025-04-01 00:00:00");
        Timestamp tMay = Timestamp.valueOf("2025-05-01 00:00:00");
        Timestamp tJun = Timestamp.valueOf("2025-06-01 00:00:00");
        Timestamp tJul = Timestamp.valueOf("2025-07-01 00:00:00");
        Timestamp tAug = Timestamp.valueOf("2025-08-01 00:00:00");
        Timestamp tSep = Timestamp.valueOf("2025-09-01 00:00:00");
        Timestamp tOct = Timestamp.valueOf("2025-10-01 00:00:00");
        Timestamp tNov = Timestamp.valueOf("2025-11-01 00:00:00");
        Timestamp tDec = Timestamp.valueOf("2025-12-01 00:00:00");
        Timestamp tNext = Timestamp.valueOf("2026-01-01 00:00:00");

        for (Object[] item : items) {
            int quantity = (int) item[0];
            BigDecimal price = (BigDecimal) item[1];
            Timestamp time = (Timestamp) item[2];
            BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

            if (!time.before(tJan) && time.before(tFeb)) list[0] = list[0].add(amount);
            else if (!time.before(tFeb) && time.before(tMar)) list[1] = list[1].add(amount);
            else if (!time.before(tMar) && time.before(tApr)) list[2] = list[2].add(amount);
            else if (!time.before(tApr) && time.before(tMay)) list[3] = list[3].add(amount);
            else if (!time.before(tMay) && time.before(tJun)) list[4] = list[4].add(amount);
            else if (!time.before(tJun) && time.before(tJul)) list[5] = list[5].add(amount);
            else if (!time.before(tJul) && time.before(tAug)) list[6] = list[6].add(amount);
            else if (!time.before(tAug) && time.before(tSep)) list[7] = list[7].add(amount);
            else if (!time.before(tSep) && time.before(tOct)) list[8] = list[8].add(amount);
            else if (!time.before(tOct) && time.before(tNov)) list[9] = list[9].add(amount);
            else if (!time.before(tNov) && time.before(tDec)) list[10] = list[10].add(amount);
            else if (!time.before(tDec) && time.before(tNext)) list[11] = list[11].add(amount);
        }

        return list;
    }

    /**
     * Get quantity sold by month for statistics
     */
    public static int[] getQuantity() {
        List<Object[]> items = OrderDao.getAllOrderItemsForStats();

        int[] list = new int[12];
        for (int i = 0; i < 12; i++) list[i] = 0;

        Timestamp tJan = Timestamp.valueOf("2025-01-01 00:00:00");
        Timestamp tFeb = Timestamp.valueOf("2025-02-01 00:00:00");
        Timestamp tMar = Timestamp.valueOf("2025-03-01 00:00:00");
        Timestamp tApr = Timestamp.valueOf("2025-04-01 00:00:00");
        Timestamp tMay = Timestamp.valueOf("2025-05-01 00:00:00");
        Timestamp tJun = Timestamp.valueOf("2025-06-01 00:00:00");
        Timestamp tJul = Timestamp.valueOf("2025-07-01 00:00:00");
        Timestamp tAug = Timestamp.valueOf("2025-08-01 00:00:00");
        Timestamp tSep = Timestamp.valueOf("2025-09-01 00:00:00");
        Timestamp tOct = Timestamp.valueOf("2025-10-01 00:00:00");
        Timestamp tNov = Timestamp.valueOf("2025-11-01 00:00:00");
        Timestamp tDec = Timestamp.valueOf("2025-12-01 00:00:00");
        Timestamp tNext = Timestamp.valueOf("2026-01-01 00:00:00");

        for (Object[] item : items) {
            int quantity = (int) item[0];
            Timestamp time = (Timestamp) item[2];

            if (!time.before(tJan) && time.before(tFeb)) list[0] += quantity;
            else if (!time.before(tFeb) && time.before(tMar)) list[1] += quantity;
            else if (!time.before(tMar) && time.before(tApr)) list[2] += quantity;
            else if (!time.before(tApr) && time.before(tMay)) list[3] += quantity;
            else if (!time.before(tMay) && time.before(tJun)) list[4] += quantity;
            else if (!time.before(tJun) && time.before(tJul)) list[5] += quantity;
            else if (!time.before(tJul) && time.before(tAug)) list[6] += quantity;
            else if (!time.before(tAug) && time.before(tSep)) list[7] += quantity;
            else if (!time.before(tSep) && time.before(tOct)) list[8] += quantity;
            else if (!time.before(tOct) && time.before(tNov)) list[9] += quantity;
            else if (!time.before(tNov) && time.before(tDec)) list[10] += quantity;
            else if (!time.before(tDec) && time.before(tNext)) list[11] += quantity;
        }

        return list;
    }
}
