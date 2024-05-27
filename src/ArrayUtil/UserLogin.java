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
public class UserLogin {
    
    private String admin_id;
    private String username;
    private String password;
    private String admin_type;
    private String name;
    private String old_username;
    
    public UserLogin(String admin_id,String username,String password,String admin_type,String name){
        this.admin_id = admin_id;
        this.username =  username;
        this.password = password;
        this.admin_type = admin_type;
        this.name = name;
    }
    
    public UserLogin(){
        
    }
    
    public void setUser(String name,String username,String password,String type, String old_username){
        this.name = name;
        this.username =  username;
        this.password = password;
        this.admin_type = type;
        this.old_username = old_username;
    }
    
    public String getId(){
        return this.admin_id;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getType(){
        return this.admin_type;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getOldUsername(){
        return this.old_username;
    }
    
}
