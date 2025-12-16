package UI;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private ChartPanel cp1,cp2,cp3,cp4;
    private JFreeChart fc1,fc2,fc3,fc4;
    private DefaultPieDataset data1,data2,data3,data4;
    public StatisticsPanel() {
        this.setBorder(new EmptyBorder(0,30,0,30));
        this.setLayout(new GridLayout(2,2,20,20));
        this.setBackground(UIStyle.colorBg);


    }
}
