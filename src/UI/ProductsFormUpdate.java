package UI;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ProductsFormUpdate extends ProductsFromInsert {
    private JLabel lblSale;
    private JTextField txtPricePercent;
    public ProductsFormUpdate(TableModel model, int row) {
        super();
        this.lblTitle.setText("Cập nhật thông tin sản phẩm !");
        this.btnSubmit.setText("Cập nhật");
        this.txtName.setText(String.valueOf(model.getValueAt(row,0)));
        this.txtPrice.setText(String.valueOf(model.getValueAt(row,1)));
        this.txtUnit.setText(String.valueOf(model.getValueAt(row,2)));
        this.txtQuantity.setText(String.valueOf(model.getValueAt(row,3)));
        this.txtDes.setText(String.valueOf(model.getValueAt(row,4)));
        remove(lblExpiry);
        remove(txtExpiry);


        // width nho lai de them sale
        lblPrice.setBounds(20, 140, 170, 22);
        add(this.lblPrice);

        txtPrice.setBounds(20, 165, 170, 40);
        add(this.txtPrice);

        this.lblSale = UIStyle.setLabel(this.lblSale,"Giảm giá (%):");
        this.lblSale.setBounds(210, 140, 170, 22);
        this.add(this.lblSale);

        this.txtPricePercent = UIStyle.setTextField(this.txtPricePercent,"");
        this.txtPricePercent.setBounds(210 ,165, 170, 40);
        this.add(this.txtPricePercent);

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
                    float pricePercent = Float.parseFloat(txtPricePercent.getText());
                    String des = txtDes.getText();

                    boolean isUpdated = Server.ProductsServer.updateProduct(name, des, quantity, price,pricePercent, unit);
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
