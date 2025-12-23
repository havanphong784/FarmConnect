package Server;

import DBConnect.StatisticsDAO;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.math.BigDecimal;
import java.util.Map;

public class StatisticsServer {

    /**
     * Tạo dataset cho biểu đồ doanh thu theo ngày (LineChart)
     */
    public static DefaultCategoryDataset createRevenueDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, BigDecimal> revenueData = StatisticsDAO.getRevenueByDate();
        
        for (Map.Entry<String, BigDecimal> entry : revenueData.entrySet()) {
            // Cắt ngắn ngày để hiển thị đẹp hơn (VD: 2024-12-23 -> 23/12)
            String date = entry.getKey();
            if (date != null && date.length() >= 10) {
                date = date.substring(8, 10) + "/" + date.substring(5, 7);
            }
            dataset.addValue(entry.getValue(), "Doanh thu", date);
        }
        
        return dataset;
    }

    /**
     * Tạo dataset cho biểu đồ top sản phẩm bán chạy (BarChart)
     */
    public static DefaultCategoryDataset createTopProductsDataset(int limit) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> topProducts = StatisticsDAO.getTopSellingProducts(limit);
        
        for (Map.Entry<String, Integer> entry : topProducts.entrySet()) {
            // Cắt ngắn tên sản phẩm nếu quá dài
            String name = entry.getKey();
            if (name != null && name.length() > 15) {
                name = name.substring(0, 12) + "...";
            }
            dataset.addValue(entry.getValue(), "Số lượng bán", name);
        }
        
        return dataset;
    }

    /**
     * Tạo dataset cho biểu đồ phân bố theo loại (PieChart)
     */
    public static DefaultPieDataset createProductTypeDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> productTypes = StatisticsDAO.getProductCountByType();
        
        for (Map.Entry<String, Integer> entry : productTypes.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        
        return dataset;
    }

    /**
     * Tạo dataset cho biểu đồ tình trạng tồn kho (BarChart)
     */
    public static DefaultCategoryDataset createStockStatusDataset(int limit) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> lowStock = StatisticsDAO.getLowStockProducts(limit);
        
        for (Map.Entry<String, Integer> entry : lowStock.entrySet()) {
            // Cắt ngắn tên sản phẩm nếu quá dài
            String name = entry.getKey();
            if (name != null && name.length() > 15) {
                name = name.substring(0, 12) + "...";
            }
            dataset.addValue(entry.getValue(), "Tồn kho", name);
        }
        
        return dataset;
    }

    /**
     * Lấy tổng doanh thu đã format
     */
    public static String getFormattedTotalRevenue() {
        BigDecimal total = StatisticsDAO.getTotalRevenue();
        return String.format("%,.0f VNĐ", total);
    }

    /**
     * Lấy tổng số đơn hàng
     */
    public static int getTotalOrders() {
        return StatisticsDAO.getTotalOrders();
    }

    /**
     * Lấy tổng số sản phẩm trong kho
     */
    public static int getTotalProducts() {
        return StatisticsDAO.getTotalProducts();
    }
}
