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
public class Product {
    private String id;
    private String name;
    private double capital_price;
    private double sell_price;
    private double stock;
    private String unit;
    private String category;
    private String supplier_name;
    private String old_name;
    private double quantity; //For cashier
    private float number;
    private float percentage=0;
    private String trans_id = "";
    Product next;
    
    
    public Product(){
        
    }
    
    
    public Product(String id, String name, double capital_price, double sell_price, double stock, String unit, String category){
        this.id = id;
        this.name = name;
        this.capital_price = capital_price;
        this.sell_price = sell_price;
        this.stock = stock;
        this.unit = unit;
        this.category = category;
        this.number = 0;
        this.percentage = 0;
    }
    
    public Product(String name, double capital_price, double sell_price, double stock, String unit, String category){
        this.name = name;
        this.capital_price = capital_price;
        this.sell_price = sell_price;
        this.stock = stock;
        this.unit = unit;
        this.category = category;
        this.next = null;
        this.number = 0;
    }
    
    public void setProduct(String name, double capital_price, double sell_price, double stock, String unit, String category, String old_name){
        this.name = name;
        this.capital_price = capital_price;
        this.sell_price = sell_price;
        this.stock = stock;
        this.unit = unit;
        this.category = category;
        this.old_name = old_name;
    }
    
    public Product(String name, double capital_price, double sell_price, double stock, String unit, String category, String supp_name){
        this.name = name;
        this.capital_price = capital_price;
        this.sell_price = sell_price;
        this.stock = stock;
        this.unit = unit;
        this.category = category;
        this.next = null;
        this.supplier_name = supp_name;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public double getCapitalPrice(){
        return this.capital_price;
    }
    
    public double getSellPrice(){
        return this.sell_price;
    }
    
    public double getStock(){
        return this.stock;
    }
    
    public String getUnit(){
        return this.unit;
    }
    
    public String getCategory(){
        return this.category;
    }
    
    public String getSupplierName(){
        return this.supplier_name;
    }
    
    public void setTransactionId(String trans_id){
        this.trans_id = trans_id;
    }
    
    public String getTransactionId(){
        return this.trans_id;
    }
    public void rankCounter(){
        this.number++;
    }
    public void setRanking(float x){
        this.number = x;
    }
    public float getRankCounter(){
        return this.number;
    }
    public String getOldName(){
        return this.old_name;
    }
    
    public void setPercentage(float x){
        this.percentage = x;
    }
    
    public float getPercentage(){
        return this.percentage;
    }
    
    
    
    //For cashier
    public void setQuantity(double q){
        this.quantity = q;
    }
    
    public void setTemporaryStock(double q){
        this.stock = q;
    }
    
    public double getQuantity(){
        return this.quantity;
    }
    
    public void setSupplier(String name, double price){
        this.supplier_name = name;
        this.capital_price = price;
    }
    
    
    
}
