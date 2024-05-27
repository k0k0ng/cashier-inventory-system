/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Claude
 */
public class DBconnection {
    
    String sessionId = "";
    private static ResultSet result = null;
    private static Statement state = null;
    public static Connection con = null;
    
    public static Connection ConnectDb() throws SQLException{
        try{
           Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:hardwareinventory.db");
            return con;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }finally{
            try {
                if(con!=null){
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        
    }
    
    public static ResultSet getResult(String query) throws SQLException{
        try{
            state = con.createStatement();
            result = state.executeQuery(query);
            
            return result;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return null;
        }finally{
            try {
               state.close();
               result.close(); 
            } catch (Exception e) {
            }
        }
    }
    
    
    public static int getTableCount(String table_name) throws SQLException{
        int rowCount = 0;
        String query = "SELECT COUNT(*) as count FROM "+table_name;
        try{
            con = DriverManager.getConnection("jdbc:sqlite:hardwareinventory.db");
            state = con.createStatement();
            result = state.executeQuery(query);
            rowCount = Integer.parseInt(result.getString("count"));
            return rowCount;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e+" debug1");
            return 0;
        }
        finally{
            try {
               state.close();
               result.close(); 
            } catch (Exception e) {
            }
        }
    }
    
    public static int getResultCount(String query) throws SQLException{
        int rowCount = 0;

        try{
            con = DriverManager.getConnection("jdbc:sqlite:hardwareinventory.db");
            state = con.createStatement();
            result = state.executeQuery(query);
            while(result.next()){
                rowCount++;
            }
            return rowCount;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return 0;
        }
        finally{
            try {
               state.close();
               result.close(); 
            } catch (Exception e) {
            }
        }
    }
    
   
}
