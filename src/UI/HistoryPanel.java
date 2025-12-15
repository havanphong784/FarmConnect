package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class HistoryPanel extends ProductListPanel{
    private JTable tableHistory;
    private JScrollPane scrollPane;
    public HistoryPanel() {
        super();
        pnBottom.removeAll();
        pnCenter.removeAll();
        TableModel model = new DefaultTableModel(data,cols);
        this.tableHistory = new JTable(model);
        this.tableHistory.setDefaultEditor(Object.class, null);
        this.tableHistory.setFont(UIStyle.font16);
        this.tableHistory.setBackground(UIStyle.colorBg);
        this.tableHistory.setRowHeight(40);
        this.tableHistory.setAutoCreateRowSorter(true);
        this.tableHistory.setShowVerticalLines(false);
        this.tableHistory.setShowVerticalLines(false);
        this.tableHistory.getTableHeader().setBackground(UIStyle.colorBg);
        this.tableHistory.getTableHeader().setFont(UIStyle.font16Bold);
        this.tableHistory.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.scrollPane = new JScrollPane(this.tableHistory);
        this.scrollPane.setPreferredSize(new Dimension(1400, 770));
        this.pnCenter.add(this.scrollPane);
    }
}
