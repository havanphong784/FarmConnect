package UI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;


public class UIStyle {
    // MÀu dùng chung
    public static Color colorPrimary = new Color(46, 125, 50);
    public static Color colorTextField = new Color(232, 240, 254);
    public static Color colorBg = new Color(248, 248, 250);
    public static Color colorText = new Color(30, 41, 59);
    public static Color colorLabel = new Color(71, 85, 105);
    public static Color colorHeader = new Color(235, 236, 236);
        // Các loại Font
    public static Font font14 = new Font("Segoe UI", Font.PLAIN, 14);
    public static Font font16 = new Font("Segoe UI", Font.PLAIN, 16);
    public static Font font16Bold = new Font("Segoe UI", Font.BOLD, 16);
    public static Font font30 = new Font("Segoe UI", Font.PLAIN, 30);
    public static Font font20 = new Font("Segoe UI", Font.ITALIC, 20);
    public static Font fontEmoji = new Font("Segoe UI Emoji", Font.PLAIN, 14);

    // OptionPane
    public static void setDefaultsTheme() {
        UIManager.put("OptionPane.background", colorBg);
        UIManager.put("Panel.background", colorBg);
        UIManager.put("OptionPane.messageFont", font16);
        UIManager.put("OptionPane.buttonFont", font16Bold);
        UIManager.put("OptionPane.messageForeground", colorLabel);
        Border messagePad = BorderFactory.createEmptyBorder(10, 14, 6, 14);
        Border buttonPad  = BorderFactory.createEmptyBorder(8, 14, 12, 14);
        UIManager.put("OptionPane.messageAreaBorder", messagePad);
        UIManager.put("OptionPane.buttonAreaBorder", buttonPad);
    }

    // Btn chung
    public static JButton setBtnActive(JButton btn , String text) {
        btn = new JButton(text);
        btn.setSize(300,40);
        btn.setForeground(Color.white);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(font16);
        btn.setBackground(colorPrimary);
        btn.setBorder(new javax.swing.border.LineBorder(colorPrimary, 1, true));
        return btn;
    }

    // Text, pass field
    public static JTextField setTextField(JTextField textField , String text) {
        textField = new JTextField(text);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBg, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        textField.setBackground(colorTextField);
        textField.setForeground(new Color(25, 30, 35));
        textField.setCaretColor(new Color(25, 30, 35));
        return textField;
    }
    public static JPasswordField setPassField(JPasswordField passwordField) {
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBg, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        passwordField.setBackground(colorTextField);
        passwordField.setForeground(colorText);
        passwordField.setCaretColor(colorText);
        return passwordField;
    }
    // Label
    public static JLabel setLabel(JLabel label , String text) {
        label = new JLabel(text);
        label.setForeground(Color.white);
        label.setBackground(colorPrimary);
        label.setFont(font16);
        return label;
    }

    public static JButton setButtonDB(JButton button , String text) {
        button = new JButton(text);
        button.setPreferredSize(new Dimension(250,50));
        button.setMaximumSize(new Dimension(250,50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(font16);
        button.setBackground(colorPrimary);
        button.setForeground(Color.white);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(JButton.LEFT);
        return button;
    }
}
