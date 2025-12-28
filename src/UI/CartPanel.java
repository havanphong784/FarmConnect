package UI;

import DBConnect.ProductsDAO;
import Model.OrderItem;
import Server.OrderServer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static UI.LoginFrame.userid;

/**
 * Shopping Cart Panel - allows adding multiple products before checkout
 */
public class CartPanel extends JPanel {
    private static CartPanel instance;
    
    private JTable cartTable;
    private DefaultTableModel cartModel;
    private JLabel lblTotal;
    private JButton btnCheckout;
    private JButton btnClear;
    
    private List<CartItem> cartItems = new ArrayList<>();
    
    // Inner class to hold cart item data
    public static class CartItem {
        public int proId;
        public String proName;
        public BigDecimal unitPrice;
        public int quantity;
        
        public CartItem(int proId, String proName, BigDecimal unitPrice, int quantity) {
            this.proId = proId;
            this.proName = proName;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }
        
        public BigDecimal getSubtotal() {
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
    
    public static CartPanel getInstance() {
        if (instance == null) {
            instance = new CartPanel();
        }
        return instance;
    }
    
    private CartPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIStyle.colorBg);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        initUI();
    }
    
    private void initUI() {
        // Title
        JLabel lblTitle = new JLabel("Giỏ Hàng");
        lblTitle.setFont(UIStyle.font20);
        lblTitle.setForeground(UIStyle.colorText);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        add(lblTitle, BorderLayout.NORTH);
        
        // Cart Table
        String[] columns = {"Ten San Pham", "Don Gia", "So Luong", "Thanh Tien", "Xoa"};
        cartModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only "Xóa" column is editable (for button)
            }
        };
        
        cartTable = new JTable(cartModel);
        UIStyle.styleTable(cartTable);
        
        // Add delete button renderer
        cartTable.getColumn("Xoa").setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JButton btn = new JButton("X");
            btn.setBackground(UIStyle.colorDanger);
            btn.setForeground(Color.WHITE);
            btn.setFont(UIStyle.font14);
            return btn;
        });
        
        // Add delete button action
        cartTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int column = cartTable.columnAtPoint(e.getPoint());
                int row = cartTable.rowAtPoint(e.getPoint());
                if (column == 4 && row >= 0) {
                    removeItem(row);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(UIStyle.colorBg);
        
        lblTotal = new JLabel("Tổng cộng: 0 VND");
        lblTotal.setFont(UIStyle.font16Bold);
        lblTotal.setForeground(UIStyle.colorPrimary);
        
        btnClear = new JButton("Xóa tất cả");
        btnClear.setFont(UIStyle.font16);
        btnClear.addActionListener(e -> clearCart());
        
        btnCheckout = UIStyle.setBtnActive(btnCheckout, "Thanh toán");
        btnCheckout.addActionListener(e -> checkout());
        
        bottomPanel.add(lblTotal);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(btnClear);
        bottomPanel.add(Box.createHorizontalStrut(10));
        bottomPanel.add(btnCheckout);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    public void addToCart(int proId, String proName, BigDecimal unitPrice, int quantity) {
        // Check if item already exists in cart
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.proId == proId) {
                // Update quantity
                item.quantity += quantity;
                refreshTable();
                return;
            }
        }
        
        // Add new item
        cartItems.add(new CartItem(proId, proName, unitPrice, quantity));
        refreshTable();
    }
    
    private void removeItem(int index) {
        if (index >= 0 && index < cartItems.size()) {
            cartItems.remove(index);
            refreshTable();
        }
    }
    
    public void clearCart() {
        cartItems.clear();
        refreshTable();
    }
    
    private void refreshTable() {
        cartModel.setRowCount(0);
        NumberFormat vnd = NumberFormat.getInstance(Locale.of("vi", "VN"));
        BigDecimal total = BigDecimal.ZERO;
        
        for (CartItem item : cartItems) {
            BigDecimal subtotal = item.getSubtotal();
            total = total.add(subtotal);
            cartModel.addRow(new Object[]{
                item.proName,
                vnd.format(item.unitPrice),
                item.quantity,
                vnd.format(subtotal),
                "X"
            });
        }
        
        lblTotal.setText("Tổng cộng: " + vnd.format(total) + " VND");
    }
    
    private void checkout() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
            return;
        }
        
        try {
            // Verify stock availability
            for (CartItem item : cartItems) {
                int currentStock = ProductsDAO.getProductQuantityById(item.proId);
                if (currentStock < item.quantity) {
                    JOptionPane.showMessageDialog(this, 
                        "Sản phẩm '" + item.proName + "' không đủ số lượng. Còn lại: " + currentStock);
                    return;
                }
            }
            
            // Convert to OrderItem list
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem item : cartItems) {
                orderItems.add(new OrderItem(item.proId, item.quantity, item.unitPrice));
            }
            
            // Create order
            int orderId = OrderServer.createOrder(userid, orderItems);
            
            if (orderId > 0) {
                JOptionPane.showMessageDialog(this, "Đặt hàng thành công! Mã đơn: " + orderId);
                clearCart();
                
                // Refresh related panels
                HistoryPanel.refreshOrdersTable();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Đặt hàng thất bại!\nVui lòng kiểm tra:\n1. Đã chạy script database/order_migration.sql chưa?\n2. Kết nối database có ổn không?");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi thanh toán: " + e.getMessage() + 
                "\n\nVui lòng kiểm tra đã chạy script database/order_migration.sql chưa!");
        }
    }
    
    public int getItemCount() {
        return cartItems.size();
    }
    
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
