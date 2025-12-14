package UI;

import DBConnect.UserDAO;
import Server.ProductsServer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import static UI.LoginFrame.username;

public class ProductsFromInsert extends JOptionPane {
    protected JLabel lblTitle,lblName,lblPrice,lblUnit,lblQuantiy,lblDes;
    protected JTextField txtName,txtPrice,txtUnit,txtQuantity,txtDes;
    protected JButton btnSubmit;

    public ProductsFromInsert() {
        this.setBackground(UIStyle.colorBg);
        this.setPreferredSize(new Dimension(400,500));
        this.setLayout(null);

        this.lblTitle = UIStyle.setLabel(this.lblTitle,"Nhập thông tin sản phẩm !");
        this.lblTitle.setFont(UIStyle.font20);
        this.lblTitle.setHorizontalAlignment(JLabel.CENTER);
        this.lblTitle.setBounds(0, 10, 400, 40);
        this.add(this.lblTitle);

        this.lblName = UIStyle.setLabel(this.lblName,"Tên sản phẩm :");
        this.lblName.setBounds(20, 65, 360, 22);
        this.add(this.lblName);

        this.txtName = UIStyle.setTextField(this.txtName,"");
        this.txtName.setBounds(20, 90, 360, 40);
        this.txtName.setFont(UIStyle.font14);
        this.add(this.txtName);

        this.lblPrice = UIStyle.setLabel(this.lblPrice,"Giá sản phẩm :");
        this.lblPrice.setBounds(20, 140, 360, 22);
        this.add(this.lblPrice);

        this.txtPrice = UIStyle.setTextField(this.txtPrice,"");
        this.txtPrice.setBounds(20, 165, 360, 40);
        this.txtPrice.setFont(UIStyle.font14);
        this.add(this.txtPrice);

        this.lblUnit = UIStyle.setLabel(this.lblUnit,"Đơn vị tính :");
        this.lblUnit.setBounds(20, 215, 360, 22);
        this.add(this.lblUnit);

        this.txtUnit = UIStyle.setTextField(this.txtUnit,"");
        this.txtUnit.setBounds(20, 240, 360, 40);
        this.txtUnit.setFont(UIStyle.font14);
        this.add(this.txtUnit);

        this.lblQuantiy = UIStyle.setLabel(this.lblQuantiy,"Số lượng :");
        this.lblQuantiy.setBounds(20, 290, 360, 22);
        this.add(this.lblQuantiy);

        this.txtQuantity = UIStyle.setTextField(this.txtQuantity,"");
        this.txtQuantity.setBounds(20, 315, 360, 40);
        this.txtQuantity.setFont(UIStyle.font14);
        this.add(this.txtQuantity);

        this.lblDes = UIStyle.setLabel(this.lblDes,"Mô tả sản phẩm :");
        this.lblDes.setBounds(20, 365, 360, 22);
        this.add(this.lblDes);

        this.txtDes = UIStyle.setTextField(this.txtDes,"");
        this.txtDes.setBounds(20, 390, 360, 40);
        this.txtDes.setFont(UIStyle.font14);
        this.add(this.txtDes);

        this.btnSubmit = UIStyle.setBtnActive(this.btnSubmit,"Thêm sản phẩm");
        this.btnSubmit.setBounds(20, 445, 360, 40);
        this.add(this.btnSubmit);

        // Action them san pham
        ActionListener insert = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtName.getText().isEmpty() || txtPrice.getText().isEmpty() || txtUnit.getText().isEmpty()
                        || txtQuantity.getText().isEmpty() || txtDes.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Vui lòng nhập đầy đủ thông tin sản phẩm !");
                    return;
                }

                try {
                    String name = txtName.getText().trim();
                    System.out.println(name);
                    BigDecimal price = new BigDecimal(txtPrice.getText().trim());
                    System.out.println(price);
                    String unit = txtUnit.getText().trim();
                    System.out.println(unit);
                    int quantity = Integer.parseInt(txtQuantity.getText().trim());
                    System.out.println(quantity);
                    String des = txtDes.getText().trim();
                    System.out.println(des);
                    int id = UserDAO.getUserIdByEmail(username);
                    System.out.println(id);

                    Boolean check = ProductsServer.convertToProduct(name,des,quantity,price,unit,id);
                    if (check) {
                        JOptionPane.showMessageDialog(null,"Thêm sản phẩm thành công !");
                    } else {
                        JOptionPane.showMessageDialog(null,"Thêm sản phẩm thất bại !");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"Giá sản phẩm và số lượng phải là số hợp lệ !");
                    return;

                }

            }
        };
        btnSubmit.addActionListener(insert);
    }
}
