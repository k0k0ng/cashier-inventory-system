/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayUtil;

import dbutil.DBconnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ArrayUtil.Supplier;
import javax.swing.JOptionPane;

/**
 *
 * @author Claude
 */
public class SupplierList {

    private Connection con;
    private Statement state;
    private ResultSet result;
    private Supplier suppliers[];
    private int counter;
    private int size;

    public SupplierList() throws SQLException {
        this.size = DBconnection.getTableCount("supplier");
        this.suppliers = new Supplier[this.size];
        this.con = DBconnection.con;
        this.state = null;
        this.result = null;
        this.counter = 0;
        this.initialize();
    }

    public void initialize() throws SQLException {
        this.state = con.createStatement();
        this.result = state.executeQuery("SELECT * FROM supplier");

        try {
            while(this.result.next()){
                this.addObject(new Supplier(this.result.getString("supplier_id"),this.result.getString("supplier_name"),this.result.getString("product_id"),this.result.getDouble("price")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addObject(Supplier supplier) throws SQLException {
        this.suppliers[this.counter] = supplier;
        this.counter++;
    }
    
    public Boolean addSupplierToDb(Supplier supplier) throws SQLException {
        String query = "INSERT INTO supplier"
                + "(supplier_name,product_id,price)"
                + "VALUES('" + supplier.getSupplierName() + "','" + supplier.getProductId() + "','" + supplier.getPrice()
                + "')";

        try {
            this.state = this.con.createStatement();
            this.state.executeUpdate(query);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid format");
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
    
    public Boolean deleteSupplierByProduct(String product_id) throws SQLException{
        String query = "DELETE FROM supplier WHERE product_id = '"+product_id+"'";
        
        try{
            this.state = this.con.createStatement();
            this.state.executeUpdate(query);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }
    
    public Boolean deleteSupplierToDb(String supplier_name, String product_id) throws SQLException{
        String query = "DELETE FROM supplier WHERE supplier_name = '"+ supplier_name +"' AND product_id = '"+product_id+"'";
        
        try{
            this.state = this.con.createStatement();
            this.state.executeUpdate(query);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }
    
    public Supplier[] getSupplierById(String product_id){
        Supplier tmp[] = null;
        int tmpCounter = 0;

        for (int i = 0; i < this.size; i++) {
            if(this.suppliers[i].getProductId().equals(product_id)){
                tmpCounter++;
            }
        }
        
        tmp = new Supplier[tmpCounter];
        tmpCounter = 0;
        
        for (int i = 0; i < this.size; i++) {
            if(this.suppliers[i].getProductId().equals(product_id)){
                tmp[tmpCounter] = suppliers[i];
                tmpCounter++;
            }
        }
        return tmp;
    }
    
    public Supplier[] getSupplierList(){
        return this.suppliers;
    }
    
    public int getTableSize(){
        return this.size;
    }
}
