package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductListPanel extends JPanel {
    private JTable table;
    private JTextField txtSearch;
    private JComboBox<String> cmbArrangement;
    private JButton btnSearch,btnAdd,btnUpdate;
    private JPanel pnTop, pnCenter,pnBottom;
    private JScrollPane scrollPane;
    public ProductListPanel() {
        this.setLayout(new BorderLayout(5, 5));
        this.setBackground(UIStyle.colorBg);
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

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
                "Xắp xếp theo tên tăng dần",
                "Xắp xếp theo tên giảm dần",
                "Xắp xếp theo số lượng tăng dần",
                "Xắp xếp theo số lượng giảm dần",
                "Xắp xếp theo giá tăng dần",
                "Xắp xếp theo giá giảm dần"
        };
        this.cmbArrangement =  new JComboBox<>(xs);
        this.cmbArrangement.setMaximumSize(new Dimension(300, 30));
        this.cmbArrangement.setBackground(UIStyle.colorBg);
        this.cmbArrangement.setFont(UIStyle.font16);
        this.pnTop.add(this.cmbArrangement);
        this.pnTop.add(Box.createHorizontalStrut(10));
        this.add(this.pnTop, BorderLayout.NORTH);

        //panel center
        this.pnCenter = new JPanel(new BorderLayout());
        String[] cols = {"Mã", "Tên", "Số lượng", "Giá"};
        Object[][] data = {
                {"SP01", "Bút", 10, 5000},
                {"SP02", "Vở", 20, 12000},
                {"SP03", "Thước", 5, 8000},
                {"SP04", "Tẩy", 15, 3000},
                {"SP05", "Sổ tay", 12, 25000},
                {"SP06", "Bìa hồ sơ", 30, 6000},
                {"SP07", "Kẹp giấy", 100, 1500},
                {"SP08", "Bút chì", 25, 4000},
                {"SP09", "Bút bi xanh", 40, 5000},
                {"SP10", "Bút bi đỏ", 35, 5000},
                {"SP11", "Bút highlight", 18, 12000},
                {"SP12", "Gôm", 22, 3000},
                {"SP13", "Compa", 7, 18000},
                {"SP14", "Thước 20cm", 16, 7000},
                {"SP15", "Thước 30cm", 14, 9000},
                {"SP16", "Giấy A4", 50, 65000},
                {"SP17", "Giấy note", 45, 10000},
                {"SP18", "Bì thư", 60, 2000},
                {"SP19", "Tập giấy", 28, 11000},
                {"SP20", "Bảng kẹp", 9, 35000},
                {"SP21", "Dao rọc giấy", 11, 15000},
                {"SP22", "Kéo", 8, 22000},
                {"SP23", "Hồ dán", 19, 9000},
                {"SP24", "Băng keo", 17, 12000},
                {"SP25", "Bấm kim", 6, 45000},
                {"SP26", "Kim bấm", 26, 8000},
                {"SP27", "Máy tính bỏ túi", 4, 120000},
                {"SP28", "Sổ lò xo", 13, 30000},
                {"SP29", "File nhựa", 55, 4000},
                {"SP30", "Túi đựng hồ sơ", 32, 6000}
        };

        DefaultTableModel model = new  DefaultTableModel(data,cols);
        this.table = new JTable(model) {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true; // set width theo kich thuoc cua ScrollPane
            }
        };
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
        this.btnAdd = UIStyle.setBtnActive(this.btnAdd,"Update");
        this.btnAdd.setBorder(BorderFactory.createEmptyBorder(4,12,4,12));
        this.pnBottom.add(this.btnAdd);
        this.pnBottom.add(Box.createHorizontalStrut(20));
        this.btnAdd = UIStyle.setBtnActive(this.btnAdd,"Thêm");
        this.btnAdd.setBorder(BorderFactory.createEmptyBorder(4,12,4,12));
        this.pnBottom.add(this.btnAdd);

        this.pnBottom.add(Box.createHorizontalStrut(40));
        this.add(this.pnBottom, BorderLayout.SOUTH);
    }
}
