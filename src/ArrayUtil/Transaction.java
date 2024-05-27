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
public class Transaction {
    private String id;
    private String date;
    private String time;
    private String total_bill;
    private String admin_id;
    
    public Transaction(String id,String date,String time,String total_bill,String admin_id){
        this.id = id;
        this.date = date;
        this.time = time;
        this.total_bill = total_bill;
        this.admin_id = admin_id;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public Float getTotalBIll(){
        return Float.parseFloat(this.total_bill);
    }
    
    public String getAdminId(){
        return this.admin_id;
    }
    
    public String getTime(){
        return this.time;
    }
    
}
