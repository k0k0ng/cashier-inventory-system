/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArrayUtil;

import dbutil.DBconnection;
import ArrayUtil.UserLogin;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class AdminList {

    private UserLogin admin;
    private UserLogin login[];
    private Connection con;
    private ResultSet result;
    private Statement state;
    private int size;
    private int counterX;

    public AdminList() throws SQLException {
        this.size = DBconnection.getTableCount("login");
        this.login = new UserLogin[this.size];
        this.counterX = 0;
        this.result = null;
        this.state = null;
        this.con = DBconnection.con;
        this.initialize();
    }

    private void initialize() throws SQLException {

        this.state = con.createStatement();
        this.result = state.executeQuery("SELECT * FROM login");

        try {
            while (this.result.next()) {
                this.admin = new UserLogin(result.getString("admin_id"), result.getString("username"), result.getString("password"), result.getString("admin_type"), result.getString("name"));
                this.addObject(admin);
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

    public void addObject(UserLogin obj) throws SQLException {
        this.login[this.counterX] = obj;
        this.counterX++;
    }
    
    public Boolean editAccountToDb(UserLogin user) throws SQLException {
        String query = "UPDATE login "
                + "SET name ='" + user.getName() + "',"
                + "username ='" + user.getUsername() + "',"
                + "password ='" + user.getPassword() + "',"
                + "admin_type ='" + user.getType() + "'"
                + "WHERE username = '" + user.getOldUsername() + "'";
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

    public UserLogin getAdminByUsername(String username) {
        for (int i = 0; i < this.size; i++) {
            if (this.login[i].getUsername().equals(username)) {

                return this.login[i];
            }
        }
        return null;
    }

    public UserLogin getAdminById(String id) {
        for (int i = 0; i < this.size; i++) {
            if (this.login[i].getId().equals(id)) {
                return this.login[i];
            }
        }
        return null;
    }

    public UserLogin[] getAdmins() {
        return this.login;
    }

    public int getTableSize() {
        return this.size;
    }

}
