package UI;

import DBConnect.UserDAO;
import Server.ProductsServer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static UI.LoginFrame.userid;
import static UI.LoginFrame.username;

public class ProductsFromInsert extends JOptionPane {
    protected JLabel lblTitle,lblName,lblPrice,lblUnit,lblQuantiy,lblDes,lblExpiry;
    protected JTextField txtName,txtPrice,txtUnit,txtQuantity,txtDes,txtExpiry;
    protected JButton btnSubmit;

    public ProductsFromInsert() {
        this.setBackground(UIStyle.colorBg);
        this.setPreferredSize(new Dimension(400,585));
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

        this.lblExpiry = UIStyle.setLabel(this.lblExpiry,"Hạn sử dụng (ngày) :");
        this.lblExpiry.setBounds(20, 440, 360, 22);
        this.add(this.lblExpiry);

        this.txtExpiry = UIStyle.setTextField(this.txtExpiry,"");
        this.txtExpiry.setBounds(20, 465, 360, 40);
        this.txtExpiry.setFont(UIStyle.font14);
        this.add(this.txtExpiry);

        this.btnSubmit = UIStyle.setBtnActive(this.btnSubmit,"Thêm sản phẩm");
        this.btnSubmit.setBounds(20, 525, 360, 40);
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
                    BigDecimal price = new BigDecimal(txtPrice.getText().trim());
                    String unit = txtUnit.getText().trim();
                    int quantity = Integer.parseInt(txtQuantity.getText().trim());
                    String des = txtDes.getText().trim();

                    int day = Integer.parseInt(txtExpiry.getText().trim());
                    System.out.println(day);
                    Instant time = Instant.now();
                    System.out.println(time);
                    Timestamp expirationDate = Timestamp.from(time.plus(day,ChronoUnit.DAYS));
                    System.out.println(expirationDate);

                    Boolean check = ProductsServer.insertProduct(name,des,quantity,price,unit,expirationDate,userid);
                    if (check) {
                        JOptionPane.showMessageDialog(null,"Thêm sản phẩm thành công !");
                    } else {
                        JOptionPane.showMessageDialog(null,"Thêm sản phẩm thất bại !");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,"Lỗi định dạng !");
                    return;
                }

            }
        };
        btnSubmit.addActionListener(insert);
    }
}
