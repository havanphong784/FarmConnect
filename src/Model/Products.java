package Model;

import java.math.BigDecimal;

public class Products {
    private int ProId,Quantity,UserId;
    private String ProName,Des,Unit;
    private BigDecimal price;

    public Products(int proId ,String proName, String des, int quantity, BigDecimal price,String unit ,int userId) {
        ProId = proId;
        Quantity = quantity;
        UserId = userId;
        ProName = proName;
        Des = des;
        Unit = unit;
        this.price = price;
    }

    public Products(String proName, String des, int quantity, BigDecimal price,String unit ,int userId) {
        Quantity = quantity;
        UserId = userId;
        ProName = proName;
        Des = des;
        Unit = unit;
        this.price = price;
    }

    public int getProId() {
        return ProId;
    }

    public void setProId(int proId) {
        ProId = proId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
