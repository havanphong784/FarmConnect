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
public class HistoryPanel extends JPanel {
    
    private static JTable tableOrders;
    private static JTable tableItems;
    private JScrollPane scrollOrders;
    private JScrollPane scrollItems;
    private static DefaultTableModel modelOrders;
    private static DefaultTableModel modelItems;
    private JButton btnExport;
    
    public static String[] columnOrders = {"Mã ĐH", "Thời Gian", "Khách Hàng", "Số SP", "Tổng Tiền"};
    public static String[] columnItems = {"Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Thành Tiền"};
    
    public HistoryPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIStyle.colorBg);
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        initHeader();
        initMasterDetailPanel();
        initBottomPanel();
    }
    
    /**
     * Header with title
     */
    private void initHeader() {
        JPanel pnHeader = new JPanel(new BorderLayout());
        pnHeader.setBackground(UIStyle.colorBg);
        pnHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel lblTitle = new JLabel("Lịch Sử Bán Hàng");
        lblTitle.setFont(UIStyle.font24Bold);
        lblTitle.setForeground(UIStyle.colorText);
        pnHeader.add(lblTitle, BorderLayout.WEST);
        
        // Order count
        Object[][] orders = OrderServer.ordersToTable();
        JLabel lblCount = new JLabel("Tổng: " + orders.length + " đơn hàng");
        lblCount.setFont(UIStyle.font14);
        lblCount.setForeground(UIStyle.colorTextSecondary);
        pnHeader.add(lblCount, BorderLayout.EAST);
        
        add(pnHeader, BorderLayout.NORTH);
    }
    
    /**
     * Master-Detail panel with split pane
     */
    private void initMasterDetailPanel() {
        // Master Panel (Orders List)
        JPanel masterPanel = new JPanel(new BorderLayout(5, 5));
        masterPanel.setBackground(UIStyle.colorBgCard);
        masterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyle.colorBorder, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblOrders = new JLabel("Danh Sách Đơn Hàng");
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
        scrollOrders.setBorder(BorderFactory.createEmptyBorder());
        masterPanel.add(scrollOrders, BorderLayout.CENTER);
        
        // Detail Panel (Order Items)
        JPanel detailPanel = new JPanel(new BorderLayout(5, 5));
        detailPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        detailPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyle.colorInfo, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblItems = new JLabel("Chi Tiết Đơn Hàng");
        lblItems.setFont(UIStyle.font16Bold);
        lblItems.setForeground(UIStyle.colorInfo);
        detailPanel.add(lblItems, BorderLayout.NORTH);
        
        modelItems = new DefaultTableModel(new Object[0][4], columnItems) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableItems = new JTable(modelItems);
        UIStyle.styleTable(tableItems);
        tableItems.setBackground(UIStyle.colorBgDark);
        tableItems.getTableHeader().setBackground(new Color(200, 220, 255));
        
        scrollItems = new JScrollPane(tableItems);
        scrollItems.setBorder(BorderFactory.createEmptyBorder());
        detailPanel.add(scrollItems, BorderLayout.CENTER);
        
        // Selection listener for master table
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
        splitPane.setDividerSize(8);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(null);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    /**
     * Bottom panel with export button
     */
    private void initBottomPanel() {
        JPanel pnBottom = new JPanel();
        pnBottom.setBackground(UIStyle.colorBgCard);
        pnBottom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyle.colorBorder, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        pnBottom.setLayout(new BoxLayout(pnBottom, BoxLayout.X_AXIS));
        
        JLabel lblInfo = new JLabel("Chọn đơn hàng để xem chi tiết hoặc xuất hóa đơn");
        lblInfo.setFont(UIStyle.font14);
        lblInfo.setForeground(UIStyle.colorTextSecondary);
        pnBottom.add(lblInfo);
        
        pnBottom.add(Box.createHorizontalGlue());
        
        btnExport = createButton("Xuất Hóa Đơn PDF", UIStyle.colorPrimary);
        btnExport.addActionListener(e -> exportInvoice());
        pnBottom.add(btnExport);
        
        add(pnBottom, BorderLayout.SOUTH);
    }
    
    /**
     * Create styled button
     */
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(180, 42));
        btn.setFont(UIStyle.font14);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        final Color hoverColor = bgColor.darker();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
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
