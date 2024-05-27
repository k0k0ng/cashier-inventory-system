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
public class Activity {
    private String id;
    private String activity_type;
    private String date_time;
    private String location;
    private String admin_id;
    
    public Activity(){
        
    }
    
    public Activity(String id,String activity_type,String date_time,String location,String admin_id){
        this.id = id;
        this.activity_type = activity_type;
        this.date_time = date_time;
        this.location = location;
        this.admin_id = admin_id;
    }
    
    public Activity(String activity_type,String location,String admin_id){
        this.activity_type = activity_type;
        this.location = location;
        this.admin_id = admin_id;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getType(){
        return this.activity_type;
    }
    
    public String getDateTime(){
        return this.date_time;
    }
    
    public String getLocation(){
        return this.location;
    }
    
    public String getAdminId(){
        return this.admin_id;
    }
}
