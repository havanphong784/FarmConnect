package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Manager
 * Provides methods to get and close SQL Server connections
 */
public class DBConnect {
    
    // Database configuration
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=FarmConnect;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456789";

    /**
     * Get a new database connection
     * Use with try-with-resources for automatic closing
     * 
     * @return Connection object or null if failed
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Close a database connection safely
     * 
     * @param con Connection to close
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
