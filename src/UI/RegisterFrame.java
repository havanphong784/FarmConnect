package UI;

import DBConnect.DBConnect;
import DBConnect.GetFuction;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFrame extends LoginFrame {
    private JLabel lblAgainPass,lblEmail;
    private JPasswordField txtAgainPass;
    private JTextField txtEmail;
    private JComboBox<String> cmbRole;
    public RegisterFrame() {
        super();
        pnRight.remove(btnLogin);
        pnRight.remove(lblOr);

        lblTitle.setText("Vui lòng nhập thông tin !");

        this.lblAgainPass = new JLabel("Nhập lại mật khẩu");
        this.lblAgainPass.setFont(UIStyle.font16);
        // dưới ô Mật khẩu
        this.lblAgainPass.setBounds(80, 470, 340, 30);
        pnRight.add(lblAgainPass);

        this.txtAgainPass = UIStyle.setPassField(this.txtAgainPass);
        this.txtAgainPass.setFont(UIStyle.font16);
        this.txtAgainPass.setBounds(60, 510, 340, 50);
        pnRight.add(txtAgainPass);

        this.lblEmail = new JLabel("Email");
        this.lblEmail.setFont(UIStyle.font16);
        this.lblEmail.setBounds(80, 580, 340, 30);
        pnRight.add(lblEmail);

        this.txtEmail = UIStyle.setTextField(this.txtEmail,"");
        this.txtEmail.setFont(UIStyle.font16);
        this.txtEmail.setBounds(60, 620, 340, 50);
        pnRight.add(txtEmail);

        // Combo role dưới Email
        String[] role = new String[] {"Customer","Farmer"};
        this.cmbRole = new JComboBox<>(role);
        this.cmbRole.setFont(UIStyle.font16);
        this.cmbRole.setBounds(60, 690, 150, 40);
        this.cmbRole.setForeground(UIStyle.colorText);
        this.cmbRole.setBackground(UIStyle.colorTextField);
        pnRight.add(this.cmbRole);

        // Nút Đăng ký dưới combo, full chiều rộng như ô nhập
        btnRegister.setBounds(60, 740, 340, 50);
        pnRight.add(this.btnRegister);

        // Action Listener
        btnRegister.removeActionListener(al);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText().trim();
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();
                String againPass = new String(txtAgainPass.getPassword()).trim();

                // 1. Kiểm tra rỗng
                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || againPass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                // 2. Kiểm tra khớp mật khẩu
                if (!againPass.equals(password)) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu nhập lại không trùng khớp!");
                    return;
                }

                // 3. Kiểm tra định dạng email (ở đây ví dụ chỉ cho gmail)
                if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
                    JOptionPane.showMessageDialog(null, "Email không hợp lệ (chỉ chấp nhận @gmail.com)!");
                    return;
                }
                // 4. Câu lệnh SQL tương ứng với bảng Users trong SQL Server
                Boolean kt = GetFuction.chekRegistration(username, password, email);
            }
        });

    }
}
