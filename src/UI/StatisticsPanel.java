package UI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    public StatisticsPanel() {
        setLayout(new BorderLayout());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(120, "Doanh số", "T1");
        dataset.addValue(180, "Doanh số", "T2");
        dataset.addValue(90,  "Doanh số", "T3");

        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh số theo tháng",   // title
                "Tháng",                 // x-axis
                "Giá trị",               // y-axis
                dataset
        );

        add(new ChartPanel(chart), BorderLayout.CENTER);
    }
}
