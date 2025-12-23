package DBConnect;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static UI.LoginFrame.userid;

public class StatisticsDAO {

    // Doanh thu theo ngày (7 ngày gần nhất)
    private static final String SQL_REVENUE_BY_DATE = """
        SELECT CAST(o.OrderTime AS DATE) AS NgayBan,
               SUM(o.OrderQuantity * p.Price) AS TongDoanhThu
        FROM [Order] o
        JOIN Product p ON o.ProId = p.ProId
        WHERE o.UserID = ?
        AND o.OrderTime >= DATEADD(DAY, -7, GETDATE())
        GROUP BY CAST(o.OrderTime AS DATE)
        ORDER BY NgayBan ASC
    """;

    // Top sản phẩm bán chạy
    private static final String SQL_TOP_SELLING = """
        SELECT TOP(?) p.ProName, SUM(o.OrderQuantity) AS TongBan
        FROM [Order] o
        JOIN Product p ON o.ProId = p.ProId
        WHERE o.UserID = ?
        GROUP BY p.ProName
        ORDER BY TongBan DESC
    """;

    // Số lượng sản phẩm theo loại
    private static final String SQL_PRODUCT_BY_TYPE = """
        SELECT [Type], COUNT(*) AS SoLuong
        FROM Product
        WHERE UserID = ? AND [Type] IS NOT NULL
        GROUP BY [Type]
    """;

    // Sản phẩm tồn kho thấp
    private static final String SQL_LOW_STOCK = """
        SELECT TOP(?) ProName, Quantity
        FROM Product
        WHERE UserID = ? AND Quantity > 0
        ORDER BY Quantity ASC
    """;

    // Tổng doanh thu
    private static final String SQL_TOTAL_REVENUE = """
        SELECT COALESCE(SUM(o.OrderQuantity * p.Price), 0) AS TongDoanhThu
        FROM [Order] o
        JOIN Product p ON o.ProId = p.ProId
        WHERE o.UserID = ?
    """;

    // Tổng số đơn hàng
    private static final String SQL_TOTAL_ORDERS = """
        SELECT COUNT(*) AS TongDon
        FROM [Order]
        WHERE UserID = ?
    """;

    // Tổng số sản phẩm trong kho
    private static final String SQL_TOTAL_PRODUCTS = """
        SELECT COUNT(*) AS TongSP
        FROM Product
        WHERE UserID = ? AND Quantity > 0
    """;

    // Sản phẩm đã hết hạn
    private static final String SQL_EXPIRED_PRODUCTS = """
        SELECT ProName, Quantity, Unit, ExpirationDate
        FROM Product
        WHERE UserID = ? AND ExpirationDate <= GETDATE()
        ORDER BY ExpirationDate ASC
    """;

    /**
     * Lấy doanh thu theo ngày (7 ngày gần nhất)
     * @return Map<String ngày, BigDecimal doanh thu>
     */
    public static Map<String, BigDecimal> getRevenueByDate() {
        Map<String, BigDecimal> result = new LinkedHashMap<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_REVENUE_BY_DATE)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String date = rs.getString("NgayBan");
                BigDecimal revenue = rs.getBigDecimal("TongDoanhThu");
                result.put(date, revenue != null ? revenue : BigDecimal.ZERO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy top sản phẩm bán chạy
     * @param limit Số lượng sản phẩm
     * @return Map<String tên SP, Integer số lượng bán>
     */
    public static Map<String, Integer> getTopSellingProducts(int limit) {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_TOP_SELLING)) {
            ps.setInt(1, limit);
            ps.setInt(2, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("ProName"), rs.getInt("TongBan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy số lượng sản phẩm theo loại
     * @return Map<String loại, Integer số lượng>
     */
    public static Map<String, Integer> getProductCountByType() {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_PRODUCT_BY_TYPE)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("Type"), rs.getInt("SoLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy sản phẩm tồn kho thấp
     * @param limit Số lượng sản phẩm
     * @return Map<String tên SP, Integer số lượng tồn>
     */
    public static Map<String, Integer> getLowStockProducts(int limit) {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_LOW_STOCK)) {
            ps.setInt(1, limit);
            ps.setInt(2, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.put(rs.getString("ProName"), rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Lấy tổng doanh thu
     */
    public static BigDecimal getTotalRevenue() {
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_TOTAL_REVENUE)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("TongDoanhThu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    /**
     * Lấy tổng số đơn hàng
     */
    public static int getTotalOrders() {
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_TOTAL_ORDERS)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TongDon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy tổng số sản phẩm trong kho
     */
    public static int getTotalProducts() {
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_TOTAL_PRODUCTS)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TongSP");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Lấy danh sách sản phẩm đã hết hạn
     * @return Object[][] data cho JTable [ProName, Quantity, Unit, ExpirationDate]
     */
    public static Object[][] getExpiredProducts() {
        ArrayList<Object[]> list = new ArrayList<>();
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_EXPIRED_PRODUCTS)) {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String proName = rs.getString("ProName");
                int quantity = rs.getInt("Quantity");
                String unit = rs.getString("Unit");
                Timestamp expDate = rs.getTimestamp("ExpirationDate");
                String formattedDate = expDate != null ? 
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(expDate) : "N/A";
                list.add(new Object[]{proName, quantity, unit, formattedDate});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new Object[0][]);
    }
}
