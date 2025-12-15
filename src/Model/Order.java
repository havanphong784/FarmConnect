package Model;

import java.sql.Timestamp;

public class Order {
    private int OrderId,UserId,ProId,OrderQuantity;
    private Timestamp OrderTime;
    private String CustomerName,CustomerSdt;

    public Order(int orderId, int userId, int proId, int orderQuantity, Timestamp orderTime, String customerName, String customerSdt) {
        OrderId = orderId;
        UserId = userId;
        ProId = proId;
        OrderQuantity = orderQuantity;
        OrderTime = orderTime;
        CustomerName = customerName;
        CustomerSdt = customerSdt;
    }

    public Order(int userId, int orderQuantity, String customerName, String customerSdt) {
        UserId = userId;
        OrderQuantity = orderQuantity;
        CustomerName = customerName;
        CustomerSdt = customerSdt;
    }

    public Order(int userId, int orderQuantity, int proId) {
        UserId = userId;
        OrderQuantity = orderQuantity;
        ProId = proId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getProId() {
        return ProId;
    }

    public void setProId(int proId) {
        ProId = proId;
    }

    public int getOrderQuantity() {
        return OrderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        OrderQuantity = orderQuantity;
    }

    public Timestamp getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        OrderTime = orderTime;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerSdt() {
        return CustomerSdt;
    }

    public void setCustomerSdt(String customerSdt) {
        CustomerSdt = customerSdt;
    }
}
