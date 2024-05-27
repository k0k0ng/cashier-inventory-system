/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayUtil;

import dbutil.DBconnection;
import ArrayUtil.Product;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Claude
 */
public class ProductList {

    Connection con;
    ResultSet result;
    PreparedStatement prepare;
    Statement state;
    Product products[];
    Product product;
    int size;
    int counterX;

    public ProductList() throws SQLException {
        this.size = DBconnection.getTableCount("product");
        this.products = new Product[this.size];
        this.result = null;
        this.state = null;
        this.prepare = null;
        this.con = DBconnection.con;
        this.counterX = 0;

        this.initialize();
    }

    public void initialize() throws SQLException {
        String query = "SELECT * FROM product";
        this.state = con.createStatement();
        this.result = state.executeQuery(query);

        try {
            while (this.result.next()) {
                String query_temp = "SELECT * FROM transaction_detail WHERE product_id = '" + this.result.getString("product_id") + "'";
                this.product = new Product(this.result.getString("product_id"), this.result.getString("name"), Double.parseDouble(this.result.getString("capital_price")), Double.parseDouble(this.result.getString("sell_price")), Double.parseDouble(this.result.getString("stock")), this.result.getString("unit"), this.result.getString("category"));
                this.product.setRanking(DBconnection.getResultCount(query_temp));
                this.addProduct(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }

    private void addProduct(Product product) {
        this.products[this.counterX] = product;
        this.counterX++;
    }

    public Boolean addProductToDb(Product product) throws SQLException {
        String query = "INSERT INTO product"
                + "(name,capital_price,sell_price,stock,unit,category)"
                + "VALUES('" + product.getName() + "','" + product.getCapitalPrice() + "','" + product.getSellPrice()
                + "','" + product.getStock() + "','" + product.getUnit() + "','" + product.getCategory() + "')";

        try {
            this.state = this.con.createStatement();
            this.state.executeUpdate(query);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid format");
            return false;
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }

    public Boolean addSupplierToDb(Product product) throws SQLException {
        String query = "INSERT INTO supplier"
                + "(supplier_name,product_name,price)"
                + "VALUES('" + product.getSupplierName() + "','" + product.getName() + "','" + product.getCapitalPrice()
                + "')";

        try {
            this.state = this.con.createStatement();
            this.state.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }

    public Boolean editProductToDb(Product product) throws SQLException {
        String query = "UPDATE product "
                + "SET name ='" + product.getName() + "',"
                + "capital_price ='" + product.getCapitalPrice() + "',"
                + "sell_price ='" + product.getSellPrice() + "',"
                + "stock ='" + product.getStock() + "',"
                + "unit ='" + product.getUnit() + "'"
                + "WHERE name = '" + product.getOldName() + "'";
        try {
            this.state = this.con.createStatement();
            this.state.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }

    public Boolean deleteProductToDb(String product_id) {
        String query = "DELETE FROM product WHERE name = '" + product_id + "'";

        try {
            this.state = this.con.createStatement();
            this.state.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }

    public Boolean changeProductStock(String product_id, String quantity) throws SQLException {
        double total_stock = this.getCurrentStock(product_id) - Float.parseFloat(quantity);

        String query = "UPDATE product "
                + "SET stock = '" + String.valueOf(total_stock) + "'"
                + "WHERE product_id = '" + product_id + "'";
        try {
            this.state = this.con.createStatement();
            this.state.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }

    public Product[] getLeastStock() {
        Product tmp[] = null;
        int tmp_counter = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.products[i].getStock() <= 2) {
                tmp_counter++;
            }
        }

        tmp = new Product[tmp_counter];
        tmp_counter = 0;

        for (int i = 0; i < this.size; i++) {
            if (this.products[i].getStock() <= 2) {
                tmp[tmp_counter] = this.products[i];
                tmp_counter++;
            }

        }
        return tmp;
    }

    public Product[] searchByNameCategory(String search, String category) throws SQLException {
        Product searchedProduct[] = null;
        String breakStr = "";
        String tmp[] = search.split("");

        if (search.length() > 2) {
            for (int i = 0; i < 3; i++) {
                breakStr += tmp[i];
            }
        } else if (search.length() < 3) {
            breakStr = search;
        }

        String query = "SELECT * FROM product WHERE (name LIKE '%" + search + "%' OR '%" + breakStr + "%') AND category = '" + category + "'";
//        AND category = '"+category+"'
        this.state = con.createStatement();
        this.result = state.executeQuery(query);
        searchedProduct = new Product[DBconnection.getResultCount(query)];

        try {
            for (int i = 0; this.result.next(); i++) {
                this.product = new Product(this.result.getString("product_id"), this.result.getString("name"), Double.parseDouble(this.result.getString("capital_price")), Double.parseDouble(this.result.getString("sell_price")), Double.parseDouble(this.result.getString("stock")), this.result.getString("unit"), this.result.getString("category"));
                searchedProduct[i] = this.product;
            }

            return searchedProduct;
        } catch (Exception e) {

            return null;
        } finally {
            try {
                this.state.close();
                this.result.close();
            } catch (Exception e) {
            }
        }
    }

    public Product[] searchByName(String search) throws SQLException {
        Product searchedProduct[] = null;
        String breakStr = "";
        String tmp[] = search.split("");

        if (search.length() > 2) {
            for (int i = 0; i < 3; i++) {
                breakStr += tmp[i];
            }
        } else if (search.length() < 3) {
            breakStr = search;
        }

        String query = "SELECT * FROM product WHERE (name LIKE '%" + search + "%' OR '%" + breakStr + "%')";
//        AND category = '"+category+"'
        this.state = con.createStatement();
        this.result = state.executeQuery(query);
        searchedProduct = new Product[DBconnection.getResultCount(query)];

        try {
            for (int i = 0; this.result.next(); i++) {
                this.product = new Product(this.result.getString("product_id"), this.result.getString("name"), Double.parseDouble(this.result.getString("capital_price")), Double.parseDouble(this.result.getString("sell_price")), Double.parseDouble(this.result.getString("stock")), this.result.getString("unit"), this.result.getString("category"));
                searchedProduct[i] = this.product;
            }

            return searchedProduct;
        } catch (Exception e) {

            return null;
        } finally {
            try {
                this.state.close();
                this.result.close();
            } catch (Exception e) {
            }
        }

    }

    public double getCurrentStock(String product_id) {
        double stock = 0;
        for (int i = 0; i < this.size; i++) {
            if (products[i].getId().equals(product_id)) {
                stock = this.products[i].getStock();
                return stock;
            }
        }
        return 0;
    }

    public double getCurrentStockByName(String product_name) {
        double stock = 0;
        for (int i = 0; i < this.size; i++) {
            if (products[i].getName().equals(product_name)) {
                stock = this.products[i].getStock();
                return stock;
            }
        }
        return 0;
    }

    public String getCurrentUnitByName(String product_name) {
        String unit = "";
        for (int i = 0; i < this.size; i++) {
            if (products[i].getName().equals(product_name)) {
                unit = this.products[i].getUnit();
                return unit;
            }
        }
        return " ";
    }

    public Product getProductById(String product_id) {
        Product tmp;
        for (int i = 0; i < this.products.length; i++) {
            if (this.products[i].getId().equals(product_id)) {
                tmp = new Product(products[i].getId(), products[i].getName(), products[i].getCapitalPrice(), products[i].getSellPrice(), products[i].getStock(), products[i].getUnit(), products[i].getCategory());
                return tmp;
            }
        }
        tmp = new Product(product_id, "DELETED PRODUCT", 0, 0, 0, "", "");
        return tmp;
    }

    public Product getProductByName(String product_name) {
        Product tmp;
        for (int i = 0; i < this.products.length; i++) {
            if (this.products[i].getName().equals(product_name)) {
                tmp = new Product(products[i].getId(), products[i].getName(), products[i].getCapitalPrice(), products[i].getSellPrice(), products[i].getStock(), products[i].getUnit(), products[i].getCategory());
                return tmp;
            }
        }
        return null;
    }

    public Product[] getProductByCategory(String category) throws SQLException {
        Product products_by_category[] = null;
        int sizeCounter = 0;
        int counter1 = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.products[i].getCategory().equals(category)) {
                sizeCounter++;//4
            }
        }

        products_by_category = new Product[sizeCounter];
        for (int i = 0; i < this.size; i++) {
            if (this.products[i].getCategory().equals(category)) {
                products_by_category[counter1] = products[i];
                counter1++;
            }
        }
        return products_by_category;
    }

    public Product[] getProducts() {
        return this.products;
    }

    public int getTableSize() {
        return this.size;
    }

}
