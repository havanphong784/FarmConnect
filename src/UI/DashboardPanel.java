package UI;

import DBConnect.StatisticsDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * Dashboard Panel - Overview with key metrics and quick actions
 * Displayed when the app first opens
 */
public class DashboardPanel extends JPanel {
    
    private JPanel pnCards;
    private JPanel pnQuickActions;
    private JPanel pnAlerts;
    
    public DashboardPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(UIStyle.colorBg);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        initUI();
    }
    
    private void initUI() {
        // Header
        JPanel pnHeader = new JPanel(new BorderLayout());
        pnHeader.setBackground(UIStyle.colorBg);
        
        JLabel lblWelcome = new JLabel("Tổng Quan");
        lblWelcome.setFont(UIStyle.font24Bold);
        lblWelcome.setForeground(UIStyle.colorText);
        pnHeader.add(lblWelcome, BorderLayout.WEST);
        
        JLabel lblDate = new JLabel(java.time.LocalDate.now().toString());
        lblDate.setFont(UIStyle.font16);
        lblDate.setForeground(UIStyle.colorTextSecondary);
        pnHeader.add(lblDate, BorderLayout.EAST);
        
        add(pnHeader, BorderLayout.NORTH);
        
        // Main content
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.setBackground(UIStyle.colorBg);
        
        // Summary Cards Section
        createSummaryCards();
        pnMain.add(pnCards);
        pnMain.add(Box.createVerticalStrut(20));
        
        // Quick Actions Section
        createQuickActions();
        pnMain.add(pnQuickActions);
        pnMain.add(Box.createVerticalStrut(20));
        
        // Alerts Section
        createAlertsSection();
        pnMain.add(pnAlerts);
        
        JScrollPane scrollPane = new JScrollPane(pnMain);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Create summary cards with key metrics
     */
    private void createSummaryCards() {
        pnCards = new JPanel(new GridLayout(1, 4, 15, 0));
        pnCards.setBackground(UIStyle.colorBg);
        pnCards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        
        // Get data from DAO
        BigDecimal totalRevenue = StatisticsDAO.getTotalRevenue();
        int totalOrders = StatisticsDAO.getTotalOrders();
        int totalProducts = StatisticsDAO.getTotalProducts();
        Object[][] expiredProducts = StatisticsDAO.getExpiredProducts();
        
        // Format currency
        NumberFormat vnd = NumberFormat.getInstance(Locale.of("vi", "VN"));
        
        // Card 1: Total Revenue
        pnCards.add(createCard(
            "Tổng Doanh Thu",
            vnd.format(totalRevenue) + " VNĐ",
            new Color(16, 185, 129),  // Emerald
            "💰"
        ));
        
        // Card 2: Total Orders
        pnCards.add(createCard(
            "Đơn Hàng",
            String.valueOf(totalOrders),
            new Color(59, 130, 246),  // Blue
            "📦"
        ));
        
        // Card 3: Products in Stock
        pnCards.add(createCard(
            "Sản Phẩm Tồn Kho",
            String.valueOf(totalProducts),
            new Color(139, 92, 246),  // Purple
            "🏷️"
        ));
        
        // Card 4: Expiring Products (warning)
        pnCards.add(createCard(
            "Sắp Hết Hạn",
            String.valueOf(expiredProducts.length),
            expiredProducts.length > 0 ? new Color(239, 68, 68) : new Color(34, 197, 94),  // Red or Green
            "⚠️"
        ));
    }
    
    /**
     * Create a single metric card
     */
    private JPanel createCard(String title, String value, Color accentColor, String emoji) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Emoji
        JLabel lblEmoji = new JLabel(emoji);
        lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblEmoji.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblEmoji);
        card.add(Box.createVerticalStrut(10));
        
        // Title
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIStyle.font14);
        lblTitle.setForeground(UIStyle.colorTextSecondary);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(5));
        
        // Value
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(UIStyle.font24Bold);
        lblValue.setForeground(accentColor);
        lblValue.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblValue);
        
        return card;
    }
    
    /**
     * Create quick action buttons
     */
    private void createQuickActions() {
        pnQuickActions = new JPanel(new BorderLayout(10, 10));
        pnQuickActions.setBackground(UIStyle.colorBg);
        pnQuickActions.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        JLabel lblTitle = new JLabel("Thao Tác Nhanh");
        lblTitle.setFont(UIStyle.font18Bold);
        lblTitle.setForeground(UIStyle.colorText);
        pnQuickActions.add(lblTitle, BorderLayout.NORTH);
        
        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnButtons.setBackground(UIStyle.colorBg);
        
        // Quick action buttons - note: these need MainFrame reference to navigate
        JButton btnAddProduct = createQuickButton("Thêm Sản Phẩm", new Color(16, 185, 129));
        JButton btnSell = createQuickButton("Bán Hàng", new Color(59, 130, 246));
        JButton btnViewHistory = createQuickButton("Lịch Sử", new Color(139, 92, 246));
        JButton btnStatistics = createQuickButton("Thống Kê", new Color(245, 158, 11));
        
        // Add tooltips to explain functionality
        btnAddProduct.setToolTipText("Mở Menu > Sản phẩm để thêm sản phẩm mới");
        btnSell.setToolTipText("Mở Menu > Giỏ hàng để bán hàng");
        btnViewHistory.setToolTipText("Mở Menu > Lịch sử bán hàng");
        btnStatistics.setToolTipText("Mở Menu > Thống kê");
        
        pnButtons.add(btnAddProduct);
        pnButtons.add(btnSell);
        pnButtons.add(btnViewHistory);
        pnButtons.add(btnStatistics);
        
        pnQuickActions.add(pnButtons, BorderLayout.CENTER);
    }
    
    /**
     * Create a quick action button
     */
    private JButton createQuickButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(UIStyle.font14);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 45));
        btn.setOpaque(true);
        
        // Hover effect
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
    
    /**
     * Create alerts section for expiring/low stock products
     */
    private void createAlertsSection() {
        pnAlerts = new JPanel(new BorderLayout(10, 10));
        pnAlerts.setBackground(Color.WHITE);
        pnAlerts.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(254, 202, 202), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        pnAlerts.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        
        // Header
        JLabel lblTitle = new JLabel("⚠️ Cảnh Báo");
        lblTitle.setFont(UIStyle.font18Bold);
        lblTitle.setForeground(UIStyle.colorDanger);
        pnAlerts.add(lblTitle, BorderLayout.NORTH);
        
        // Content
        JPanel pnContent = new JPanel();
        pnContent.setLayout(new BoxLayout(pnContent, BoxLayout.Y_AXIS));
        pnContent.setBackground(Color.WHITE);
        
        // Get expired products
        Object[][] expiredProducts = StatisticsDAO.getExpiredProducts();
        
        if (expiredProducts.length == 0) {
            JLabel lblNoAlerts = new JLabel("Không có cảnh báo nào. Tất cả sản phẩm đều trong trạng thái tốt!");
            lblNoAlerts.setFont(UIStyle.font14);
            lblNoAlerts.setForeground(UIStyle.colorSuccess);
            pnContent.add(lblNoAlerts);
        } else {
            // Show up to 5 items
            int count = Math.min(expiredProducts.length, 5);
            for (int i = 0; i < count; i++) {
                Object[] product = expiredProducts[i];
                String name = (String) product[0];
                String expDate = (String) product[3];
                String status = (String) product[4];
                
                JPanel pnItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
                pnItem.setBackground(Color.WHITE);
                pnItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                
                JLabel lblIcon = new JLabel(status.equals("expired") ? "🔴" : "🟡");
                JLabel lblName = new JLabel(name);
                lblName.setFont(UIStyle.font14);
                lblName.setForeground(UIStyle.colorText);
                
                JLabel lblExp = new JLabel("- Hết hạn: " + expDate);
                lblExp.setFont(UIStyle.font12);
                lblExp.setForeground(status.equals("expired") ? UIStyle.colorDanger : UIStyle.colorWarning);
                
                pnItem.add(lblIcon);
                pnItem.add(lblName);
                pnItem.add(lblExp);
                pnContent.add(pnItem);
            }
            
            if (expiredProducts.length > 5) {
                JLabel lblMore = new JLabel("... và " + (expiredProducts.length - 5) + " sản phẩm khác");
                lblMore.setFont(UIStyle.font12);
                lblMore.setForeground(UIStyle.colorTextSecondary);
                lblMore.setBorder(new EmptyBorder(5, 30, 0, 0));
                pnContent.add(lblMore);
            }
        }
        
        // Low stock warning
        Map<String, Integer> lowStock = StatisticsDAO.getLowStockProducts(3);
        if (!lowStock.isEmpty()) {
            pnContent.add(Box.createVerticalStrut(15));
            
            JLabel lblLowStock = new JLabel("📉 Sản phẩm sắp hết hàng:");
            lblLowStock.setFont(UIStyle.font14);
            lblLowStock.setForeground(UIStyle.colorWarning);
            pnContent.add(lblLowStock);
            
            for (Map.Entry<String, Integer> entry : lowStock.entrySet()) {
                if (entry.getValue() < 20) {
                    JPanel pnItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
                    pnItem.setBackground(Color.WHITE);
                    pnItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
                    
                    JLabel lblName = new JLabel("• " + entry.getKey() + " - Còn: " + entry.getValue());
                    lblName.setFont(UIStyle.font12);
                    lblName.setForeground(UIStyle.colorTextSecondary);
                    pnItem.add(lblName);
                    pnContent.add(pnItem);
                }
            }
        }
        
        pnAlerts.add(pnContent, BorderLayout.CENTER);
    }
    
    /**
     * Refresh dashboard data
     */
    public void refresh() {
        removeAll();
        initUI();
        revalidate();
        repaint();
    }
}
