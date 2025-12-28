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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Statistics Panel - Dashboard with charts and reports
 */
public class StatisticsPanel extends JPanel {
    private ChartPanel cpRevenue, cpTopProducts, cpProductType, cpStockStatus;
    private JFreeChart fcRevenue, fcTopProducts, fcProductType, fcStockStatus;
    private JPanel pnCharts, pnBottom;
    private JTable tableExpired;
    private DefaultTableModel modelExpired;
    private JSplitPane splitPane;

    public StatisticsPanel() {
        this.setBorder(new EmptyBorder(15, 15, 15, 15));
        this.setLayout(new BorderLayout());
        this.setBackground(UIStyle.colorBg);

        // Charts section with light blue background
        JPanel pnChartsWrapper = new JPanel(new BorderLayout());
        pnChartsWrapper.setBackground(new Color(240, 248, 255)); // Alice Blue
        pnChartsWrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        pnCharts = new JPanel(new GridLayout(2, 2, 12, 12));
        pnCharts.setBackground(new Color(240, 248, 255)); // Alice Blue
        pnCharts.setOpaque(false);
        pnCharts.setMinimumSize(new Dimension(400, 300));
        pnChartsWrapper.add(pnCharts, BorderLayout.CENTER);

        // Header for charts section
        JLabel lblChartsTitle = new JLabel("  📊 Bieu Do Thong Ke");
        lblChartsTitle.setFont(UIStyle.font18Bold);
        lblChartsTitle.setForeground(new Color(30, 90, 150));
        lblChartsTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        pnChartsWrapper.add(lblChartsTitle, BorderLayout.NORTH);

        // Create charts
        createRevenueChart();
        createTopProductsChart();
        createProductTypeChart();
        createStockStatusChart();

        // Bottom section: Expired products table
        createExpiredProductsPanel();

        // Use JSplitPane for resizable layout (65% charts, 35% table)
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnChartsWrapper, pnBottom);
        splitPane.setResizeWeight(0.65);
        splitPane.setDividerSize(8);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(null);
        splitPane.setBackground(UIStyle.colorBg);

        this.add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Panel: Expired Products Table
     */
    private void createExpiredProductsPanel() {
        pnBottom = new JPanel(new BorderLayout(10, 10));
        pnBottom.setBackground(new Color(255, 248, 245)); // Warm seashell color
        pnBottom.setMinimumSize(new Dimension(300, 180));
        pnBottom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 200, 180), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Header
        JLabel lblTitle = new JLabel("  ⚠️ San Pham Het Han / Sap Het Han");
        lblTitle.setFont(UIStyle.font18Bold);
        lblTitle.setForeground(UIStyle.colorDanger);
        pnBottom.add(lblTitle, BorderLayout.NORTH);

        // Table with hidden status column
        String[] columns = {"Ten San Pham", "So Luong", "Don Vi", "Ngay Het Han", "Status"};
        Object[][] data = StatisticsDAO.getExpiredProducts();

        modelExpired = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableExpired = new JTable(modelExpired);
        UIStyle.styleTable(tableExpired);
        tableExpired.getTableHeader().setBackground(new Color(255, 230, 230));
        tableExpired.getTableHeader().setForeground(UIStyle.colorDanger);
        tableExpired.setSelectionBackground(new Color(255, 220, 220));

        // Hide the status column (column index 4)
        tableExpired.getColumnModel().getColumn(4).setMinWidth(0);
        tableExpired.getColumnModel().getColumn(4).setMaxWidth(0);
        tableExpired.getColumnModel().getColumn(4).setWidth(0);

        // Custom cell renderer for color coding
        Color colorExpired = new Color(220, 53, 69);      // Red - đã hết hạn
        Color colorExpiring = new Color(255, 152, 0);     // Orange - sắp hết hạn
        Color colorExpiredBg = new Color(255, 235, 238);  // Light red background
        Color colorExpiringBg = new Color(255, 243, 224); // Light orange background

        DefaultTableCellRenderer colorRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Get status from hidden column (column 4)
                String status = (String) table.getModel().getValueAt(row, 4);
                
                if (!isSelected) {
                    if ("expired".equals(status)) {
                        c.setBackground(colorExpiredBg);
                        c.setForeground(colorExpired);
                    } else {
                        c.setBackground(colorExpiringBg);
                        c.setForeground(colorExpiring);
                    }
                }
                
