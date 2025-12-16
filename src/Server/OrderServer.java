package Server;

import DBConnect.OrderDao;
import Model.Order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OrderServer {
    public static Boolean insertOrder(int userId, int orderQuantity,int proId) {
        Order order = new Order(userId, orderQuantity, proId);
        return DBConnect.OrderDao.insertOrder(order);
    }

    public static Object[][] orderToTable() {
        ArrayList<Order> list = OrderDao.getOrder();
        Object[][] dataOrder = new Object[list.size()][5];
        for (int i = 0 ; i < list.size(); ++i ) {
            Order o = list.get(i);
            dataOrder[i] = new Object[] {
                    o.getProName(),
                    o.getPrice(),
                    o.getOrderQuantity(),
                    o.getPrice().multiply(BigDecimal.valueOf(o.getOrderQuantity())),
                    o.getOrderTime()
            };
        }
        return dataOrder;
    }

    public static BigDecimal[] getRevenue() {
        ArrayList<Order> orders = OrderDao.getOrder();

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

        for (Order od : orders) {
            Timestamp time = od.getOrderTime();
            BigDecimal amount = od.getPrice().multiply(BigDecimal.valueOf(od.getOrderQuantity()));

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

    public static int[] getQuantity() {
        ArrayList<Order> orders = OrderDao.getOrder();

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

        for (Order od : orders) {
            Timestamp time = od.getOrderTime();
            int quantity = od.getOrderQuantity();

            if (!time.before(tJan) && time.before(tFeb)) list[0] = list[0] + quantity;
            else if (!time.before(tFeb) && time.before(tMar)) list[1] = list[1] + quantity;
            else if (!time.before(tMar) && time.before(tApr)) list[2] = list[2] + quantity;
            else if (!time.before(tApr) && time.before(tMay)) list[3] = list[3] + quantity;
            else if (!time.before(tMay) && time.before(tJun)) list[4] = list[4] + quantity;
            else if (!time.before(tJun) && time.before(tJul)) list[5] = list[5] + quantity;
            else if (!time.before(tJul) && time.before(tAug)) list[6] = list[6] + quantity;
            else if (!time.before(tAug) && time.before(tSep)) list[7] = list[7] + quantity;
            else if (!time.before(tSep) && time.before(tOct)) list[8] = list[8] + quantity;
            else if (!time.before(tOct) && time.before(tNov)) list[9] = list[9] + quantity;
            else if (!time.before(tNov) && time.before(tDec)) list[10] = list[10] + quantity;
            else if (!time.before(tDec) && time.before(tNext)) list[11] = list[11] + quantity;
        }

        return list;
    }

}
