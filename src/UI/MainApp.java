package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame  {
    private JPanel pnNavigation,pnContent,pnHeader,pnDes,pnBtnAction;
    private JButton btnOne, btnTwo, btnThree, btnFour;
    private JLabel lblNameApp,lblAvatar,lblRole,lblName;
    public MainApp() {
        this.setSize(1200,850);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panel navigation bên phải
        this.pnNavigation = new JPanel();
        this.pnNavigation.setLayout(new BoxLayout(pnNavigation,BoxLayout.Y_AXIS));
        this.pnNavigation.setPreferredSize(new Dimension(240,0));
        this.pnNavigation.setBackground(UIStyle.colorPrimary);
        this.add(this.pnNavigation,BorderLayout.WEST);

        this.lblNameApp = UIStyle.setLabel(this.lblNameApp,"Farm Connect");
        this.lblNameApp.setFont(UIStyle.font30);
        this.lblNameApp.setPreferredSize(new Dimension(240,80));
        this.lblNameApp.setMaximumSize(new Dimension(240,80));
        this.lblNameApp.setHorizontalAlignment(JLabel.CENTER);
        this.lblNameApp.setVerticalAlignment(JLabel.TOP);
        this.pnNavigation.add(this.lblNameApp);

        this.btnOne = UIStyle.setButtonDB(this.btnOne,"Sản phẩm");
        this.pnNavigation.add(this.btnOne);
        this.btnTwo = UIStyle.setButtonDB(this.btnTwo,"Login");
        this.pnNavigation.add(this.btnTwo);
        this.btnThree = UIStyle.setButtonDB(this.btnThree,"About");
        this.pnNavigation.add(this.btnThree);

        this.pnNavigation.add(Box.createVerticalGlue());

        this.btnFour = UIStyle.setButtonDB(this.btnFour,"Đăng xuất");
        this.btnFour.setFont(UIStyle.font16);
        this.btnFour.setVerticalAlignment(JLabel.TOP);
        this.btnFour.setHorizontalAlignment(JLabel.CENTER);
        this.pnNavigation.add(this.btnFour);

        // Panel content bên trái
        this.pnContent = new JPanel(new BorderLayout());
        this.pnHeader = new JPanel();
        this.pnHeader.setLayout(new BoxLayout(this.pnHeader,BoxLayout.X_AXIS));
        this.pnHeader.setBackground(UIStyle.colorHeader);
        this.pnHeader.add(Box.createHorizontalGlue());
        this.pnDes = new JPanel();
        this.pnDes.setLayout(new BoxLayout(this.pnDes,BoxLayout.Y_AXIS));
        this.pnDes.setBackground(UIStyle.colorHeader);
        this.pnDes.setPreferredSize(new Dimension(300,80));
        this.lblName = new JLabel("Nguyen Van A");
        this.lblName.setMaximumSize(new Dimension(300,24));
        this.lblName.setHorizontalAlignment(JLabel.RIGHT);
        this.lblName.setFont(UIStyle.font16);
        this.pnDes.add(this.lblName);
        this.lblRole = new JLabel("Admin");
        this.lblRole.setMaximumSize(new Dimension(300,24));
        this.lblRole.setHorizontalAlignment(JLabel.RIGHT);
        this.lblRole.setFont(UIStyle.font16);
        this.lblAvatar = new JLabel("Avatar");
        this.lblAvatar.setMaximumSize(new Dimension(60,60));
        this.lblAvatar.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));


        this.pnDes.add(this.lblRole);
        this.pnDes.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
        this.pnHeader.add(this.pnDes);
        this.pnHeader.add(lblAvatar);
        this.pnContent.add(this.pnHeader,BorderLayout.NORTH);
        this.add(this.pnContent,BorderLayout.CENTER);

        //Panel khi bam cac btn
        this.pnBtnAction = new ProductListPanel();
        pnContent.add(this.pnBtnAction,BorderLayout.CENTER);



        // Action
        btnFour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        btnOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
