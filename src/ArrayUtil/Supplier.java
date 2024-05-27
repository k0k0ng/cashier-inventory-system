/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayUtil;

/**
 *
 * @author Claude
 */
public class Supplier {
    private String id;
    private String supplier_name;
    private String product_id;
    private double price;
    
    public Supplier(String id, String supplier_name, String product_id, double price){
        this.id = id;
        this.supplier_name = supplier_name;
        this.product_id = product_id;
        this.price = price;
    }
    
    public Supplier(String supplier_name, String product_id, double price){
        this.supplier_name = supplier_name;
        this.product_id = product_id;
        this.price = price;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getSupplierName(){
        return this.supplier_name;
    }
    
    public String getProductId(){
        return this.product_id;
    }
    
    public double getPrice(){
        return this.price;
    }
}
