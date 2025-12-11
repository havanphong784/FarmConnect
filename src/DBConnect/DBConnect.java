package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    public DBConnect() {
    }
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://XW:1433;DatabaseName=FarmConnect;encrypt=true;trustServerCertificate=true;";
            String user = "sa";
            String password = "123456789";
            System.out.println("Connecting to database...");
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
