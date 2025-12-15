package Server;

import Model.Order;

public class OrderServer {
    public static Boolean insertOrder(int userId, int orderQuantity,int proId) {
        Order order = new Order(userId, orderQuantity, proId);
        return DBConnect.OrderDao.insertOrder(order);
    }
}
