package UI;

import Server.ProductsServer;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Form to update existing product information
 * Extends ProductsFromInsert and customizes for update operation
 */
public class ProductsFormUpdate extends ProductsFromInsert {
    
    // Additional fields for update form
    private JLabel lblSale;
    private JTextField txtPricePercent;
    
    /**
     * Constructor - Initialize update form with product data
     * @param model Table model containing product data
     * @param row Selected row index
     */
    public ProductsFormUpdate(TableModel model, int row) {
        super();
        
        // Set form title and button text
        this.lblTitle.setText("Cap nhat thong tin san pham");
        this.btnSubmit.setText("Cap nhat");
        
        // Fill form with existing product data
        this.txtName.setText(String.valueOf(model.getValueAt(row, 0)));
        this.txtPrice.setText(String.valueOf(model.getValueAt(row, 1)));
        this.txtUnit.setText(String.valueOf(model.getValueAt(row, 2)));
        this.txtQuantity.setText(String.valueOf(model.getValueAt(row, 3)));
        this.txtDes.setText(String.valueOf(model.getValueAt(row, 4)));
        
        // Remove expiry fields (not needed for update)
        remove(lblExpiry);
        remove(txtExpiry);

        // Resize price field to make room for sale field
        lblPrice.setBounds(20, 140, 170, 22);
        txtPrice.setBounds(20, 165, 170, 40);

        // Add sale percentage field
        this.lblSale = UIStyle.setLabel(this.lblSale, "Giam gia (%):");
        this.lblSale.setBounds(210, 140, 170, 22);
        this.add(this.lblSale);

        this.txtPricePercent = UIStyle.setTextField(this.txtPricePercent, "0");
        this.txtPricePercent.setBounds(210, 165, 170, 40);
        this.add(this.txtPricePercent);

        // Make product name read-only
        this.txtName.setEditable(false);
        this.txtName.setBackground(new Color(240, 240, 240));

        // Setup update action
        setupUpdateAction();
    }
    
    /**
     * Setup action listener for update button
     */
    private void setupUpdateAction() {
        // Remove inherited action listener
        if (this.btnSubmit.getActionListeners().length > 0) {
            this.btnSubmit.removeActionListener(this.btnSubmit.getActionListeners()[0]);
        }
        
        // Add update action
        this.btnSubmit.addActionListener(e -> {
            try {
                // Get form values
                String name = txtName.getText().trim();
                BigDecimal price = new BigDecimal(txtPrice.getText().trim());
                String unit = txtUnit.getText().trim();
                int quantity = Integer.parseInt(txtQuantity.getText().trim());
                float pricePercent = Float.parseFloat(txtPricePercent.getText().trim());
                String des = txtDes.getText().trim();

                // Call server to update product
                boolean isUpdated = ProductsServer.updateProduct(name, des, quantity, price, pricePercent, unit);
                
                if (isUpdated) {
                    JOptionPane.showMessageDialog(null, "Cap nhat san pham thanh cong!");
                    // Close the dialog
                    SwingUtilities.getWindowAncestor(this).dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Cap nhat san pham that bai!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Vui long nhap dung dinh dang so!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Da xay ra loi: " + ex.getMessage());
            }
        });
    }
}
