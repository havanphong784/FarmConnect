package UI;

import DBConnect.DBConnect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    protected JPanel pnLeft,pnRight;
    protected JLabel lblTitle,lblAvatar,lblAppName,lblUsername,lblPassword,lblOr;
    protected JTextField txtUsername;
    protected JPasswordField txtPassword;
    protected JButton btnLogin,btnRegister;
    protected ActionListener al;
    public LoginFrame() {
        this.setSize(1200,850);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        //Panel phía bên trái ( ảnh )
        this.pnLeft = new JPanel();
        this.pnLeft.setLayout(null);
        this.pnLeft.setBounds(0,0,720,850);
        JLabel img =  new JLabel("");
        img.setIcon(new ImageIcon(LoginFrame.class.getResource("/Image/login720x850.png")));
        img.setBounds(0,0,720,850);
        this.pnLeft.add(img);
        this.add(this.pnLeft);

        // Pane phía bên phải
        this.pnRight = new JPanel();
        this.pnRight.setLayout(null);
        this.pnRight.setBounds(720,0,480,850);
        this.pnRight.setBackground(UIStyle.colorBg);
        this.add(this.pnRight);

        this.lblAvatar = new JLabel("");
        this.lblAvatar.setIcon(new ImageIcon(LoginFrame.class.getResource("/Image/avatar.png")));
        this.lblAvatar.setBounds(50,80,60,60);
        this.lblAvatar.setBackground(UIStyle.colorBg);
        this.lblAvatar.setForeground(UIStyle.colorPrimary);
        this.pnRight.add(lblAvatar);

        this.lblAppName = new JLabel("Farm Connect");
        this.lblAppName.setFont(UIStyle.font30);
        this.lblAppName.setForeground(UIStyle.colorPrimary);
        this.lblAppName.setBounds(120,95,200,30);
        this.pnRight.add(lblAppName);

        this.lblTitle = new JLabel("Chào mừng đã trở lại !");
        this.lblTitle.setFont(UIStyle.font20);
        this.lblTitle.setForeground(UIStyle.colorPrimary);
        this.lblTitle.setBounds(60,180,300,30);
        this.pnRight.add(lblTitle);

        this.lblUsername = new JLabel("Tên đăng nhập");
        this.lblUsername.setFont(UIStyle.font16);
        this.lblUsername.setForeground(UIStyle.colorLabel);
        this.lblUsername.setBounds(80,250,200,30);
        this.pnRight.add(lblUsername);

        this.txtUsername = UIStyle.setTextField(this.txtUsername,"");
        this.txtUsername.setBounds(60,290,340,50);
        this.txtUsername.setFont(UIStyle.font16);
        this.pnRight.add(txtUsername);

        this.lblPassword = new JLabel("Mật khẩu");
        this.lblPassword.setFont(UIStyle.font16);
        this.lblPassword.setForeground(UIStyle.colorLabel);
        this.lblPassword.setBounds(80,360,200,30);
        this.pnRight.add(lblPassword);

        this.txtPassword = UIStyle.setPassField(txtPassword);
        this.txtPassword.setBounds(60,400,340,50);
        this.txtPassword.setFont(UIStyle.font16);
        this.pnRight.add(txtPassword);

        this.btnLogin = UIStyle.setBtnActive(this.btnLogin,"Đăng nhập");
        this.btnLogin.setBounds(60,520,340,50);
        this.pnRight.add(btnLogin);

        this.lblOr = new JLabel("Hoặc");
        this.lblOr.setFont(UIStyle.font16);
        this.lblOr.setForeground(UIStyle.colorLabel);
        this.lblOr.setBounds(210,580,50,40);
        this.pnRight.add(lblOr);

        this.btnRegister = UIStyle.setBtnActive(this.btnRegister,"Đăng ký");
        this.btnRegister.setBounds(60,630,340,50);
        this.pnRight.add(btnRegister);


        // Acition Lestener
         al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame().setVisible(true);
            }
        };
        this.btnRegister.addActionListener(al);

        btnLogin.addActionListener(e -> {
            if (txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
                JOptionPane.showMessageDialog(null,"Vui lòng nhập Tên đăng nhập và Password");
                return;
            }
            String sql = "SELECT PasswordHash, Role FROM Users WHERE Email=?";
            try (Connection con = DBConnect.getConnection();
                 PreparedStatement st = con.prepareStatement(sql)) {

                st.setString(1, txtUsername.getText().trim());

                try (ResultSet rs = st.executeQuery()) {

                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(null, "User không tồn tại!");
                        return;
                    }

                    String pass = new String(txtPassword.getPassword());
                    if (!rs.getString("PasswordHash").equals(pass)) {
                        JOptionPane.showMessageDialog(null, "Sai mật khẩu.");
                        return;
                    }

                    String role = rs.getString("Role");
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công.");

                    if ("Admin".equals(role)) System.out.println("ADMIN");
                    else if ("User".equals(role)) System.out.println("USER");
                    else if ("Farm".equals(role)) System.out.println("FARM");
                    else System.out.println("Role không hợp lệ: " + role);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }
}
