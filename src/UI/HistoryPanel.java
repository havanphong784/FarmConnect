package UI;

import Server.OrderServer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistoryPanel extends ProductListPanel{
    protected    JTable tableHistory;
    private JScrollPane scrollPane;
    private JButton btn;
    public static DefaultTableModel modelOrder;
    public static String[] columnOrder;
    public HistoryPanel() {
        super();

        pnTop.removeAll();
        JLabel label = new JLabel("Lịch Sử Bán Hàng");
        label.setPreferredSize(new Dimension(900,50));
        label.setBackground(UIStyle.colorBg);
        label.setFont(UIStyle.font20);
        label.setForeground(UIStyle.colorText);
        label.setHorizontalAlignment(JLabel.CENTER);
        pnTop.add(label);
        add(pnTop,BorderLayout.NORTH);

        pnBottom.removeAll();
        pnCenter.removeAll();
        Object[][] dataOrder = OrderServer.orderToTable();
        columnOrder = new String[]{"Tên Sản Phẩm","Giá","Số lượng mua","Thành Tiền","Thời Gian"};
        modelOrder = new DefaultTableModel(dataOrder,columnOrder);
        this.tableHistory = new JTable(modelOrder);
        this.tableHistory.setDefaultEditor(Object.class, null);
        this.tableHistory.setFont(UIStyle.font16);
        this.tableHistory.setBackground(UIStyle.colorBg);
        this.tableHistory.setRowHeight(40);
        this.tableHistory.setAutoCreateRowSorter(true);
        this.tableHistory.setShowVerticalLines(false);
        this.tableHistory.getTableHeader().setBackground(UIStyle.colorBg);
        this.tableHistory.getTableHeader().setFont(UIStyle.font16Bold);
        this.tableHistory.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.tableHistory.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.scrollPane = new JScrollPane(this.tableHistory);
        this.scrollPane.setPreferredSize(new Dimension(1400, 770));
        this.pnCenter.add(this.scrollPane);

        this.btn = UIStyle.setBtnActive(this.btn, "Xuất hóa đơn");
        this.btn.setBorder(BorderFactory.createEmptyBorder(6,16,6,16));
        pnBottom.add(Box.createHorizontalGlue());
        pnBottom.add(this.btn);
        pnBottom.add(Box.createHorizontalStrut(40));

        this.btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] rows = tableHistory.getSelectedRows();
                // chuyen qua thu tu trong model
                for (int i = 0 ; i < rows.length ; ++i) {
                    rows[i] = tableHistory.convertRowIndexToModel(rows[i]);
                    JOptionPane form = new OrderForm(modelOrder,rows);
                    JOptionPane.showMessageDialog(null,form,"Xuất hóa đơn.",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }

    public static void refreshTableOrder() {
        modelOrder.setDataVector(OrderServer.orderToTable(),columnOrder);
    }
}
