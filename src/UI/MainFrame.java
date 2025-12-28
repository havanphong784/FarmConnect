package UI;

import javax.swing.*;
import java.awt.*;

import static UI.LoginFrame.username;

/**
 * Main application frame - Contains navigation and content panels
 * Uses CardLayout to switch between different views
 */
public class MainFrame extends JFrame {
    
    // Main panels
    private JPanel pnNavigation, pnContent, pnHeader, pnCard;
    
    // Navigation buttons
    private JButton btnDashboard, btnProducts, btnCart, btnStatistics, btnHistory, btnLogout, btnMenu;
    
    // Header components
    private JLabel lblNameApp, lblAvatar, lblRole, lblUsername;

    /**
     * Constructor - Initialize main frame
     */
    public MainFrame() {
        setupFrame();
        setupNavigationPanel();
        setupContentPanel();
        setupCardLayout();
        setupNavigationActions();
    }
    
    /**
     * Configure main frame settings
     */
    private void setupFrame() {
        this.setSize(1200, 850);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("FarmConnect - Quản lý nông sản");
    }
    
    /**
     * Setup left navigation panel
     */
    private void setupNavigationPanel() {
        this.pnNavigation = new JPanel();
        this.pnNavigation.setLayout(new BoxLayout(pnNavigation, BoxLayout.Y_AXIS));
        this.pnNavigation.setPreferredSize(new Dimension(240, 0));
        this.pnNavigation.setBackground(UIStyle.colorPrimary);
        this.add(this.pnNavigation, BorderLayout.WEST);

        // App name label
        this.lblNameApp = UIStyle.setLabelPrimary(this.lblNameApp, "Farm Connect");
        this.lblNameApp.setFont(UIStyle.font30);
        this.lblNameApp.setPreferredSize(new Dimension(240, 80));
        this.lblNameApp.setMaximumSize(new Dimension(240, 80));
        this.lblNameApp.setHorizontalAlignment(JLabel.CENTER);
        this.lblNameApp.setVerticalAlignment(JLabel.CENTER);
        this.lblNameApp.setForeground(Color.white);
        this.pnNavigation.add(this.lblNameApp);

        // Navigation buttons
        this.btnDashboard = UIStyle.setButtonDB(this.btnDashboard, "Tổng quan");
        this.pnNavigation.add(this.btnDashboard);
        
        this.btnProducts = UIStyle.setButtonDB(this.btnProducts, "Sản phẩm");
        this.pnNavigation.add(this.btnProducts);
        
        this.btnCart = UIStyle.setButtonDB(this.btnCart, "Giỏ hàng");
        this.pnNavigation.add(this.btnCart);
        
        this.btnStatistics = UIStyle.setButtonDB(this.btnStatistics, "Thống kê");
        this.pnNavigation.add(this.btnStatistics);
        
        this.btnHistory = UIStyle.setButtonDB(this.btnHistory, "Lịch sử bán hàng");
        this.pnNavigation.add(this.btnHistory);

        // Spacer
        this.pnNavigation.add(Box.createVerticalGlue());

        // Logout button
        this.btnLogout = UIStyle.setButtonDB(this.btnLogout, "Đăng xuất");
        this.btnLogout.setFont(UIStyle.font16);
        this.btnLogout.setForeground(UIStyle.colorRed);
        this.pnNavigation.add(this.btnLogout);

        // Hide navigation by default
        this.pnNavigation.setVisible(false);
    }
    
