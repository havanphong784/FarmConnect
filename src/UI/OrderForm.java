package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrderForm extends JOptionPane{
    private JTextArea txtOrder;
    private JButton btn;
    public OrderForm(DefaultTableModel model , int[] row) {
        txtOrder = new JTextArea();
        txtOrder.setEditable(false);
        txtOrder.setLineWrap(true);
        txtOrder.setWrapStyleWord(true);
        txtOrder.setBackground(UIStyle.colorBg);
        txtOrder.setSize(500,700);
        add(txtOrder,BorderLayout.CENTER);
        setLayout(new BorderLayout());
    }
}
