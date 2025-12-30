package UI;

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
import java.awt.*;

/**
 * Statistics Panel - Dashboard with charts and reports
 */
public class StatisticsPanel extends JPanel {
    private ChartPanel cpRevenue, cpTopProducts, cpProductType, cpStockStatus;
    private JFreeChart fcRevenue, fcTopProducts, fcProductType, fcStockStatus;
    private JPanel pnCharts;

    public StatisticsPanel() {
        this.setBorder(new EmptyBorder(15, 15, 15, 15));
        this.setLayout(new BorderLayout());
        this.setBackground(UIStyle.colorBg);

        // Header
        JLabel lblTitle = new JLabel("  📊 Biểu Đồ Thống Kê");
        lblTitle.setFont(UIStyle.font24Bold);
        lblTitle.setForeground(new Color(30, 90, 150));
        lblTitle.setBorder(new EmptyBorder(0, 0, 15, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // Charts section with light blue background
        JPanel pnChartsWrapper = new JPanel(new BorderLayout());
        pnChartsWrapper.setBackground(new Color(240, 248, 255)); // Alice Blue
        pnChartsWrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        pnCharts = new JPanel(new GridLayout(2, 2, 15, 15));
        pnCharts.setBackground(new Color(240, 248, 255)); // Alice Blue
        pnCharts.setOpaque(false);
        pnChartsWrapper.add(pnCharts, BorderLayout.CENTER);

        // Create charts
        createRevenueChart();
        createTopProductsChart();
        createProductTypeChart();
        createStockStatusChart();

        this.add(pnChartsWrapper, BorderLayout.CENTER);
    }

    /**
     * Chart 1: Revenue by day (7 days)
     */
    private void createRevenueChart() {
        DefaultCategoryDataset dataset = StatisticsServer.createRevenueDataset();

        fcRevenue = ChartFactory.createLineChart(
            "Doanh Thu 7 Ngày",
            "Ngày",
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
            "Top 5 Sản Phẩm Bán Chạy",
            "Sản phẩm",
            "Số lượng",
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
            "Phân Bố Theo Loại",
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
            "Top Khách Hàng Doanh Thu Cao",
            "Khách hàng",
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
     * Refresh all charts
     */
    public void refreshCharts() {
        pnCharts.removeAll();
        createRevenueChart();
        createTopProductsChart();
        createProductTypeChart();
        createStockStatusChart();

        this.revalidate();
        this.repaint();
    }
}
