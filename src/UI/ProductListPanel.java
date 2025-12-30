package UI;

import Server.ProductsServer;
import DBConnect.ProductsDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Product List Panel - Displays all products with search, filter, and sorting
 */
public class ProductListPanel extends JPanel {
    protected JTable table;
    protected JTextField txtSearch;
    protected JComboBox<String> cmbArrangement, cmbType;
    protected JButton btnSearch, btnAdd, btnUpdate, btnSell;
    protected JPanel pnHeader, pnToolbar, pnCenter, pnBottom;
    protected JScrollPane scrollPane;
    protected DefaultTableModel model;
    public Object[][] data;
    public String[] cols;
    
    public ProductListPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(UIStyle.colorBg);
        this.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        initHeader();
        initToolbar();
        initTable();
        initBottomPanel();
        setupActions();
    }
    
    /**
     * Header with page title
     */
    private void initHeader() {
        pnHeader = new JPanel(new BorderLayout());
        pnHeader.setBackground(UIStyle.colorBg);
        pnHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel lblTitle = new JLabel("Quản Lý Sản Phẩm");
        lblTitle.setFont(UIStyle.font24Bold);
        lblTitle.setForeground(UIStyle.colorText);
        pnHeader.add(lblTitle, BorderLayout.WEST);
        
        // Product count
        int count = ProductsDAO.getAll().size();
        JLabel lblCount = new JLabel("Tổng: " + count + " sản phẩm");
        lblCount.setFont(UIStyle.font14);
        lblCount.setForeground(UIStyle.colorTextSecondary);
        pnHeader.add(lblCount, BorderLayout.EAST);
        
        this.add(pnHeader, BorderLayout.NORTH);
    }
    
    /**
     * Toolbar with search, filter, and sort
     */
    private void initToolbar() {
        pnToolbar = new JPanel();
        pnToolbar.setLayout(new BoxLayout(pnToolbar, BoxLayout.X_AXIS));
        pnToolbar.setBackground(UIStyle.colorBgCard);
        pnToolbar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyle.colorBorder, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        // Search field
        JLabel lblSearch = new JLabel("Tim: ");
        lblSearch.setFont(UIStyle.font16);
        pnToolbar.add(lblSearch);
        
        txtSearch = UIStyle.setTextField(txtSearch, "");
        txtSearch.setMaximumSize(new Dimension(250, 40));
        txtSearch.setPreferredSize(new Dimension(250, 40));
        pnToolbar.add(txtSearch);
        pnToolbar.add(Box.createHorizontalStrut(10));
        
        // Type filter
        cmbType = new JComboBox<>();
        cmbType.addItem("Tất cả loại");
        for (String type : ProductsDAO.getTypes()) {
            cmbType.addItem(type);
        }
        styleComboBox(cmbType);
        pnToolbar.add(cmbType);
        pnToolbar.add(Box.createHorizontalStrut(10));
        
        // Search button
        btnSearch = new JButton("Tìm kiếm");
        styleToolbarButton(btnSearch, UIStyle.colorPrimary);
        pnToolbar.add(btnSearch);
        
        pnToolbar.add(Box.createHorizontalGlue());
        
        // Sort dropdown
        JLabel lblSort = new JLabel("Sắp xếp: ");
        lblSort.setFont(UIStyle.font14);
        lblSort.setForeground(UIStyle.colorTextSecondary);
        pnToolbar.add(lblSort);
        
        String[] sortOptions = {
            "Mặc định",
            "Tên A-Z",
            "Tên Z-A",
            "Số lượng tăng dần",
            "Số lượng giảm dần",
            "Giá tăng dần",
            "Giá giảm dần"
        };
        cmbArrangement = new JComboBox<>(sortOptions);
        styleComboBox(cmbArrangement);
        pnToolbar.add(cmbArrangement);
        
        // Wrapper to include toolbar
        JPanel toolbarWrapper = new JPanel(new BorderLayout());
        toolbarWrapper.setBackground(UIStyle.colorBg);
        toolbarWrapper.add(pnToolbar, BorderLayout.CENTER);
        toolbarWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Add to a container with header
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(UIStyle.colorBg);
        
        // Move header here
        this.remove(pnHeader);
        topContainer.add(pnHeader);
        topContainer.add(toolbarWrapper);
        
        this.add(topContainer, BorderLayout.NORTH);
    }
    
    /**
     * Style combobox
     */
    private void styleComboBox(JComboBox<String> cmb) {
        cmb.setPreferredSize(new Dimension(180, 40));
        cmb.setMaximumSize(new Dimension(180, 40));
        cmb.setFont(UIStyle.font14);
        cmb.setBackground(UIStyle.colorBgCard);
        cmb.setBorder(BorderFactory.createLineBorder(UIStyle.colorBorder, 1));
    }
    
    /**
     * Style toolbar button
     */
    private void styleToolbarButton(JButton btn, Color bgColor) {
        btn.setPreferredSize(new Dimension(100, 38));
        btn.setMaximumSize(new Dimension(100, 38));
        btn.setFont(UIStyle.font14);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * Product table
     */
    private void initTable() {
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setBackground(UIStyle.colorBg);
        
        cols = new String[]{"Tên Sản Phẩm", "Giá", "Đơn Vị", "Tồn Kho", "Mô Tả"};
        data = ProductsServer.toTableData(ProductsDAO.getAll());
        model = new DefaultTableModel(data, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        UIStyle.styleTable(table);
        
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIStyle.colorBorder, 1));
        pnCenter.add(scrollPane, BorderLayout.CENTER);
        
        this.add(pnCenter, BorderLayout.CENTER);
    }
    
    /**
     * Bottom panel with action buttons
     */
    private void initBottomPanel() {
        pnBottom = new JPanel();
        pnBottom.setBackground(UIStyle.colorBgCard);
        pnBottom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyle.colorBorder, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        pnBottom.setLayout(new BoxLayout(pnBottom, BoxLayout.X_AXIS));
        
        // Info text
        JLabel lblInfo = new JLabel("Chọn sản phẩm để thao tác");
        lblInfo.setFont(UIStyle.font14);
        lblInfo.setForeground(UIStyle.colorTextSecondary);
        pnBottom.add(lblInfo);
        
        pnBottom.add(Box.createHorizontalGlue());
        
        // Action buttons with consistent styling
        btnAdd = createActionButton("Thêm Mới", UIStyle.colorPrimary);
        pnBottom.add(btnAdd);
        pnBottom.add(Box.createHorizontalStrut(15));
        
        btnUpdate = createActionButton("Cập Nhật", UIStyle.colorInfo);
        pnBottom.add(btnUpdate);
        pnBottom.add(Box.createHorizontalStrut(15));
        
        btnSell = createActionButton("Bán Hàng", UIStyle.colorSuccess);
        pnBottom.add(btnSell);
        
        this.add(pnBottom, BorderLayout.SOUTH);
    }
    
    /**
     * Create consistent action button
     */
    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(140, 42));
        btn.setFont(UIStyle.font14);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        final Color hoverColor = bgColor.darker();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }
    
    /**
     * Setup action listeners
     */
    private void setupActions() {
        // Search/filter actions
        cmbArrangement.addActionListener(e -> refreshTable());
        cmbType.addActionListener(e -> refreshTable());
        btnSearch.addActionListener(e -> refreshTable());
        
        // Enter key in search field
        txtSearch.addActionListener(e -> refreshTable());
        
        // Add product
        btnAdd.addActionListener(e -> {
            ProductsFromInsert form = new ProductsFromInsert();
            JDialog dialog = new JDialog();
            dialog.setTitle("Thêm Sản Phẩm Mới");
            dialog.setModal(true);
            dialog.setContentPane(form);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            refreshTable();
            DashboardPanel.refreshDashboard();
        });
        
        // Update product
        btnUpdate.addActionListener(e -> {
            int indexRow = table.getSelectedRow();
            if (indexRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần cập nhật!");
                return;
            }
            int row = table.convertRowIndexToModel(indexRow);
            TableModel tableModel = table.getModel();
            
            ProductsFormUpdate form = new ProductsFormUpdate(tableModel, row);
            JDialog dialog = new JDialog();
            dialog.setTitle("Cập Nhật Sản Phẩm");
            dialog.setModal(true);
            dialog.setContentPane(form);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            refreshTable();
            DashboardPanel.refreshDashboard();
        });
        
        // Sell product
        btnSell.addActionListener(e -> {
            int indexRow = table.getSelectedRow();
            if (indexRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần bán!");
                return;
            }
            int row = table.convertRowIndexToModel(indexRow);
            TableModel tableModel = table.getModel();
            
            ProductsFormSell form = new ProductsFormSell(row, tableModel);
            JDialog dialog = new JDialog();
            dialog.setTitle("Thêm Vào Giỏ Hàng");
            dialog.setModal(true);
            dialog.setContentPane(form);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            refreshTable();
            HistoryPanel.refreshOrdersTable();
            DashboardPanel.refreshDashboard();
        });
    }
    
    /**
     * Refresh table with current filters
     */
    protected void refreshTable() {
        String nameSearch = txtSearch.getText().trim();
        String selected = String.valueOf(cmbArrangement.getSelectedItem());
        String selectedType = String.valueOf(cmbType.getSelectedItem());
        
        // Handle "Tất cả loại" option
        if ("Tất cả loại".equals(selectedType)) {
            selectedType = "Tất cả";
        }
        
        Object[][] newData;
        
        switch (selected) {
            case "Tên A-Z":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderNameASC(nameSearch, selectedType));
                break;
            case "Tên Z-A":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderNameDESC(nameSearch, selectedType));
                break;
            case "Số lượng tăng dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderQuantityASC(nameSearch, selectedType));
                break;
            case "Số lượng giảm dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderQuantityDESC(nameSearch, selectedType));
                break;
            case "Giá tăng dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderPriceASC(nameSearch, selectedType));
                break;
            case "Giá giảm dần":
                newData = ProductsServer.toTableData(ProductsDAO.searchOderPriceDESC(nameSearch, selectedType));
                break;
            default:
                newData = ProductsServer.toTableData(ProductsDAO.searchOderNameASC(nameSearch, selectedType));
                break;
        }
        
        model.setDataVector(newData, cols);
    }
}
