package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends LoginFrame {
    private JLabel lblAgainPass,lblEmail;
    private JPasswordField txtAgainPass;
    private JTextField txtEmail;
    public RegisterFrame() {
        super();
        pnRight.remove(btnLogin);
        pnRight.remove(lblOr);

        lblTitle.setText("Vui lòng nhập thông tin !");

        txtUsername.setText("Tên đùng để đăng nhập");

        this.lblAgainPass = new JLabel("Nhập lại mật khẩu");
        this.lblAgainPass.setFont(UIStyle.font16);
        this.lblAgainPass.setBounds(80,460,340,50);
        pnRight.add(lblAgainPass);

        this.txtAgainPass = UIStyle.setPassField(this.txtAgainPass);
        this.txtAgainPass.setFont(UIStyle.font16);
        this.txtAgainPass.setBounds(60,520,340,50);
        pnRight.add(txtAgainPass);

        this.lblEmail = new JLabel("Email");
        this.lblEmail.setFont(UIStyle.font16);
        this.lblEmail.setBounds(80,580,340,50);
        pnRight.add(lblEmail);

        this.txtEmail = UIStyle.setTextField(this.txtEmail,"Email");
        this.txtEmail.setFont(UIStyle.font16);
        this.txtEmail.setBounds(60,640,340,50);
        pnRight.add(txtEmail);

        btnRegister.setBounds(60,740,340,50);
        pnRight.add(this.btnRegister);

        // Action Listener
        btnRegister.removeActionListener(al);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }
}
