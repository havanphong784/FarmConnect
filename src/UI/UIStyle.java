package UI;

import javax.swing.*;
import java.awt.*;


public class UIStyle {
    // MÀu dùng chung
    public static Color colorPrimary = new Color(46, 125, 50);
    public static Color colorTextField = new Color(232, 240, 254);
    public static Color colorBg = new Color(248, 248, 250);
    public static Color colorText = new Color(30, 41, 59);
    public static Color colorLabel = new Color(71, 85, 105);
        // Các loại Font
    public static Font font16 = new Font("Segoe UI", Font.PLAIN, 16);
    public static Font font30 = new Font("Segoe UI", Font.PLAIN, 30);
    public static Font font20 = new Font("Segoe UI", Font.ITALIC, 20);

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
    public void setLabel(JLabel label , String text) {
        label.setText(text);
        label.setSize(300,40);
        label.setForeground(colorTextField);
        label.setBackground(colorPrimary);
    }
}
