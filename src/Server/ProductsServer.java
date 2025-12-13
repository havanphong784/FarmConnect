package Server;

import Model.Products;

import java.util.ArrayList;

public class ProductsServer {

    public static Object[][] toTableData(ArrayList<Products> list) {
        Object[][] data = new Object[list.size()][5];

        for (int r = 0; r < list.size(); r++) {
            Products p = list.get(r);

            data[r] = new Object[] {
                    p.getProName(),
                    p.getPrice(),
                    p.getUnit(),
                    p.getQuantity(),
                    p.getDes()
            };
        }
        return data;
    }
}
