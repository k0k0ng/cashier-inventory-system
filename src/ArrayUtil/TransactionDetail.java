package ArrayUtil;

public class TransactionDetail {
    
    private String id;
    private String transaction_id;
    private String quantity;
    private String product_id;
    private float number_of_purchase;
    private double product_price;
    
    public TransactionDetail(String id,String transaction_id,String quantity,String product_id,double product_price){
        this.id = id;
        this.transaction_id = transaction_id;
        this.quantity = quantity;
        this.product_id = product_id;
        this.product_price  = product_price;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getTransactionId(){
        return this.transaction_id;
    }
    
    public float getQuantity(){
        return Float.parseFloat(this.quantity);
    }
    
    public String getProductId(){
        return this.product_id;
    }
    
    public double getProductPrice(){
        return this.product_price;
    }
    //
    public void setNumberOfPurchase(float x){
        this.number_of_purchase = x;
    }
    
    public float getNumberOfPurchase(){
        return this.number_of_purchase;
    } 
    
}
