package ArrayUtil;

import dbutil.DBconnection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import ArrayUtil.TransactionDetail;
import ArrayUtil.Product;

public class TransactionDetailList {

    private Connection con;
    private Statement state;
    private ResultSet result;
    private TransactionDetail details[];
    private TransactionDetail detail;
    private ProductList prod;
    private Product prod_tmp[];
    private int counter;
    private int size;

    public TransactionDetailList() throws SQLException {
        this.size = DBconnection.getTableCount("transaction_detail");
        this.details = new TransactionDetail[this.size];
        this.con = DBconnection.con;
        this.prod_tmp = null;
        this.state = null;
        this.result = null;
        this.counter = 0;
        this.prod = new ProductList();
        this.initialize();
        this.initMostPurchased();
    }

    public void initialize() throws SQLException {
        this.state = con.createStatement();
        this.result = state.executeQuery("SELECT * FROM transaction_detail");

        try {
            while (this.result.next()) {
                this.detail = new TransactionDetail(result.getString("detail_id"), result.getString("transaction_id"), result.getString("quantity"), result.getString("product_id"), result.getDouble("product_price"));
                this.addObject(this.detail);
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

    public Boolean addDetailToDb(TransactionDetail detail) throws SQLException {
        String query = "INSERT INTO transaction_detail"
                + "(transaction_id,quantity,product_id,product_price)"
                + "VALUES('" + String.valueOf(detail.getTransactionId()) + "','" + detail.getQuantity()
                + "','" + detail.getProductId() + "','"+detail.getProductPrice()+"')";

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
    
    public boolean deleteDetailToDb(String id) throws SQLException{
        String query = "DELETE FROM transaction_detail WHERE transaction_id = '"+id+"'";
    
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

    public void initMostPurchased() { //default most purchased
        prod_tmp = this.sortDescending(this.prod.getProducts());

        for (int i = 0; i < prod_tmp.length; i++) {
            if (prod_tmp[i].getRankCounter() != 0) {
                prod_tmp[i].setPercentage(this.calculatePercentage(prod_tmp[i], this.size));
            }
        }
    }
    
    public float calculatePercentage(Product obj,int num_of_result){
        return (obj.getRankCounter() / num_of_result) * 100;
    }

    public Product[] sortDescending(Product arr[]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].getRankCounter() <= arr[j].getRankCounter()) {
                    Product tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return arr;
    }

    public Product[] sortAscending(Product arr[]) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].getRankCounter() >= arr[j].getRankCounter()) {
                    Product tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return arr;
    }

    public Product[] getMostPurchasedByDate(String start_date, String end_date) {
        String query = "SELECT * FROM transactions WHERE date BETWEEN '" + start_date + "' AND '" + end_date + "'";
        TransactionDetail tmp_details[] = null;
        Transaction tmp2[] = null;
        Product tmp_product[] = null;

        int tmp_counter = 0;

        try {
            tmp2 = new Transaction[DBconnection.getResultCount(query)];

            this.state = this.con.createStatement();
            this.result = state.executeQuery(query);

            for (int i = 0; result.next(); i++) {
                tmp2[i] = new Transaction(result.getString("transaction_id"), result.getString("date"), result.getString("time"), result.getString("total_bill"), result.getString("admin_id"));
            }

            for (int i = 0; i < tmp2.length; i++) {
                for (int j = 0; j < this.size; j++) {
                    if (tmp2[i].getId().equals(details[j].getTransactionId())) {
                        tmp_counter++;
                    }
                }
            }

            tmp_details = new TransactionDetail[tmp_counter];
            int x = 0;
            int duplicates = 0;

            for (int i = 0; i < tmp2.length; i++) {
                for (int j = 0; j < this.size; j++) {
                    if (tmp2[i].getId().equals(details[j].getTransactionId())) {
                        tmp_details[x] = details[j];                        
                        x++;
                    }
                }
            }

            for (int i = 0; i < tmp_details.length; i++) {
                for (int j = i + 1; j < tmp_details.length; j++) {
                    if (tmp_details[i].getProductId().equals(tmp_details[j].getProductId())) {
                        duplicates++;
                        break;
                    }
                }
            }

            tmp_product = new Product[tmp_details.length - duplicates];
            x = 0;

            for (int i = 0; i < tmp_details.length; i++) {
                Boolean flag = true;
                for (int j = i + 1; j < tmp_details.length; j++) {
                    if (tmp_details[i].getProductId().equals(tmp_details[j].getProductId())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    tmp_product[x] = prod.getProductById(tmp_details[i].getProductId());
                    tmp_product[x].setRanking(0);
                    
                    for (int j = 0; j < tmp_details.length; j++) {
                        if(tmp_product[x].getId().equals(tmp_details[j].getProductId())){
                            tmp_product[x].rankCounter();
                        }
                    }
                    x++;
                }
            }
            return this.sortDescending(tmp_product);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public Product[] getMostPurchased() {
        return this.prod_tmp;
    }

    public void addObject(TransactionDetail detail) throws SQLException {
        this.details[this.counter] = detail;
        this.counter++;
    }

    public TransactionDetail[] getDetails() {
        return this.details;
    }

    public TransactionDetail[] getDetailById(String transaction_id) throws SQLException {
        TransactionDetail details_by_id[] = null;
        int sizeCounter = 0;
        int counterx = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.details[i].getTransactionId().equals(transaction_id)) {
                sizeCounter++;
            }
        }

        details_by_id = new TransactionDetail[sizeCounter];
        for (int i = 0; i < this.size; i++) {
            if (this.details[i].getTransactionId().equals(transaction_id)) {
                details_by_id[counterx] = details[i];
                counterx++;
            }
        }
        return details_by_id;
    }
}