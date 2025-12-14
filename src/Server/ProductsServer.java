package Server;

import Model.Products;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductsServer {

    public static Object[][] toTableData(ArrayList<Products> list) {
        Object[][] data = new Object[list.size()][5];

        for (int r = 0; r < list.size(); r++) {
            Products p = list.get(r);

            data[r] = new Object[] {
                    p.getProName(),
                    p.getPrice(),
                    p.getUnit(),
                    p.getQuantity(),
                    p.getDes()
            };
        }
        return data;
    }

    public static boolean convertToProduct(String name,String desc,int Quantity, BigDecimal price,String unit,int userID) {
        Products products = new Products(name,desc,Quantity,price,unit,userID);
        return DBConnect.ProductsDAO.insertProduct(products);
    }
}
