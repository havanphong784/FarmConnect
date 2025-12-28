package UI;

import DBConnect.ProductsDAO;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Form to sell product - Add product to cart
 * Extends ProductsFromInsert and customizes for selling operation
 */
public class ProductsFormSell extends ProductsFromInsert {
    
    // Additional fields for sell form
    private JLabel lblStock;
    private JTextField txtStock;
    private final int stockQuantity;
    private final String productName;
    private final String productPrice;
    
    /**
     * Constructor - Initialize sell form with product data
     * @param row Selected row index
     * @param model Table model containing product data
     */
    public ProductsFormSell(int row, TableModel model) {
        super();
        
        // Get product info from table
        this.productName = model.getValueAt(row, 0).toString();
        this.productPrice = model.getValueAt(row, 1).toString();
        String unit = model.getValueAt(row, 2).toString();
        this.stockQuantity = Integer.parseInt(model.getValueAt(row, 3).toString());
        
        // Configure form
        setupFormLayout();
        setupProductInfo(unit);
        setupQuantityFields();
        setupSellAction();
    }
    
    /**
     * Configure form layout and size
     */
    private void setupFormLayout() {
        this.lblTitle.setText("Them san pham vao gio hang");
        this.setPreferredSize(new Dimension(400, 450));
        
        // Remove unused fields
        this.remove(lblDes);
        this.remove(txtDes);
        this.remove(lblExpiry);
        this.remove(txtExpiry);
    }
    
    /**
     * Setup product info fields (read-only)
     */
    private void setupProductInfo(String unit) {
        // Product name
        this.txtName.setText(productName);
        this.txtName.setEditable(false);
        this.txtName.setBackground(new Color(240, 240, 240));

        // Price
        this.txtPrice.setText(productPrice);
        this.txtPrice.setEditable(false);
        this.txtPrice.setBackground(new Color(240, 240, 240));

        // Unit
        this.txtUnit.setText(unit);
        this.txtUnit.setEditable(false);
        this.txtUnit.setBackground(new Color(240, 240, 240));
    }
    
    /**
     * Setup stock and quantity fields
     */
    private void setupQuantityFields() {
        // Stock quantity label (read-only)
        this.lblStock = UIStyle.setLabel(this.lblStock, "Ton kho:");
        this.lblStock.setBounds(20, 290, 170, 22);
        this.add(this.lblStock);

        this.txtStock = UIStyle.setTextField(this.txtStock, String.valueOf(stockQuantity));
        this.txtStock.setBounds(20, 315, 170, 40);
        this.txtStock.setFont(UIStyle.font14);
        this.txtStock.setEditable(false);
        this.txtStock.setBackground(new Color(240, 240, 240));
        this.add(this.txtStock);

        // Quantity to buy
        this.lblQuantiy.setText("So luong mua:");
        this.lblQuantiy.setBounds(210, 290, 170, 22);
        this.txtQuantity.setText("1");
        this.txtQuantity.setBounds(210, 315, 170, 40);
        
        // Submit button
        this.btnSubmit.setText("Them vao gio");
        this.btnSubmit.setBounds(20, 380, 360, 45);
    }
    
    /**
     * Setup action listener for sell button
     */
    private void setupSellAction() {
        // Remove inherited action listener
        if (this.btnSubmit.getActionListeners().length > 0) {
            this.btnSubmit.removeActionListener(this.btnSubmit.getActionListeners()[0]);
        }
        
        // Add sell action
        this.btnSubmit.addActionListener(e -> {
            try {
                int orderQuantity = Integer.parseInt(txtQuantity.getText().trim());
                
                // Validate quantity
                if (orderQuantity <= 0 || orderQuantity > stockQuantity) {
                    JOptionPane.showMessageDialog(null, 
                        "So luong phai > 0 va <= ton kho (" + stockQuantity + ")!");
                    return;
                }

                // Get product ID and add to cart
                int productId = ProductsDAO.getProductIdByName(productName);
                BigDecimal unitPrice = new BigDecimal(productPrice);
                
                CartPanel.getInstance().addToCart(productId, productName, unitPrice, orderQuantity);
                
                JOptionPane.showMessageDialog(null, 
                    "Da them " + orderQuantity + " x " + productName + " vao gio hang!");
                
                // Close the dialog
                SwingUtilities.getWindowAncestor(this).dispose();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Vui long nhap dung dinh dang so!");
            }
        });
    }
}
