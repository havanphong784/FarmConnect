package UI;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ProductsFormUpdate extends ProductsFromInsert {
    public ProductsFormUpdate(TableModel model, int row) {
        super();
        this.lblTitle.setText("Cập nhật thông tin sản phẩm !");
        this.btnSubmit.setText("Cập nhật");
        this.txtName.setText(String.valueOf(model.getValueAt(row,0)));
        this.txtPrice.setText(String.valueOf(model.getValueAt(row,1)));
        this.txtUnit.setText(String.valueOf(model.getValueAt(row,2)));
        this.txtQuantity.setText(String.valueOf(model.getValueAt(row,3)));
        this.txtDes.setText(String.valueOf(model.getValueAt(row,4)));

        // khong cho sửa tên sản phẩm
        this.txtName.setEditable(false);
        this.txtName.setForeground(UIStyle.colorPrimary);

        // Action
        this.btnSubmit.removeActionListener(this.btnSubmit.getActionListeners()[0]);
        this.btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = txtName.getText();
                    BigDecimal price = new BigDecimal(txtPrice.getText());
                    String unit = txtUnit.getText();
                    int quantity = Integer.parseInt(txtQuantity.getText());
                    String des = txtDes.getText();

                    boolean isUpdated = Server.ProductsServer.updateProduct(name, des, quantity, price, unit);
                    if (isUpdated) {
                        JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thành công !");
                        SwingUtilities.getWindowAncestor(ProductsFormUpdate.this).dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Cập nhật sản phẩm thất bại !");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số cho giá và số lượng !");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + ex.getMessage());
                }
            }
        });
    }
}
