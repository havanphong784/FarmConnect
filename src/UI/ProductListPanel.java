package UI;

import Server.ProductsServer;
import DBConnect.ProductsDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductListPanel extends JPanel {
    protected JTable table;
    protected JTextField txtSearch;
    protected JComboBox<String> cmbArrangement;
    protected JButton btnSearch,btnAdd,btnUpdate;
    protected JPanel pnTop, pnCenter,pnBottom;
    protected JScrollPane scrollPane;
    public Object[][] data;
    public String[] cols;
    public ProductListPanel() {
        this.setLayout(new BorderLayout(5, 5));
        this.setBackground(UIStyle.colorBg);
        this.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));

        // Panel Top
        this.pnTop = new JPanel();
        this.pnTop.setLayout(new BoxLayout(this.pnTop, BoxLayout.X_AXIS));
        this.pnTop.setBackground(UIStyle.colorBg);
        this.pnTop.setPreferredSize(new Dimension(0, 50));
        this.txtSearch = UIStyle.setTextField(this.txtSearch,"Nhập");
        this.txtSearch.setFont(UIStyle.font16);
        this.txtSearch.setMaximumSize(new Dimension(400, 50));
        this.txtSearch.setPreferredSize(new Dimension(240, 50));
        this.pnTop.add(this.txtSearch);
        this.btnSearch = new JButton("Tìm kiếm");
        this.btnSearch.setPreferredSize(new Dimension(100, 50));
        this.btnSearch.setMaximumSize(new Dimension(100, 50));
        this.btnSearch.setBackground(UIStyle.colorBg);
        this.btnSearch.setFont(UIStyle.font16);
        this.btnSearch.setFocusPainted(false);
        this.btnSearch.setBorderPainted(false);
        this.pnTop.add(this.btnSearch);
        this.pnTop.add(Box.createHorizontalGlue());
        String[] xs = {
                "Chọn kiểu sắp xếp",
                "Xắp xếp theo tên tăng dần",
                "Xắp xếp theo tên giảm dần",
                "Xắp xếp theo số lượng tăng dần",
                "Xắp xếp theo số lượng giảm dần",
                "Xắp xếp theo giá tăng dần",
                "Xắp xếp theo giá giảm dần"
        };
        this.cmbArrangement =  new JComboBox<>(xs);
        this.cmbArrangement.setMaximumSize(new Dimension(300, 30));
        this.cmbArrangement.setBackground(new Color(158, 248, 121));
        this.cmbArrangement.setFont(UIStyle.font16);
        this.pnTop.add(this.cmbArrangement);
        this.pnTop.add(Box.createHorizontalStrut(10));
        this.add(this.pnTop, BorderLayout.NORTH);

        //panel center

        this.pnCenter = new JPanel(new BorderLayout());
        cols = new String[]{"Tên", "Giá","Đơn vị","Tồn kho","Mô tả"};
        data = ProductsServer.toTableData(ProductsDAO.getAll());
        DefaultTableModel model = new  DefaultTableModel(data,cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true; // set width theo kich thuoc cua ScrollPane
            }
        };

        // su kien khi thay doi xap xep
        this.cmbArrangement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = String.valueOf(cmbArrangement.getSelectedItem());

                Object[][] newData;

                switch (selected) {
                    case "Xắp xếp theo tên tăng dần":
                        newData = ProductsServer.toTableData(ProductsDAO.getOderNameASC());
                        break;
                    case "Xắp xếp theo tên giảm dần":
                        newData = ProductsServer.toTableData(ProductsDAO.getOderNameDESC());
                        break;
                    case "Xắp xếp theo số lượng tăng dần":
                        newData = ProductsServer.toTableData(ProductsDAO.getOderQuantityASC());
                        break;
                    case "Xắp xếp theo số lượng giảm dần":
                        newData = ProductsServer.toTableData(ProductsDAO.getOderQuantityDESC());
                        break;
                    case "Xắp xếp theo giá tăng dần":
                        newData = ProductsServer.toTableData(ProductsDAO.getOderPriceASC());
                        break;
                    case "Xắp xếp theo giá giảm dần":
                        newData = ProductsServer.toTableData(ProductsDAO.getOderPriceDESC());
                        break;
                    default:
                        newData = ProductsServer.toTableData(ProductsDAO.getAll());
                }

                model.setDataVector(newData, cols); // cập nhật dữ liệu + header
                // không cần repaint() thủ công
            }
        });

        this.btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameSearch = txtSearch.getText();
                String selected = String.valueOf(cmbArrangement.getSelectedItem());
                Object[][] newData;
                switch (selected) {
                    case "Chọn kiểu sắp xếp":
                        newData = ProductsServer.toTableData(ProductsDAO.searchOderNameASC(nameSearch));
                        break;
                    case "Xắp xếp theo tên tăng dần":
                        newData = ProductsServer.toTableData(ProductsDAO.searchOderNameASC(nameSearch));
                        break;
                    case "Xắp xếp theo tên giảm dần":
                        newData = ProductsServer.toTableData(ProductsDAO.searchOderNameDESC(nameSearch));
                        break;
                    case "Xắp xếp theo số lượng tăng dần":
                        newData = ProductsServer.toTableData(ProductsDAO.searchOderQuantityASC(nameSearch));
                        break;
                    case "Xắp xếp theo số lượng giảm dần":
                        newData = ProductsServer.toTableData(ProductsDAO.searchOderQuantityDESC(nameSearch));
                        break;
                    case "Xắp xếp theo giá tăng dần":
                        newData = ProductsServer.toTableData(ProductsDAO.searchOderPriceASC(nameSearch));
                        break;
                    case "Xắp xếp theo giá giảm dần":
                        newData = ProductsServer.toTableData(ProductsDAO.searchOderPriceDESC(nameSearch));
                        break;
                    default:
                        newData = ProductsServer.toTableData(ProductsDAO.getAll());
                }

                model.setDataVector(newData, cols); // cập nhật dữ liệu + header
                // không cần repaint() thủ công
            }
        });

        this.table.setDefaultEditor(Object.class, null);
        this.table.setFont(UIStyle.font16);
        this.table.setBackground(UIStyle.colorBg);
        this.table.setRowHeight(25);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPane.setPreferredSize(new Dimension(1400, 770));
        this.pnCenter.add(this.scrollPane);
        this.add(this.pnCenter, BorderLayout.CENTER);


        // pane bottom
        this.pnBottom = new JPanel();
        this.pnBottom.setBackground(UIStyle.colorBg);
        this.pnBottom.setPreferredSize(new Dimension(900,40));
        this.pnBottom.setLayout(new BoxLayout(this.pnBottom, BoxLayout.X_AXIS));
        this.pnBottom.add(Box.createHorizontalGlue());
        this.btnAdd = UIStyle.setBtnActive(this.btnAdd,"Thêm");
        this.btnAdd.setBorder(BorderFactory.createEmptyBorder(6,16,6,16));
        this.pnBottom.add(this.btnAdd);
        this.pnBottom.add(Box.createHorizontalStrut(20));
        this.btnUpdate = UIStyle.setBtnActive(this.btnUpdate,"Bán");
        this.btnUpdate.setBorder(BorderFactory.createEmptyBorder(6,16,6,16));
        this.btnUpdate.setBackground(UIStyle.colorRed);
        this.pnBottom.add(this.btnUpdate);

        this.pnBottom.add(Box.createHorizontalStrut(40));
        this.add(this.pnBottom, BorderLayout.SOUTH);
    }
}
