package DBConnect;

import Model.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static UI.LoginFrame.username;

public class ProductsDAO {

    private static final String sqlSelect = """
        SELECT
            p.ProId,
            p.ProName,
            p.[Des],
            p.Quantity,
            p.Price,
            p.Unit,
            p.UserID
        FROM dbo.Product AS p
        JOIN dbo.[User] AS u    
            ON p.UserID = u.ID
        WHERE u.Email = ?
        """;

    // Search sp
    private static final String sqlSearch = """
        AND p.ProName LIKE ?
        """;

    private static ArrayList<Products> queryAll(String orderBy) {
        ArrayList<Products> list = new ArrayList<>();
        String sql = sqlSelect + (orderBy == null || orderBy.isBlank() ? "" : ("\n" + orderBy));

        try (Connection con = DBConnect.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Products pro = new Products(
                            rs.getInt("ProId"),
                            rs.getString("ProName"),
                            rs.getString("Des"),
                            rs.getInt("Quantity"),
                            rs.getBigDecimal("Price"),
                            rs.getString("Unit"),
                            rs.getInt("UserID")
                    );
                    list.add(pro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static ArrayList<Products> querySearchByName(String keyword, String orderBy) {
        ArrayList<Products> list = new ArrayList<>();
        String sql = sqlSelect + "\n" + sqlSearch
                + (orderBy == null || orderBy.isBlank() ? "" : ("\n" + orderBy));

        try (Connection con = DBConnect.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, "%" + (keyword == null ? "" : keyword.trim()) + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Products pro = new Products(
                            rs.getInt("ProId"),
                            rs.getString("ProName"),
                            rs.getString("Des"),
                            rs.getInt("Quantity"),
                            rs.getBigDecimal("Price"),
                            rs.getString("Unit"),
                            rs.getInt("UserID")
                    );
                    list.add(pro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Products> getAll() {
        return queryAll("");
    }

    public static ArrayList<Products> getOderNameASC() {
        return queryAll("ORDER BY p.ProName ASC, p.Price ASC, p.Quantity ASC");
    }

    public static ArrayList<Products> getOderNameDESC() {
        return queryAll("ORDER BY p.ProName DESC, p.Price ASC, p.Quantity ASC");
    }

    public static ArrayList<Products> getOderQuantityASC() {
        return queryAll("ORDER BY p.Quantity ASC, p.ProName ASC, p.Price ASC");
    }

    public static ArrayList<Products> getOderQuantityDESC() {
        return queryAll("ORDER BY p.Quantity DESC, p.ProName ASC, p.Price ASC");
    }

    public static ArrayList<Products> getOderPriceASC() {
        return queryAll("ORDER BY p.Price ASC, p.Quantity ASC, p.ProName ASC");
    }

    public static ArrayList<Products> getOderPriceDESC() {
        return queryAll("ORDER BY p.Price DESC, p.Quantity ASC, p.ProName ASC");
    }

    public static ArrayList<Products> searchOderNameASC(String keyword) {
        return querySearchByName(keyword, "ORDER BY p.ProName ASC, p.Price ASC, p.Quantity ASC");
    }

    public static ArrayList<Products> searchOderNameDESC(String keyword) {
        return querySearchByName(keyword, "ORDER BY p.ProName DESC, p.Price ASC, p.Quantity ASC");
    }

    public static ArrayList<Products> searchOderQuantityASC(String keyword) {
        return querySearchByName(keyword, "ORDER BY p.Quantity ASC, p.ProName ASC, p.Price ASC");
    }

    public static ArrayList<Products> searchOderQuantityDESC(String keyword) {
        return querySearchByName(keyword, "ORDER BY p.Quantity DESC, p.ProName ASC, p.Price ASC");
    }

    public static ArrayList<Products> searchOderPriceASC(String keyword) {
        return querySearchByName(keyword, "ORDER BY p.Price ASC, p.Quantity ASC, p.ProName ASC");
    }

    public static ArrayList<Products> searchOderPriceDESC(String keyword) {
        return querySearchByName(keyword, "ORDER BY p.Price DESC, p.Quantity ASC, p.ProName ASC");
    }

    public static Boolean insertProduct(Products p) {
        String sql = "INSERT INTO dbo.Product (ProName, [Des], Quantity, Price, Unit, UserID) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, p.getProName());
            st.setString(2, p.getDes());
            st.setInt(3, p.getQuantity());
            st.setBigDecimal(4, p.getPrice());
            st.setString(5, p.getUnit());
            st.setInt(6, p.getUserId());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
