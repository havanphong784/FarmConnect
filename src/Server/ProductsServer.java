package Server;

import DBConnect.ProductsDAO;
import Model.Products;
import java.math.BigDecimal;
import java.util.ArrayList;
import static DBConnect.ProductsDAO.getProductIdByName;
import static UI.LoginFrame.userid;

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

    public static boolean insertProduct(String name, String desc, int Quantity, BigDecimal price, String unit, int userID) {
        Products products = new Products(name,desc,Quantity,price,unit,userID);
        return ProductsDAO.insertProduct(products);
    }

    public static boolean updateProduct(String name ,String desc,int Quantity, BigDecimal price,String unit) {
        Products products = new Products(getProductIdByName(name),name,desc,Quantity,price,unit,userid);
        return ProductsDAO.update(products);
    }

    public static boolean updateCell(int quantity, int proId) {
        Products products = new Products(quantity, proId);
        return ProductsDAO.updateCell(products);
    }

}
