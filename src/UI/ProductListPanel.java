package UI;

import Server.ProductsServer;
import DBConnect.ProductsDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductListPanel extends JPanel {
    protected JTable table;
    protected JTextField txtSearch;
    protected JComboBox<String> cmbArrangement, cmbType;
    protected JButton btnSearch,btnAdd,btnUpdate,btnSell;
    protected JPanel pnTop, pnCenter,pnBottom;
    protected JScrollPane scrollPane;
    protected  DefaultTableModel model;
    public static String nameSearch;
    public Object[][] data;
    public String[] cols;
    public ProductListPanel() {
        this.setLayout(new BorderLayout(5, 5));
        this.setBackground(UIStyle.colorBg);
        this.setBorder(BorderFactory.createEmptyBorder(10,40,10,30));

        // Panel Top
        this.pnTop = new JPanel();
        this.pnTop.setLayout(new BoxLayout(this.pnTop, BoxLayout.X_AXIS));
        this.pnTop.setBackground(UIStyle.colorBg);
        this.pnTop.setPreferredSize(new Dimension(0, 50));
        this.txtSearch = UIStyle.setTextField(this.txtSearch,"");
        this.txtSearch.setFont(UIStyle.font16);
        this.txtSearch.setMaximumSize(new Dimension(400, 50));
        this.txtSearch.setPreferredSize(new Dimension(240, 50));
        this.pnTop.add(this.txtSearch);
        this.cmbType = new JComboBox<>();
        this.cmbType.addItem("Tất cả");
        for (String type : ProductsDAO.getTypes()) {
            this.cmbType.addItem(type);
        }
        this.cmbType.setPreferredSize(new Dimension(150, 30));
        this.cmbType.setMaximumSize(new Dimension(150, 30));
        this.cmbType.setFont(UIStyle.font16);
        this.cmbType.setBackground(UIStyle.colorHeader);
        this.pnTop.add(Box.createHorizontalStrut(10));
        this.pnTop.add(this.cmbType);
        this.pnTop.add(Box.createHorizontalStrut(10));
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
        this.cmbArrangement.setBackground(UIStyle.colorHeader);
        this.cmbArrangement.setFont(UIStyle.font16);
        this.pnTop.add(this.cmbArrangement);
        this.pnTop.add(Box.createHorizontalStrut(10));
        this.add(this.pnTop, BorderLayout.NORTH);

        //panel center

        this.pnCenter = new JPanel(new BorderLayout());
        cols = new String[]{"Tên", "Giá","Đơn Vị","Tồn Kho","Mô Tả"};
        data = ProductsServer.toTableData(ProductsDAO.getAll());
        model = new  DefaultTableModel(data,cols) {};
        this.table = new JTable(model) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        // su kien khi thay doi xap xep
        this.cmbArrangement.addActionListener(e -> refreshTable());

        this.cmbType.addActionListener(e -> refreshTable());

        this.btnSearch.addActionListener(e -> refreshTable());

        table.setDefaultEditor(Object.class, null);
        table.setFont(UIStyle.font16);
        table.setBackground(UIStyle.colorBg);
        table.setRowHeight(40);
        table.setAutoCreateRowSorter(true);
        table.setShowVerticalLines(false);
        UIStyle.styleTable(table); // Apply modern table style
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
        this.btnAdd = UIStyle.setBtnActive(this.btnAdd, "Them");
        this.btnAdd.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        this.pnBottom.add(this.btnAdd);
        this.pnBottom.add(Box.createHorizontalStrut(20));
        this.btnUpdate = UIStyle.setBtnSecondary(this.btnUpdate, "Cap nhat");
        this.pnBottom.add(this.btnUpdate);
        this.pnBottom.add(Box.createHorizontalStrut(20));
        this.btnSell = UIStyle.setBtnActive(this.btnSell, "Ban hang");
        this.btnSell.setBackground(UIStyle.colorInfo);
        this.pnBottom.add(this.btnSell);


        this.pnBottom.add(Box.createHorizontalStrut(40));
        this.add(this.pnBottom, BorderLayout.SOUTH);

        this.btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductsFromInsert form = new ProductsFromInsert();
                JDialog dialog = new JDialog();
                dialog.setTitle("Them san pham");
                dialog.setModal(true);
                dialog.setContentPane(form);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                refreshTable();
            }
        });

        this.btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexRow = table.getSelectedRow();
                int row = table.convertRowIndexToModel(indexRow);
                TableModel model = table.getModel();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Vui long chon san pham can cap nhat!");
                    return;
                }
                ProductsFormUpdate form = new ProductsFormUpdate(model, row);
                JDialog dialog = new JDialog();
                dialog.setTitle("Cap nhat san pham");
                dialog.setModal(true);
                dialog.setContentPane(form);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                refreshTable();
            }
        });

        this.btnSell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexRow = table.getSelectedRow();
                int row = table.convertRowIndexToModel(indexRow);
                TableModel model = table.getModel();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "Vui long chon san pham can ban!");
                    return;
                }
                ProductsFormSell form = new ProductsFormSell(row, model);
                JDialog dialog = new JDialog();
                dialog.setTitle("Ban hang");
                dialog.setModal(true);
                dialog.setContentPane(form);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                refreshTable();
                HistoryPanel.refreshOrdersTable();
            }
        });
    }
    protected void refreshTable() {
        String nameSearch = txtSearch.getText().trim();
        String selected = String.valueOf(cmbArrangement.getSelectedItem());
        String selectedType = String.valueOf(cmbType.getSelectedItem());
        Object[][] newData;

        switch (selected) {
            case "Xắp xếp theo tên tăng dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderNameASC(nameSearch, selectedType));
                break;
            case "Xắp xếp theo tên giảm dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderNameDESC(nameSearch, selectedType));
                break;
            case "Xắp xếp theo số lượng tăng dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderQuantityASC(nameSearch, selectedType));
                break;
            case "Xắp xếp theo số lượng giảm dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderQuantityDESC(nameSearch, selectedType));
                break;
            case "Xắp xếp theo giá tăng dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderPriceASC(nameSearch, selectedType));
                break;
            case "Xắp xếp theo giá giảm dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderPriceDESC(nameSearch, selectedType));
                break;
            case "Chọn kiểu sắp xếp":
            default:
                newData = ProductsServer.toTableData(ProductsDAO.searchOderNameASC(nameSearch, selectedType));
                break;
        }

        model.setDataVector(newData, cols);
    }
}
