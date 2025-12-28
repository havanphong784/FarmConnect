package UI;

import Server.OrderServer;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * History Panel with Master-Detail UI
 * Master table: List of orders
 * Detail table: List of items in selected order
 */
public class HistoryPanel extends ProductListPanel {
    
    private static JTable tableOrders;
    private static JTable tableItems;
    private JScrollPane scrollOrders;
    private JScrollPane scrollItems;
    private static DefaultTableModel modelOrders;
    private static DefaultTableModel modelItems;
    private JButton btnExport;
    
    public static String[] columnOrders = {"Mã ĐH", "Thời Gian", "Số SP", "Tổng Tiền"};
    public static String[] columnItems = {"Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền"};
    
    public HistoryPanel() {
        super();
        
        // Setup Top Panel
        pnTop.removeAll();
        JLabel label = new JLabel("Lịch Sử Bán Hàng");
        label.setPreferredSize(new Dimension(900, 50));
        label.setBackground(UIStyle.colorBg);
        label.setFont(UIStyle.font20);
        label.setForeground(UIStyle.colorText);
        label.setHorizontalAlignment(JLabel.CENTER);
        pnTop.add(label);
        add(pnTop, BorderLayout.NORTH);
        
        // Setup Center Panel with Master-Detail
        pnBottom.removeAll();
        pnCenter.removeAll();
        pnCenter.setLayout(new BorderLayout(10, 10));
        
        // Master Panel (Orders List)
        JPanel masterPanel = new JPanel(new BorderLayout());
        masterPanel.setBackground(UIStyle.colorBg);
        
        JLabel lblOrders = new JLabel("Danh sách đơn hàng");
        lblOrders.setFont(UIStyle.font16Bold);
        lblOrders.setForeground(UIStyle.colorText);
        masterPanel.add(lblOrders, BorderLayout.NORTH);
        
        Object[][] dataOrders = OrderServer.ordersToTable();
        modelOrders = new DefaultTableModel(dataOrders, columnOrders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableOrders = new JTable(modelOrders);
        UIStyle.styleTable(tableOrders);
        tableOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollOrders = new JScrollPane(tableOrders);
        scrollOrders.setPreferredSize(new Dimension(1400, 300));
        masterPanel.add(scrollOrders, BorderLayout.CENTER);
        
        // Detail Panel (Order Items) - different color for distinction
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        detailPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, UIStyle.colorPrimary),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblItems = new JLabel("Chi tiet don hang");
        lblItems.setFont(UIStyle.font16Bold);
        lblItems.setForeground(UIStyle.colorPrimary);
        detailPanel.add(lblItems, BorderLayout.NORTH);
        
        modelItems = new DefaultTableModel(new Object[0][4], columnItems) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableItems = new JTable(modelItems);
        UIStyle.styleTable(tableItems);
        tableItems.setBackground(UIStyle.colorBgDark); // Slightly different color
        tableItems.getTableHeader().setBackground(UIStyle.colorPrimaryLight);
        
        scrollItems = new JScrollPane(tableItems);
        scrollItems.setPreferredSize(new Dimension(1400, 300));
        detailPanel.add(scrollItems, BorderLayout.CENTER);
        
        // Add selection listener to master table
        tableOrders.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tableOrders.getSelectedRow();
                    if (selectedRow >= 0) {
                        int orderId = (int) modelOrders.getValueAt(selectedRow, 0);
                        refreshItemsTable(orderId);
                    }
                }
            }
        });
        
        // Split pane for master-detail
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, masterPanel, detailPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(350);
        pnCenter.add(splitPane, BorderLayout.CENTER);
        
        // Bottom Panel with Export Button
        btnExport = UIStyle.setBtnActive(btnExport, "Xuat hoa don");
        btnExport.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        pnBottom.add(Box.createHorizontalGlue());
        pnBottom.add(btnExport);
        pnBottom.add(Box.createHorizontalStrut(40));
        
        btnExport.addActionListener(e -> exportInvoice());
    }
    
    private void exportInvoice() {
        int selectedRow = tableOrders.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn hàng!");
            return;
        }
        
        int orderId = (int) modelOrders.getValueAt(selectedRow, 0);
        
        // Open OrderForm with current order items
        OrderFormNew form = new OrderFormNew(orderId, modelItems);
        form.setVisible(true);
    }
    
    private static void refreshItemsTable(int orderId) {
        Object[][] dataItems = OrderServer.orderItemsToTable(orderId);
        modelItems.setDataVector(dataItems, columnItems);
    }
    
    public static void refreshOrdersTable() {
        if (modelOrders != null) {
            modelOrders.setDataVector(OrderServer.ordersToTable(), columnOrders);
            // Clear items table when orders refresh
            if (modelItems != null) {
                modelItems.setRowCount(0);
            }
        }
    }
}
