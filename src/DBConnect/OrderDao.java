package DBConnect;

import Model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static UI.LoginFrame.userid;

public class OrderDao {
    public static final String sqlInsertOrder = "INSERT INTO [Order] (UserID, OrderQuantity,ProId) VALUES (?,?,?)";
    public static Boolean insertOrder(Order order) {
         try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlInsertOrder))
         {
                ps.setInt(1, userid);
                ps.setInt(2, order.getOrderQuantity());
                ps.setInt(3, order.getProId());
                int kt = ps.executeUpdate();
                return kt > 0;
         }catch (Exception e) {
             e.printStackTrace();
             return false;
         }
    }
}
