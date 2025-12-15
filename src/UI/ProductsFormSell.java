package UI;

import DBConnect.ProductsDAO;
import Server.OrderServer;
import static UI.LoginFrame.userid;

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
                int quantity ;
                int orderQuantity;
                String proName = model.getValueAt(row,0).toString();
                int productid = ProductsDAO.getProductIdByName(proName);
                try {
                    quantity = Integer.parseInt(model.getValueAt(row,3).toString());
                    orderQuantity = Integer.parseInt(txtQuantity.getText());
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số cho số lượng !");
                    return;
                }
                if (orderQuantity > quantity) {
                    JOptionPane.showMessageDialog(null, "Số lượng bán phải lớn hơn 0 và < = số lượng tồn kho( " + quantity + " )!");
                    return;
                }

                boolean kt = (
                        OrderServer.insertOrder(userid,orderQuantity,productid)
                        && Server.ProductsServer.updateCell(quantity-orderQuantity,productid)
                );
                if (kt) {
                    JOptionPane.showMessageDialog(null, "Bán hàng thành công !");
                } else {
                    JOptionPane.showMessageDialog(null, "Bán hàng thất bại !");
                }

            }
        });
    }
}
