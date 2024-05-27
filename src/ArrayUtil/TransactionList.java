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
import ArrayUtil.Transaction;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionList {

    Connection con;
    ResultSet result;
    Statement state;
    Transaction transactions[];
    Transaction transaction;
    int size;
    int counterX;

    public TransactionList() throws SQLException {
        this.size = DBconnection.getTableCount("transactions");
        this.result = null;
        this.state = null;
        this.con = DBconnection.con;
        this.transactions = new Transaction[this.size];
        this.counterX = 0;
        this.initialize();
    }

    public void initialize() throws SQLException {
        this.state = con.createStatement();
        this.result = state.executeQuery("SELECT * FROM transactions");

        try {
            while (this.result.next()) {
                this.transaction = new Transaction(result.getString("transaction_id"), result.getString("date"), result.getString("time"), result.getString("total_bill"), result.getString("admin_id"));
                this.addObject(this.transaction);
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

    public Boolean addTransactionToDb(Transaction trans) throws SQLException {
        String query = "INSERT INTO transactions"
                + "(transaction_id,date,total_bill,admin_id,time)"
                + "VALUES('" + trans.getId() + "','" + trans.getDate() + "','" + trans.getTotalBIll()
                + "','" + trans.getAdminId() + "','" + trans.getTime() + "')";

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

    public boolean deleteTransactionToDb(String id) throws SQLException {
        String query = "DELETE FROM transactions WHERE transaction_id = '" + id + "'";

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

    public void addObject(Transaction transaction) throws SQLException {
        this.transactions[this.counterX] = transaction;
        this.counterX++;
    }

    public Transaction[] getTransactions() {
        return this.transactions;
    }

    public int getTableSize() {
        return this.size;
    }

    public Transaction getTransactionById(String transaction_id) {
        for (int i = 0; i < this.size; i++) {
            if (transactions[i].getId().equals(transaction_id)) {
                return this.transactions[i];
            }
        }
        return null;
    }

    public Transaction[] getDateRange(String start_date, String end_date) throws SQLException {
        String query = "SELECT * FROM transactions WHERE date BETWEEN '" + start_date + "' AND '" + end_date + "'";
        this.state = this.con.createStatement();
        this.result = state.executeQuery(query);
        Transaction transactions_by_date[] = new Transaction[DBconnection.getResultCount(query)];

        try {
            for (int i = 0; this.result.next(); i++) {
                this.transaction = new Transaction(result.getString("transaction_id"), result.getString("date"), result.getString("time"), result.getString("total_bill"), result.getString("admin_id"));
                transactions_by_date[i] = this.transaction;
            }
            return transactions_by_date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                state.close();
                result.close();
            } catch (Exception e) {
            }
        }
    }

    public float getTotalGainToday(String date_today) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) formatter.parse(date_today);
        SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy");
        String finalString = newFormat.format(date);
        float gain = 0;

        for (int i = 0; i < this.size; i++) {
            if (transactions[i].getDate().equals(finalString) && transactions[i] != null) {
                gain += transactions[i].getTotalBIll();
            }
        }

        return gain;
    }

    public String generateReceiptId(String date_today) {
        //100220191
        String receipt_id = "";
        String tmp[] = date_today.split("-");
        int trans_number = 0;

        for (int i = 0; i < tmp.length; i++) {
            receipt_id += tmp[i];
        }

        if (this.isEmpty()) {
            receipt_id += "1";
            return receipt_id;
        }

        tmp = null;
        for (int i = 0; i < this.size; i++) {
            if (transactions[i].getDate().equals(date_today)) {
                String last_digit = "";
                tmp = transactions[i].getId().split("");

                for (int j = 0; j < tmp.length; j++) {
                    if(j>=8){
                        last_digit+=tmp[j];
                    }
                }
                trans_number = Integer.parseInt(last_digit) + 1;
            }
        }

        receipt_id += String.valueOf(trans_number);

        return receipt_id;
    }

    public Transaction[] getTransaction() {
        return this.transactions;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
}
