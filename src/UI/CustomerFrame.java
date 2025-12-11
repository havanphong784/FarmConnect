package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerFrame extends JFrame {

    // Sidebar buttons
    private JButton btnHome;
    private JButton btnProducts;
    private JButton btnCart;
    private JButton btnOrders;

    // Top bar
    private JLabel lblUserName;
    private JButton btnLogout;

    // Main content with CardLayout
    private JPanel contentPanel;
    private CardLayout cardLayout;

    // Panels
    private JPanel homePanel;
    private JPanel productPanel;
    private JPanel cartPanel;
    private JPanel orderHistoryPanel;

    // Product components
    private JTable tblProducts;
    private DefaultTableModel productTableModel;
    private JButton btnAddToCart;
    private JButton btnBuyNow;

    // Cart components
    private JTable tblCart;
    private DefaultTableModel cartTableModel;
    private JLabel lblCartTotal;
    private JButton btnCheckout;
    private JButton btnRemoveFromCart;
    private JButton btnClearCart;

    // Order history components
    private JTable tblOrders;
    private DefaultTableModel orderTableModel;

    public CustomerFrame() {
        initComponents();
        initLayout();
        initActions();

        setTitle("FarmConnest - Customer Dashboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // full screen
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // ---------- Sidebar buttons ----------
        btnHome = new JButton("Trang chủ");
        btnProducts = new JButton("Sản phẩm");
        btnCart = new JButton("Giỏ hàng");
        btnOrders = new JButton("Lịch sử mua");

        // ---------- Top bar ----------
        lblUserName = new JLabel("Xin chào, Customer Demo");
        lblUserName.setFont(lblUserName.getFont().deriveFont(Font.BOLD));
        btnLogout = new JButton("Đăng xuất");

        // ---------- Content panel ----------
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Tạo từng panel
        initHomePanel();
        initProductPanel();
        initCartPanel();
        initOrderHistoryPanel();

        // Đăng ký vào CardLayout
        contentPanel.add(homePanel, "HOME");
        contentPanel.add(productPanel, "PRODUCTS");
        contentPanel.add(cartPanel, "CART");
        contentPanel.add(orderHistoryPanel, "ORDERS");

        // Set màn hình mặc định
        cardLayout.show(contentPanel, "HOME");
    }

    private void initHomePanel() {
        homePanel = new JPanel(new BorderLayout());
        JLabel lblWelcome = new JLabel("Dashboard khách hàng FarmConnest", SwingConstants.CENTER);
        lblWelcome.setFont(lblWelcome.getFont().deriveFont(Font.BOLD, 26f));

        JTextArea txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);
        txtInfo.setText(
                "- Xem và chọn sản phẩm nông nghiệp chất lượng.\n" +
                        "- Thêm sản phẩm vào giỏ hàng.\n" +
                        "- Thanh toán nhanh chóng.\n" +
                        "- Theo dõi lịch sử mua hàng."
        );

        homePanel.add(lblWelcome, BorderLayout.NORTH);
        homePanel.add(new JScrollPane(txtInfo), BorderLayout.CENTER);
    }

    private void initProductPanel() {
        productPanel = new JPanel(new BorderLayout(10, 10));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Danh sách sản phẩm", SwingConstants.LEFT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 20f));
        productPanel.add(lblTitle, BorderLayout.NORTH);

        // Bảng sản phẩm
        String[] productColumns = {"Mã SP", "Tên sản phẩm", "Danh mục", "Đơn vị", "Giá", "Tồn kho"};
        productTableModel = new DefaultTableModel(productColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProducts = new JTable(productTableModel);
        tblProducts.setRowHeight(24);

        // Fake data demo
        productTableModel.addRow(new Object[]{"SP001", "Rau cải xanh", "Rau", "Kg", 15000, 100});
        productTableModel.addRow(new Object[]{"SP002", "Cà chua bi", "Rau quả", "Kg", 30000, 50});
        productTableModel.addRow(new Object[]{"SP003", "Trứng gà ta", "Trứng", "Vỉ 10", 35000, 200});

        JScrollPane spProduct = new JScrollPane(tblProducts);
        productPanel.add(spProduct, BorderLayout.CENTER);

        // Panel action
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAddToCart = new JButton("Thêm vào giỏ");
        btnBuyNow = new JButton("Mua ngay");
        actionPanel.add(btnAddToCart);
        actionPanel.add(btnBuyNow);

        productPanel.add(actionPanel, BorderLayout.SOUTH);
    }

    private void initCartPanel() {
        cartPanel = new JPanel(new BorderLayout(10, 10));
        cartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Giỏ hàng", SwingConstants.LEFT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 20f));
        cartPanel.add(lblTitle, BorderLayout.NORTH);

        String[] cartColumns = {"Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        cartTableModel = new DefaultTableModel(cartColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép sửa cột số lượng nếu bạn muốn
                return column == 2; // chỉ cho sửa số lượng
            }
        };
        tblCart = new JTable(cartTableModel);
        tblCart.setRowHeight(24);

        JScrollPane spCart = new JScrollPane(tblCart);
        cartPanel.add(spCart, BorderLayout.CENTER);

        // Panel tổng tiền + nút
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblCartTotal = new JLabel("Tổng tiền: 0 VND");
        totalPanel.add(lblCartTotal);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRemoveFromCart = new JButton("Xóa khỏi giỏ");
        btnClearCart = new JButton("Xóa hết");
        btnCheckout = new JButton("Thanh toán");
        buttonPanel.add(btnRemoveFromCart);
        buttonPanel.add(btnClearCart);
        buttonPanel.add(btnCheckout);

        bottomPanel.add(totalPanel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        cartPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initOrderHistoryPanel() {
        orderHistoryPanel = new JPanel(new BorderLayout(10, 10));
        orderHistoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Lịch sử mua hàng", SwingConstants.LEFT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 20f));
        orderHistoryPanel.add(lblTitle, BorderLayout.NORTH);

        String[] orderColumns = {"Mã đơn", "Ngày", "Tổng tiền", "Trạng thái", "Ghi chú"};
        orderTableModel = new DefaultTableModel(orderColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblOrders = new JTable(orderTableModel);
        tblOrders.setRowHeight(24);

        // Fake data demo
        orderTableModel.addRow(new Object[]{"DH001", "2025-12-01", 250000, "Hoàn thành", ""});
        orderTableModel.addRow(new Object[]{"DH002", "2025-12-05", 120000, "Đang giao", ""});

        JScrollPane spOrders = new JScrollPane(tblOrders);
        orderHistoryPanel.add(spOrders, BorderLayout.CENTER);
    }

    private void initLayout() {
        // ---------- Sidebar ----------
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblLogo = new JLabel("FarmConnest");
        lblLogo.setFont(lblLogo.getFont().deriveFont(Font.BOLD, 20f));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(lblLogo);
        sidebar.add(Box.createVerticalStrut(20));

        btnHome.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnProducts.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCart.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnOrders.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(btnHome);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnProducts);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnCart);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnOrders);
        sidebar.add(Box.createVerticalGlue());

        // ---------- Top bar ----------
        JPanel topBar = new JPanel(new BorderLayout(10, 10));
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        topBar.add(lblUserName, BorderLayout.WEST);
        topBar.add(btnLogout, BorderLayout.EAST);

        // ---------- Main layout ----------
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Chia trái/phải: Sidebar + Main
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, mainPanel);
        splitPane.setDividerLocation(200);
        splitPane.setOneTouchExpandable(true);

        setContentPane(splitPane);
    }

    private void initActions() {
        // Sidebar navigation
        btnHome.addActionListener(e -> cardLayout.show(contentPanel, "HOME"));
        btnProducts.addActionListener(e -> cardLayout.show(contentPanel, "PRODUCTS"));
        btnCart.addActionListener(e -> cardLayout.show(contentPanel, "CART"));
        btnOrders.addActionListener(e -> cardLayout.show(contentPanel, "ORDERS"));

        // Đăng xuất (demo)
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn đăng xuất?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        // Thêm sản phẩm vào giỏ
        btnAddToCart.addActionListener(e -> addSelectedProductToCart(false));
        // Mua ngay: thêm vào giỏ rồi chuyển sang giỏ hàng
        btnBuyNow.addActionListener(e -> {
            addSelectedProductToCart(true);
            cardLayout.show(contentPanel, "CART");
        });

        // Xóa khỏi giỏ
        btnRemoveFromCart.addActionListener(e -> removeSelectedItemFromCart());

        // Xóa hết giỏ
        btnClearCart.addActionListener(e -> {
            cartTableModel.setRowCount(0);
            updateCartTotal();
        });

        // Thanh toán
        btnCheckout.addActionListener(e -> doCheckout());
    }

    // ---------- Logic demo cho giỏ hàng ----------

    private void addSelectedProductToCart(boolean silentIfNoSelection) {
        int selectedRow = tblProducts.getSelectedRow();
        if (selectedRow < 0) {
            if (!silentIfNoSelection) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn một sản phẩm.",
                        "Chưa chọn sản phẩm",
                        JOptionPane.WARNING_MESSAGE);
            }
            return;
        }

        String productId = productTableModel.getValueAt(selectedRow, 0).toString();
        String productName = productTableModel.getValueAt(selectedRow, 1).toString();
        int price = Integer.parseInt(productTableModel.getValueAt(selectedRow, 4).toString());

        // Mặc định số lượng = 1 (demo), sau này có thể cho user nhập
        int quantity = 1;
        int amount = price * quantity;

        cartTableModel.addRow(new Object[]{productId, productName, quantity, price, amount});
        updateCartTotal();
    }

    private void removeSelectedItemFromCart() {
        int selectedRow = tblCart.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn sản phẩm trong giỏ để xóa.",
                    "Chưa chọn dòng",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        cartTableModel.removeRow(selectedRow);
        updateCartTotal();
    }

    private void updateCartTotal() {
        long total = 0;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            Object value = cartTableModel.getValueAt(i, 4);
            if (value != null) {
                total += Long.parseLong(value.toString());
            }
        }
        lblCartTotal.setText("Tổng tiền: " + total + " VND");
    }

    private void doCheckout() {
        if (cartTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Giỏ hàng đang trống.",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // TODO: Thực hiện tạo đơn hàng, lưu DB, trừ tồn kho,...
        int confirm = JOptionPane.showConfirmDialog(this,
                "Xác nhận thanh toán giỏ hàng?",
                "Thanh toán",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Demo: xóa giỏ, có thể thêm 1 dòng lịch sử đơn hàng
            long total = 0;
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                total += Long.parseLong(cartTableModel.getValueAt(i, 4).toString());
            }

            // Thêm vào lịch sử demo
            String orderId = "DH" + String.format("%03d", orderTableModel.getRowCount() + 1);
            String date = java.time.LocalDate.now().toString();
            orderTableModel.addRow(new Object[]{orderId, date, total, "Hoàn thành", ""});

            cartTableModel.setRowCount(0);
            updateCartTotal();

            JOptionPane.showMessageDialog(this,
                    "Thanh toán thành công.\nMã đơn: " + orderId,
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ---------- Main demo ----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CustomerFrame().setVisible(true);
        });
    }
}
