package UI;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductsFormSell extends ProductsFromInsert {
    public  ProductsFormSell(int row , TableModel model) {
        super();
        this.lblTitle.setText("Nhập số lượng sản phẩm cần bán !");
        this.setPreferredSize(new Dimension(400,435));
        this.remove(lblDes);
        this.remove(txtDes);
        this.txtName.setText(model.getValueAt(row,0).toString());
        this.txtName.setEditable(false);
        this.txtName.setForeground(UIStyle.colorPrimary);

        this.txtPrice.setText(model.getValueAt(row,1).toString());
        this.txtPrice.setEditable(false);
        this.txtPrice.setForeground(UIStyle.colorPrimary);

        this.txtUnit.setText(model.getValueAt(row,2).toString());
        this.txtUnit.setEditable(false);
        this.txtUnit.setForeground(UIStyle.colorPrimary);

        this.txtQuantity.setText(model.getValueAt(row,3).toString());
        this.btnSubmit.setText("Bán hàng");
        this.btnSubmit.setBounds(20,380,360,40);
        this.add(btnSubmit);

        this.btnSubmit.removeActionListener(this.btnSubmit.getActionListeners()[0]);
        this.btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
