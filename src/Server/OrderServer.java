package Server;

import DBConnect.OrderDao;
import Model.Order;

import java.math.BigDecimal;
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
}
