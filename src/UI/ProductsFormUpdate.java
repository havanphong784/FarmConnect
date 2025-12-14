package UI;

import javax.swing.*;
import javax.swing.table.TableModel;

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

        // khong cho sửa tên sản phẩm , gia , đơn vị (set lai mau xanh cho de nhin)
        this.txtName.setEditable(false);
        this.txtName.setForeground(UIStyle.colorPrimary);
        this.txtPrice.setEditable(false);
        this.txtPrice.setForeground(UIStyle.colorPrimary);
        this.txtUnit.setEditable(false);
        this.txtUnit.setForeground(UIStyle.colorPrimary);
    }
}
