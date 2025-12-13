package DBConnect;

import Model.Products;
import DBConnect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static UI.LoginFrame.username;

public class ProductsDAO {

    private static final String sqlBase = """
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

    private static ArrayList<Products> query(String orderBy) {
        ArrayList<Products> list = new ArrayList<>();
        String sql = sqlBase + (orderBy == null || orderBy.isBlank() ? "" : ("\n" + orderBy));

        try (Connection con = DBConnect.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Products pro = new Products(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getBigDecimal(5),
                        rs.getString(6),
                        rs.getInt(7)
                );
                list.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Products> getAll() {
        return query("");
    }

    public static ArrayList<Products> getOderNameASC() {
        return query("ORDER BY p.ProName ASC, p.Price ASC, p.Quantity ASC");
    }

    public static ArrayList<Products> getOderNameDESC() {
        return query("ORDER BY p.ProName DESC, p.Price ASC, p.Quantity ASC");
    }

    public static ArrayList<Products> getOderQuantityASC() {
        return query("ORDER BY p.Quantity ASC, p.ProName ASC, p.Price ASC");
    }

    public static ArrayList<Products> getOderQuantityDESC() {
        return query("ORDER BY p.Quantity DESC, p.ProName ASC, p.Price ASC");
    }

    public static ArrayList<Products> getOderPriceASC() {
        return query("ORDER BY p.Price ASC, p.Quantity ASC, p.ProName ASC");
    }

    public static ArrayList<Products> getOderPriceDESC() {
        return query("ORDER BY p.Price DESC, p.Quantity ASC, p.ProName ASC");
    }
}
