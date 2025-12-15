package DBConnect;

import Model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static UI.LoginFrame.userid;

public class OrderDao {
    public static final String sqlGetOrder = """
            SELECT  p.[ProName],p.[Price],o.[OrderQuantity],o.[OrderTime]
                FROM [Order] o
                JOIN [Product] p ON p.ProId=o.ProId
                JOIN [User] u ON o.UserID=u.ID
                WHERE u.ID = ?
        """;
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

    public static ArrayList<Order> getOrder() {
        ArrayList<Order> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlGetOrder))
        {
            ps.setInt(1,userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt(3),
                        rs.getString(1),
                        rs.getBigDecimal(2),
                        rs.getTimestamp(4)
                );
                list.add(order);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