    /**
     * Setup right content panel with header
     */
    private void setupContentPanel() {
        this.pnContent = new JPanel(new BorderLayout());

        // Header panel
        this.pnHeader = new JPanel();
        this.pnHeader.setLayout(new BoxLayout(this.pnHeader, BoxLayout.X_AXIS));
        this.pnHeader.setBackground(UIStyle.colorHeader);
        this.pnHeader.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        // Menu toggle button
        this.btnMenu = new JButton("[=] Menu");
        this.btnMenu.setFont(UIStyle.font16Bold);
        this.btnMenu.setBackground(UIStyle.colorPrimary);
        this.btnMenu.setForeground(Color.WHITE);
        this.btnMenu.setFocusable(false);
        this.btnMenu.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        this.btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.pnHeader.add(this.btnMenu);
        
        // Toggle menu action
        btnMenu.addActionListener(e -> toggleNavigation());

        // Spacer
        this.pnHeader.add(Box.createHorizontalGlue());

        // User info
        setupUserInfo();

        this.pnContent.add(this.pnHeader, BorderLayout.NORTH);
        this.add(this.pnContent, BorderLayout.CENTER);
    }
    
    /**
     * Setup user info display in header
     */
    private void setupUserInfo() {
        JPanel pnUserInfo = new JPanel();
        pnUserInfo.setLayout(new BoxLayout(pnUserInfo, BoxLayout.Y_AXIS));
        pnUserInfo.setBackground(UIStyle.colorHeader);

        this.lblUsername = new JLabel(username);
        this.lblUsername.setFont(UIStyle.font16Bold);
        this.lblUsername.setForeground(UIStyle.colorPrimary);
        this.lblUsername.setAlignmentX(Component.RIGHT_ALIGNMENT);

        this.lblRole = new JLabel("Quan tri vien");
        this.lblRole.setFont(UIStyle.font14);
        this.lblRole.setForeground(UIStyle.colorText);
        this.lblRole.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pnUserInfo.add(this.lblUsername);
        pnUserInfo.add(Box.createVerticalStrut(4));
        pnUserInfo.add(this.lblRole);

        // Avatar
        this.lblAvatar = new JLabel("FC");
        this.lblAvatar.setFont(new Font("Arial", Font.BOLD, 24));
        this.lblAvatar.setForeground(UIStyle.colorPrimary);
        this.lblAvatar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 10));

        this.pnHeader.add(pnUserInfo);
        this.pnHeader.add(this.lblAvatar);
    }
    
    /**
     * Setup card layout for content views
     */
    private void setupCardLayout() {
        this.pnCard = new JPanel(new CardLayout());
        this.pnCard.add(new DashboardPanel(), "Dashboard");
        this.pnCard.add(new ProductListPanel(), "Products");
        this.pnCard.add(new StatisticsPanel(), "Statistics");
        this.pnCard.add(new HistoryPanel(), "History");
        this.pnCard.add(CartPanel.getInstance(), "Cart");
        
        // Show dashboard by default
        showCard("Dashboard");

        this.pnContent.add(this.pnCard, BorderLayout.CENTER);
    }
    
    /**
     * Setup action listeners for navigation buttons
     */
    private void setupNavigationActions() {
        btnDashboard.addActionListener(e -> {
            showCard("Dashboard");
            hideNavigation();
        });

        btnProducts.addActionListener(e -> {
            showCard("Products");
            hideNavigation();
        });

        btnCart.addActionListener(e -> {
            showCard("Cart");
            hideNavigation();
        });

        btnStatistics.addActionListener(e -> {
            showCard("Statistics");
            hideNavigation();
        });

        btnHistory.addActionListener(e -> {
            showCard("History");
            hideNavigation();
        });

        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
    
    /**
     * Toggle navigation panel visibility
     */
    private void toggleNavigation() {
        if (pnNavigation.isVisible()) {
            pnNavigation.setVisible(false);
            btnMenu.setText("[=] Menu");
        } else {
            pnNavigation.setVisible(true);
            btnMenu.setText("[X] Dong");
        }
        revalidate();
        repaint();
    }
    
    /**
     * Hide navigation panel
     */
    private void hideNavigation() {
        pnNavigation.setVisible(false);
        btnMenu.setText("[=] Menu");
    }
    
    /**
     * Show specific card in card layout
     * @param cardName Name of the card to show
     */
    private void showCard(String cardName) {
        CardLayout cl = (CardLayout) pnCard.getLayout();
        cl.show(pnCard, cardName);
    }
}