                // Center alignment for numeric columns
                if (column == 1 || column == 2) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                
                return c;
            }
        };

        // Apply renderer to visible columns (0-3)
        for (int i = 0; i < 4; i++) {
            tableExpired.getColumnModel().getColumn(i).setCellRenderer(colorRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tableExpired);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        if (data.length == 0) {
            JLabel lblNoData = new JLabel("Khong co san pham het han", JLabel.CENTER);
            lblNoData.setFont(UIStyle.font16);
            lblNoData.setForeground(UIStyle.colorSuccess);
            pnBottom.add(lblNoData, BorderLayout.CENTER);
        } else {
            pnBottom.add(scrollPane, BorderLayout.CENTER);
            
            // Count expired vs expiring
            int expiredCount = 0;
            int expiringCount = 0;
            for (Object[] row : data) {
                if ("expired".equals(row[4])) expiredCount++;
                else expiringCount++;
            }
            
            JPanel pnFooter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
            pnFooter.setBackground(UIStyle.colorBgCard);
            pnFooter.setBorder(new EmptyBorder(10, 0, 0, 0));
            
            JLabel lblExpired = new JLabel("● Da het han: " + expiredCount);
            lblExpired.setFont(UIStyle.font14);
            lblExpired.setForeground(colorExpired);
            
            JLabel lblExpiring = new JLabel("● Sap het han: " + expiringCount);
            lblExpiring.setFont(UIStyle.font14);
            lblExpiring.setForeground(colorExpiring);
            
            JLabel lblTotal = new JLabel("| Tong: " + data.length + " san pham");
            lblTotal.setFont(UIStyle.font14);
            lblTotal.setForeground(UIStyle.colorTextSecondary);
            
            pnFooter.add(lblExpired);
            pnFooter.add(lblExpiring);
            pnFooter.add(lblTotal);
            
            pnBottom.add(pnFooter, BorderLayout.SOUTH);
        }
    }

    /**
     * Chart 1: Revenue by day (7 days)
     */
    private void createRevenueChart() {
        DefaultCategoryDataset dataset = StatisticsServer.createRevenueDataset();

        fcRevenue = ChartFactory.createLineChart(
            "Doanh Thu 7 Ngay",
            "Ngay",
            "VND",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );

        customizeChart(fcRevenue);
        CategoryPlot plot = fcRevenue.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, UIStyle.colorInfo);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesShapesVisible(0, true);

        cpRevenue = createChartPanel(fcRevenue);
        pnCharts.add(cpRevenue);
    }

    /**
     * Chart 2: Top 5 best-selling products
     */
    private void createTopProductsChart() {
        DefaultCategoryDataset dataset = StatisticsServer.createTopProductsDataset(5);

        fcTopProducts = ChartFactory.createBarChart(
            "Top 5 San Pham Ban Chay",
            "San pham",
            "So luong",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );

        customizeChart(fcTopProducts);
        CategoryPlot plot = fcTopProducts.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, UIStyle.colorSuccess);
        renderer.setDrawBarOutline(false);

        cpTopProducts = createChartPanel(fcTopProducts);
        pnCharts.add(cpTopProducts);
    }

    /**
     * Chart 3: Product distribution by type
     */
    private void createProductTypeChart() {
        DefaultPieDataset dataset = StatisticsServer.createProductTypeDataset();

        fcProductType = ChartFactory.createPieChart(
            "Phan Bo Theo Loai",
            dataset,
            true, true, false
        );

        customizeChart(fcProductType);
        PiePlot plot = (PiePlot) fcProductType.getPlot();
        plot.setBackgroundPaint(UIStyle.colorBgCard);
        plot.setOutlineVisible(false);
        plot.setLabelFont(UIStyle.font12);
        plot.setShadowPaint(null);

        // Colors for sections
        Color[] colors = {
            new Color(34, 139, 34),   // Forest Green
            new Color(23, 162, 184),  // Info Blue
            new Color(255, 193, 7),   // Warning Yellow
            new Color(220, 53, 69),   // Danger Red
            new Color(111, 66, 193),  // Purple
            new Color(253, 126, 20),  // Orange
            new Color(32, 201, 151)   // Teal
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
     * Chart 4: Top customers by revenue
     */
    private void createStockStatusChart() {
        DefaultCategoryDataset dataset = StatisticsServer.createTopCustomersDataset(5);

        fcStockStatus = ChartFactory.createBarChart(
            "Top Khach Hang Doanh Thu Cao",
            "Khach hang",
            "Doanh thu (K VND)",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false
        );

        customizeChart(fcStockStatus);
        CategoryPlot plot = fcStockStatus.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(139, 92, 246)); // Purple for customers
        renderer.setDrawBarOutline(false);

        cpStockStatus = createChartPanel(fcStockStatus);
        pnCharts.add(cpStockStatus);
    }

    /**
     * Common chart styling
     */
    private void customizeChart(JFreeChart chart) {
        chart.setBackgroundPaint(UIStyle.colorBgCard);

        // Title
        TextTitle title = chart.getTitle();
        title.setFont(UIStyle.font16Bold);
        title.setPaint(UIStyle.colorText);

        // Legend
        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(UIStyle.colorBgCard);
            chart.getLegend().setItemFont(UIStyle.font12);
        }

        // Plot background
        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(UIStyle.colorBgCard);
            plot.setRangeGridlinePaint(UIStyle.colorBorder);
            plot.setDomainGridlinePaint(UIStyle.colorBorder);
            plot.getDomainAxis().setTickLabelFont(UIStyle.font12);
            plot.getDomainAxis().setLabelFont(UIStyle.font14);
            plot.getRangeAxis().setTickLabelFont(UIStyle.font12);
            plot.getRangeAxis().setLabelFont(UIStyle.font14);
        }
    }

    /**
     * Create styled ChartPanel
     */
    private ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(UIStyle.colorBgCard);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyle.colorBorder, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    /**
     * Refresh all charts and table
     */
    public void refreshCharts() {
        // Refresh charts
        pnCharts.removeAll();
        createRevenueChart();
        createTopProductsChart();
        createProductTypeChart();
        createStockStatusChart();

        // Refresh expired products table
        createExpiredProductsPanel();
        splitPane.setBottomComponent(pnBottom);

        this.revalidate();
        this.repaint();
    }
}
