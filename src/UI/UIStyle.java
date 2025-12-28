package UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * UIStyle - Unified Design System for FarmConnect
 * Modern, clean, professional look
 */
public class UIStyle {
    
    // =============================================
    // COLOR PALETTE - Modern Green Theme
    // =============================================
    
    // Primary colors
    public static Color colorPrimary = new Color(34, 139, 34);      // Forest Green
    public static Color colorPrimaryDark = new Color(25, 100, 25);  // Darker green
    public static Color colorPrimaryLight = new Color(144, 238, 144); // Light green
    
    // Background colors
    public static Color colorBg = new Color(250, 252, 250);         // Very light green-white
    public static Color colorBgDark = new Color(240, 245, 240);     // Slightly darker bg
    public static Color colorBgCard = new Color(255, 255, 255);     // White for cards
    
    // Text colors
    public static Color colorText = new Color(33, 37, 41);          // Dark gray for text
    public static Color colorTextSecondary = new Color(108, 117, 125); // Gray for secondary text
    public static Color colorLabel = new Color(73, 80, 87);         // Label color
    
    // UI Element colors
    public static Color colorTextField = new Color(248, 250, 252);  // Light input bg
    public static Color colorBorder = new Color(206, 212, 218);     // Border color
    public static Color colorHeader = new Color(248, 249, 250);     // Header bg
    
    // Status colors
    public static Color colorSuccess = new Color(40, 167, 69);      // Success green
    public static Color colorWarning = new Color(255, 193, 7);      // Warning yellow
    public static Color colorDanger = new Color(220, 53, 69);       // Danger red
    public static Color colorRed = new Color(220, 53, 69);          // Alias for red
    public static Color colorInfo = new Color(23, 162, 184);        // Info blue
    
    // Table colors
    public static Color colorTableHeader = new Color(233, 245, 233); // Light green header
    public static Color colorTableRowEven = new Color(255, 255, 255);
    public static Color colorTableRowOdd = new Color(248, 252, 248);
    public static Color colorTableSelected = new Color(200, 230, 200);

    // =============================================
    // FONTS - Clean, Modern Typography
    // =============================================
    
    public static Font font12 = new Font("Segoe UI", Font.PLAIN, 12);
    public static Font font14 = new Font("Segoe UI", Font.PLAIN, 14);
    public static Font font16 = new Font("Segoe UI", Font.PLAIN, 16);
    public static Font font16Bold = new Font("Segoe UI", Font.BOLD, 16);
    public static Font font18 = new Font("Segoe UI", Font.PLAIN, 18);
    public static Font font18Bold = new Font("Segoe UI", Font.BOLD, 18);
    public static Font font20 = new Font("Segoe UI", Font.PLAIN, 20);
    public static Font font24Bold = new Font("Segoe UI", Font.BOLD, 24);
    public static Font font30 = new Font("Segoe UI", Font.BOLD, 30);
    public static Font fontEmoji = new Font("Segoe UI Emoji", Font.PLAIN, 24);

    // =============================================
    // COMPONENT STYLES
    // =============================================
    
    /**
     * Set default theme for dialogs
     */
    public static void setDefaultsTheme() {
        UIManager.put("OptionPane.background", colorBg);
        UIManager.put("Panel.background", colorBg);
        UIManager.put("OptionPane.messageFont", font16);
        UIManager.put("OptionPane.buttonFont", font16Bold);
        UIManager.put("OptionPane.messageForeground", colorText);
        Border messagePad = BorderFactory.createEmptyBorder(15, 20, 10, 20);
        Border buttonPad = BorderFactory.createEmptyBorder(10, 20, 15, 20);
        UIManager.put("OptionPane.messageAreaBorder", messagePad);
        UIManager.put("OptionPane.buttonAreaBorder", buttonPad);
        
        // Button defaults
        UIManager.put("Button.background", colorPrimary);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", font16);
    }

    /**
     * Primary action button (green)
     */
    public static JButton setBtnActive(JButton btn, String text) {
        btn = new JButton(text);
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorPrimary);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(font16Bold);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    /**
     * Secondary button (outline style)
     */
    public static JButton setBtnSecondary(JButton btn, String text) {
        btn = new JButton(text);
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setForeground(colorPrimary);
        btn.setBackground(colorBgCard);
        btn.setFocusPainted(false);
        btn.setFont(font16);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(colorPrimary, 2));
        return btn;
    }

    /**
     * Danger button (red)
     */
    public static JButton setBtnDanger(JButton btn, String text) {
        btn = new JButton(text);
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorDanger);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(font16Bold);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Navigation button (sidebar)
     */
    public static JButton setButtonDB(JButton button, String text) {
        button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(font16);
        button.setBackground(colorPrimary);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(JButton.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    /**
     * Text field with modern styling
     */
    public static JTextField setTextField(JTextField textField, String text) {
        textField = new JTextField(text);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBorder, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        textField.setBackground(colorTextField);
        textField.setForeground(colorText);
        textField.setCaretColor(colorPrimary);
        textField.setFont(font16);
        return textField;
    }

    /**
     * Password field with modern styling
     */
    public static JPasswordField setPassField(JPasswordField passwordField) {
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBorder, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        passwordField.setBackground(colorTextField);
        passwordField.setForeground(colorText);
        passwordField.setCaretColor(colorPrimary);
        passwordField.setFont(font16);
        return passwordField;
    }

    /**
     * Primary label (green text)
     */
    public static JLabel setLabelPrimary(JLabel label, String text) {
        label = new JLabel(text);
        label.setForeground(colorPrimary);
        label.setFont(font16Bold);
        return label;
    }

    /**
     * Standard label
     */
    public static JLabel setLabel(JLabel label, String text) {
        label = new JLabel(text);
        label.setForeground(colorLabel);
        label.setFont(font16);
        return label;
    }

    /**
     * Style a JTable with modern look
     */
    public static void styleTable(JTable table) {
        table.setFont(font14);
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(colorBorder);
        table.setBackground(colorBgCard);
        table.setSelectionBackground(colorTableSelected);
        table.setSelectionForeground(colorText);
        table.setIntercellSpacing(new Dimension(0, 1));
        
        // Header styling
        table.getTableHeader().setFont(font16Bold);
        table.getTableHeader().setBackground(colorTableHeader);
        table.getTableHeader().setForeground(colorText);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, colorPrimary));
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
    }

    /**
     * Create a card-style panel
     */
    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(colorBgCard);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBorder, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return card;
    }

    /**
     * Create a section title
     */
    public static JLabel createSectionTitle(String text) {
        JLabel title = new JLabel(text);
        title.setFont(font18Bold);
        title.setForeground(colorText);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        return title;
    }
}
