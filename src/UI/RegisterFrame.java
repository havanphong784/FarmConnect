package UI;

import DBConnect.GetFuction;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterFrame extends LoginFrame {
    private JLabel lblAgainPass,lblEmail;
    private JPasswordField txtAgainPass;
    private JTextField txtEmail;
    private JButton btnBack;
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


//        this.cmbRole.setBounds(60, 690, 150, 40);

        // Nút Đăng ký dưới combo, full chiều rộng như ô nhập
        btnRegister.setBounds(60, 690, 340, 50);
        pnRight.add(this.btnRegister);

        //nut back
        this.btnBack = UIStyle.setBtnActive(this.btnBack, "Back");
        btnBack.setBounds(60,770,100,30);
        pnRight.add(btnBack);

        // Action Listener
        this.btnRegister.removeActionListener(al);
        this.btnRegister.addActionListener(new ActionListener() {
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
                Boolean kt = GetFuction.chekRegistration(username, password, email);
                if (kt) {
                    new LoginFrame().setVisible(true);
                    setVisible(false);
                    dispose();
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }
}
