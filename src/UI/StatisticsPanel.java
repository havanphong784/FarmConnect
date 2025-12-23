package UI;

import DBConnect.StatisticsDAO;
import Server.StatisticsServer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private ChartPanel cpRevenue, cpTopProducts, cpProductType, cpStockStatus;
    private JFreeChart fcRevenue, fcTopProducts, fcProductType, fcStockStatus;
    private JPanel pnCharts, pnExpiredProducts;
    private JTable tableExpired;
    private DefaultTableModel modelExpired;

    public StatisticsPanel() {
        this.setBorder(new EmptyBorder(20, 30, 20, 30));
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(UIStyle.colorBg);

        // Panel chứa 4 biểu đồ (2x2)
        pnCharts = new JPanel(new GridLayout(2, 2, 15, 15));
        pnCharts.setBackground(UIStyle.colorBg);

        // 1. Biểu đồ doanh thu theo ngày (Line Chart)
        createRevenueChart();

        // 2. Biểu đồ top sản phẩm bán chạy (Bar Chart)
        createTopProductsChart();

        // 3. Biểu đồ phân bố theo loại (Pie Chart)
        createProductTypeChart();

        // 4. Biểu đồ tình trạng tồn kho (Bar Chart)
        createStockStatusChart();

        this.add(pnCharts, BorderLayout.CENTER);

        // 5. Panel danh sách sản phẩm hết hạn
        createExpiredProductsPanel();
        this.add(pnExpiredProducts, BorderLayout.SOUTH);
    }

    /**
     * Tạo panel danh sách sản phẩm hết hạn
     */
    private void createExpiredProductsPanel() {
        pnExpiredProducts = new JPanel(new BorderLayout(10, 10));
        pnExpiredProducts.setBackground(UIStyle.colorBg);
        pnExpiredProducts.setPreferredSize(new Dimension(0, 200));
        pnExpiredProducts.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Header với icon cảnh báo
        JPanel pnHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnHeader.setBackground(UIStyle.colorBg);
        
        JLabel lblWarning = new JLabel("⚠️");
        lblWarning.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        
        JLabel lblTitle = new JLabel("Sản Phẩm Đã Hết Hạn");
        lblTitle.setFont(UIStyle.font16Bold);
        lblTitle.setForeground(UIStyle.colorRed);
        
        pnHeader.add(lblWarning);
        pnHeader.add(lblTitle);
        pnExpiredProducts.add(pnHeader, BorderLayout.NORTH);

        // Bảng danh sách sản phẩm hết hạn
        String[] columns = {"Tên Sản Phẩm", "Số Lượng", "Đơn Vị", "Ngày Hết Hạn"};
        Object[][] data = StatisticsDAO.getExpiredProducts();
        
        modelExpired = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableExpired = new JTable(modelExpired);
        tableExpired.setFont(UIStyle.font14);
        tableExpired.setBackground(UIStyle.colorBg);
        tableExpired.setRowHeight(35);
        tableExpired.setShowVerticalLines(false);
        tableExpired.getTableHeader().setBackground(new Color(255, 235, 235));
        tableExpired.getTableHeader().setFont(UIStyle.font14);
        tableExpired.getTableHeader().setForeground(UIStyle.colorRed);
        tableExpired.setSelectionBackground(new Color(255, 220, 220));
        tableExpired.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(tableExpired);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIStyle.colorBg);
        
        // Hiển thị message nếu không có sản phẩm hết hạn
        if (data.length == 0) {
            JLabel lblNoData = new JLabel("✓ Không có sản phẩm nào hết hạn", JLabel.CENTER);
            lblNoData.setFont(UIStyle.font16);
            lblNoData.setForeground(new Color(52, 168, 83)); // Green
            pnExpiredProducts.add(lblNoData, BorderLayout.CENTER);
        } else {
            pnExpiredProducts.add(scrollPane, BorderLayout.CENTER);
            
            // Hiển thị số lượng sản phẩm hết hạn
            JLabel lblCount = new JLabel("Tổng: " + data.length + " sản phẩm hết hạn");
            lblCount.setFont(UIStyle.font14);
            lblCount.setForeground(UIStyle.colorRed);
            lblCount.setBorder(new EmptyBorder(5, 5, 0, 0));
            pnExpiredProducts.add(lblCount, BorderLayout.SOUTH);
        }
    }

    /**
     * Biểu đồ 1: Doanh thu theo ngày (7 ngày gần nhất)
     */
    private void createRevenueChart() {
        DefaultCategoryDataset dataset = StatisticsServer.createRevenueDataset();

        fcRevenue = ChartFactory.createLineChart(
                "Doanh Thu 7 Ngày Gần Nhất",
                "Ngày",
                "Doanh thu (VNĐ)",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        // Tùy chỉnh style
        customizeChart(fcRevenue);
        CategoryPlot plot = fcRevenue.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(66, 133, 244)); // Google Blue
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesShapesVisible(0, true);

        cpRevenue = createChartPanel(fcRevenue);
        pnCharts.add(cpRevenue);
    }

    /**
     * Biểu đồ 2: Top 5 sản phẩm bán chạy
     */
    private void createTopProductsChart() {
        DefaultCategoryDataset dataset = StatisticsServer.createTopProductsDataset(5);

        fcTopProducts = ChartFactory.createBarChart(
                "Top 5 Sản Phẩm Bán Chạy",
                "Sản phẩm",
                "Số lượng bán",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        // Tùy chỉnh style
        customizeChart(fcTopProducts);
        CategoryPlot plot = fcTopProducts.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(52, 168, 83)); // Google Green
        renderer.setDrawBarOutline(false);

        cpTopProducts = createChartPanel(fcTopProducts);
        pnCharts.add(cpTopProducts);
    }

    /**
     * Biểu đồ 3: Phân bố sản phẩm theo loại
     */
    private void createProductTypeChart() {
        DefaultPieDataset dataset = StatisticsServer.createProductTypeDataset();

        fcProductType = ChartFactory.createPieChart(
                "Phân Bố Sản Phẩm Theo Loại",
                dataset,
                true,
                true,
                false
        );

        // Tùy chỉnh style
        customizeChart(fcProductType);
        PiePlot plot = (PiePlot) fcProductType.getPlot();
        plot.setBackgroundPaint(UIStyle.colorBg);
        plot.setOutlineVisible(false);
        plot.setLabelFont(UIStyle.font14);
        plot.setShadowPaint(null);

        // Màu sắc cho các phần
        Color[] colors = {
                new Color(66, 133, 244),   // Blue
                new Color(52, 168, 83),    // Green
                new Color(251, 188, 4),    // Yellow
                new Color(234, 67, 53),    // Red
                new Color(154, 103, 234),  // Purple
                new Color(255, 138, 101),  // Orange
                new Color(79, 195, 247)    // Light Blue
        };

        int i = 0;
        for (Object key : dataset.getKeys()) {
            plot.setSectionPaint((Comparable<?>) key, colors[i % colors.length]);
            i++;
        }

        cpProductType = createChartPanel(fcProductType);
        pnCharts.add(cpProductType);
    }

    /**
     * Biểu đồ 4: Tình trạng tồn kho (5 sản phẩm tồn kho thấp nhất)
     */
    private void createStockStatusChart() {
        DefaultCategoryDataset dataset = StatisticsServer.createStockStatusDataset(5);

        fcStockStatus = ChartFactory.createBarChart(
                "Sản Phẩm Tồn Kho Thấp",
                "Sản phẩm",
                "Số lượng tồn",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        // Tùy chỉnh style
        customizeChart(fcStockStatus);
        CategoryPlot plot = fcStockStatus.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(234, 67, 53)); // Google Red
        renderer.setDrawBarOutline(false);

        cpStockStatus = createChartPanel(fcStockStatus);
        pnCharts.add(cpStockStatus);
    }

    /**
     * Tùy chỉnh style chung cho chart
     */
    private void customizeChart(JFreeChart chart) {
        chart.setBackgroundPaint(UIStyle.colorBg);

        // Title
        TextTitle title = chart.getTitle();
        title.setFont(UIStyle.font16Bold);
        title.setPaint(UIStyle.colorText);

        // Legend
        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(UIStyle.colorBg);
            chart.getLegend().setItemFont(UIStyle.font14);
        }

        // Plot background
        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(new Color(250, 250, 250));
            plot.setRangeGridlinePaint(new Color(220, 220, 220));
            plot.setDomainGridlinePaint(new Color(220, 220, 220));
            plot.getDomainAxis().setTickLabelFont(UIStyle.font12);
            plot.getDomainAxis().setLabelFont(UIStyle.font14);
            plot.getRangeAxis().setTickLabelFont(UIStyle.font12);
            plot.getRangeAxis().setLabelFont(UIStyle.font14);
        }
    }

    /**
     * Tạo ChartPanel với style
     */
    private ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(UIStyle.colorBg);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    /**
     * Làm mới dữ liệu cho tất cả biểu đồ và bảng
     */
    public void refreshCharts() {
        // Clear pnCharts
        pnCharts.removeAll();
        createRevenueChart();
        createTopProductsChart();
        createProductTypeChart();
        createStockStatusChart();
        
        // Refresh expired products
        this.remove(pnExpiredProducts);
        createExpiredProductsPanel();
        this.add(pnExpiredProducts, BorderLayout.SOUTH);
        
        this.revalidate();
        this.repaint();
    }
}
