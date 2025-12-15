package Model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private int OrderId,UserId,ProId,OrderQuantity;
    private Timestamp OrderTime;
    private String CustomerName,CustomerSdt,ProName;
    private BigDecimal price;

    public Order(int userId, int orderQuantity, int proId) {
        UserId = userId;
        OrderQuantity = orderQuantity;
        ProId = proId;
    }

    public Order(int orderQuantity, String proName, BigDecimal price, Timestamp orderTime) {
        OrderQuantity = orderQuantity;
        ProName = proName;
        this.price = price;
        OrderTime = orderTime;
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

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
