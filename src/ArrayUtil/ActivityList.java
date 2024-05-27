/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayUtil;

import dbutil.DBconnection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class ActivityList {

    private Connection con;
    private Statement state;
    private ResultSet result;
    private Activity activities[];
    private Activity activity;
    private int counter;
    private int size;

    public ActivityList() throws SQLException {
        this.size = DBconnection.getTableCount("activity_log");
        this.activities = new Activity[this.size];
        this.con = DBconnection.con;
        this.state = null;
        this.result = null;
        this.activity = null;
        this.counter = 0;
        this.initialize();
    }

    public void initialize() throws SQLException {
        this.state = con.createStatement();
        this.result = state.executeQuery("SELECT * FROM activity_log");

        try {
            while (this.result.next()) {
                this.activity = new Activity(result.getString("activity_id"), result.getString("activity_type"), result.getString("date_time"), result.getString("activity_location"), result.getString("admin_id"));
                this.addObject(this.activity);
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

    public void addObject(Activity obj) {
        this.activities[this.counter] = obj;
        this.counter++;
    }

    public Boolean addActivityToDb(Activity obj) throws SQLException {
        String query = "INSERT INTO activity_log "
                     + "(activity_type,activity_location,admin_id) "
                     + "VALUES('"+obj.getType()+"','"+obj.getLocation()+"','"+obj.getAdminId()+"')";
        try {
            this.state = con.createStatement();
            this.state.executeUpdate(query);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }
    
    public Activity[] getActivities(){
        return this.activities;
    }

}
