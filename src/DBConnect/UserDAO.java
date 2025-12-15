package DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public UserDAO() {}
    public static int getUserIdByEmail(String email) {
        String sql = "SELECT ID FROM [User] WHERE Email = ?";
        try (
            Connection con = DBConnect.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ){
            ps.setString(1, email.trim());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ID");
            } else {
                return 0;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
