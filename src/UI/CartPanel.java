package UI;

import DBConnect.CustomerDAO;
import DBConnect.ProductsDAO;
import Model.Customer;
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
    private JComboBox<Customer> cboCustomer;
    private JTextField txtCustomerName;
    private JTextField txtCustomerSdt;
    private List<Customer> customerList;
    
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
        scrollPane.setPreferredSize(new Dimension(600, 250));
        add(scrollPane, BorderLayout.CENTER);
        
        // South Container (Customer Info + Bottom Panel)
        JPanel southContainer = new JPanel();
        southContainer.setLayout(new BoxLayout(southContainer, BoxLayout.Y_AXIS));
        southContainer.setBackground(UIStyle.colorBg);
        
        // Customer Info Panel
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        customerPanel.setBackground(UIStyle.colorBgDark);
        customerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 1, 0, UIStyle.colorBorder),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Customer combobox
        JLabel lblSelectCustomer = new JLabel("Chọn KH:");
        lblSelectCustomer.setFont(UIStyle.font14);
        lblSelectCustomer.setForeground(UIStyle.colorText);
        
        customerList = CustomerDAO.getAllCustomers();
        cboCustomer = new JComboBox<>();
        cboCustomer.addItem(null); // Empty option for new customer
        for (Customer c : customerList) {
            cboCustomer.addItem(c);
        }
        cboCustomer.setFont(UIStyle.font14);
        cboCustomer.setPreferredSize(new Dimension(200, 30));
        cboCustomer.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("-- Khách mới --");
                } else {
                    setText(((Customer) value).toString());
                }
                return this;
            }
        });
        
        // Auto-fill when customer selected
        cboCustomer.addActionListener(e -> {
            Customer selected = (Customer) cboCustomer.getSelectedItem();
            if (selected != null) {
                txtCustomerName.setText(selected.getCustomerName());
                txtCustomerSdt.setText(selected.getCustomerSdt() != null ? selected.getCustomerSdt() : "");
            } else {
                txtCustomerName.setText("");
                txtCustomerSdt.setText("");
            }
        });
        
        JLabel lblCustomerName = new JLabel("Tên:");
        lblCustomerName.setFont(UIStyle.font14);
        lblCustomerName.setForeground(UIStyle.colorText);
        
        txtCustomerName = new JTextField(12);
        txtCustomerName.setFont(UIStyle.font14);
        txtCustomerName.setPreferredSize(new Dimension(140, 30));
        
        JLabel lblCustomerSdt = new JLabel("SĐT:");
        lblCustomerSdt.setFont(UIStyle.font14);
        lblCustomerSdt.setForeground(UIStyle.colorText);
        
        txtCustomerSdt = new JTextField(10);
        txtCustomerSdt.setFont(UIStyle.font14);
        txtCustomerSdt.setPreferredSize(new Dimension(110, 30));
        
        customerPanel.add(lblSelectCustomer);
        customerPanel.add(cboCustomer);
        customerPanel.add(Box.createHorizontalStrut(10));
        customerPanel.add(lblCustomerName);
        customerPanel.add(txtCustomerName);
        customerPanel.add(lblCustomerSdt);
        customerPanel.add(txtCustomerSdt);
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(UIStyle.colorBg);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
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
        
        southContainer.add(customerPanel);
        southContainer.add(bottomPanel);
        add(southContainer, BorderLayout.SOUTH);
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
        cboCustomer.setSelectedIndex(0);
        txtCustomerName.setText("");
        txtCustomerSdt.setText("");
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
            
            // Get customer info
            String customerName = txtCustomerName.getText().trim();
            String customerSdt = txtCustomerSdt.getText().trim();
            
            // Convert to OrderItem list
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem item : cartItems) {
                orderItems.add(new OrderItem(item.proId, item.quantity, item.unitPrice));
            }
            
            // Create order with customer info
            int orderId = OrderServer.createOrder(userid, orderItems, customerName, customerSdt);
            
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
