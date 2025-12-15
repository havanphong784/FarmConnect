package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static UI.LoginFrame.username;

public class MainFrame extends JFrame  {
    private JPanel pnNavigation,pnContent,pnHeader,pnDes,pnCard;
    private JButton btnOne, btnTwo, btnThree, btnFour,btnHome;
    private JLabel lblNameApp,lblAvatar,lblRole,lblName;
    public MainFrame() {
        this.setSize(1200,850);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panel navigation bên trái
        this.pnNavigation = new JPanel();
        this.pnNavigation.setLayout(new BoxLayout(pnNavigation,BoxLayout.Y_AXIS));
        this.pnNavigation.setPreferredSize(new Dimension(240,0));
        this.pnNavigation.setBackground(UIStyle.colorPrimary);
        this.add(this.pnNavigation,BorderLayout.WEST);

        this.lblNameApp = UIStyle.setLabelPrimary(this.lblNameApp,"Farm Connect");
        this.lblNameApp.setFont(UIStyle.font30);
        this.lblNameApp.setPreferredSize(new Dimension(240,80));
        this.lblNameApp.setMaximumSize(new Dimension(240,80));
        this.lblNameApp.setHorizontalAlignment(JLabel.CENTER);
        this.lblNameApp.setVerticalAlignment(JLabel.CENTER);
        this.lblNameApp.setForeground(Color.white);
        this.pnNavigation.add(this.lblNameApp);

        this.btnOne = UIStyle.setButtonDB(this.btnOne,"Sản phẩm");
        this.pnNavigation.add(this.btnOne);
        this.btnTwo = UIStyle.setButtonDB(this.btnTwo,"Thống kê bán hàng");
        this.pnNavigation.add(this.btnTwo);
        this.btnThree = UIStyle.setButtonDB(this.btnThree,"Lịch sử bán hàng");
        this.pnNavigation.add(this.btnThree);

        this.pnNavigation.add(Box.createVerticalGlue());

        this.btnFour = UIStyle.setButtonDB(this.btnFour,"Đăng xuất");
        this.btnFour.setFont(UIStyle.font16);
        this.btnFour.setForeground(UIStyle.colorRed);
        this.btnFour.setVerticalAlignment(JLabel.TOP);
        this.btnFour.setHorizontalAlignment(JLabel.LEFT);
        this.pnNavigation.add(this.btnFour);

        // Panel content bên phải
        this.pnContent = new JPanel(new BorderLayout());

        this.pnHeader = new JPanel();
        this.pnHeader.setLayout(new BoxLayout(this.pnHeader,BoxLayout.X_AXIS));
        this.pnHeader.setBackground(UIStyle.colorHeader);

        this.btnHome = new JButton("🏠");
        this.btnHome.setFont(UIStyle.fontEmoji);
        this.btnHome.setBackground(UIStyle.colorHeader);
        this.btnHome.setForeground(UIStyle.colorText);
        this.btnHome.setFocusable(false);
        this.btnHome.setMaximumSize(new Dimension(120,40));
        this.btnHome.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        this.pnHeader.add(this.btnHome);
        this.pnNavigation.setVisible(false);
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pnNavigation.isVisible()) {
                    pnNavigation.setVisible(false);
                } else {
                    pnNavigation.setVisible(true);
                }
                revalidate();
                repaint();
            }
        });

        this.pnHeader.add(Box.createHorizontalGlue());

        this.pnDes = new JPanel();
        this.pnDes.setLayout(new BoxLayout(this.pnDes,BoxLayout.Y_AXIS));
        this.pnDes.setBackground(UIStyle.colorHeader);
        this.pnDes.setPreferredSize(new Dimension(300,80));

        this.lblName = new JLabel(username);
        this.lblName.setMaximumSize(new Dimension(300,24));
        this.lblName.setHorizontalAlignment(JLabel.RIGHT);
        this.lblName.setFont(UIStyle.font16);

        this.pnDes.add(this.lblName);

        this.lblRole = new JLabel("Admin");
        this.lblRole.setMaximumSize(new Dimension(300,24));
        this.lblRole.setHorizontalAlignment(JLabel.RIGHT);
        this.lblRole.setFont(UIStyle.font16);

        this.lblAvatar = new JLabel("");
        this.lblAvatar.setIcon(new ImageIcon(MainFrame.class.getResource("/Image/avatar.png")));
        this.lblAvatar.setMaximumSize(new Dimension(60,60));
        this.lblAvatar.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));


        this.pnDes.add(this.lblRole);
        this.pnDes.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

        this.pnHeader.add(this.pnDes);
        this.pnHeader.add(lblAvatar);

        this.pnContent.add(this.pnHeader,BorderLayout.NORTH);

        this.pnCard = new JPanel(new CardLayout());
        this.pnCard.add(new ProductListPanel(),"Products");
        this.pnCard.add(new StatisticsPanel(),"Statistics");
        this.pnCard.add(new HistoryPanel(),"History");
        CardLayout cl = (CardLayout) pnCard.getLayout();
        cl.show(pnCard, "Products");

        this.pnContent.add(this.pnCard,BorderLayout.CENTER);
        this.add(this.pnContent,BorderLayout.CENTER);




        // Action
        btnFour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });

        btnOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) pnCard.getLayout();
                cl.show(pnCard, "Products");
            }
        });

        btnTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) pnCard.getLayout();
                cl.show(pnCard, "Statistics");
            }
        });

        btnThree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) pnCard.getLayout();
                cl.show(pnCard, "History");
            }
        });
    }
}
