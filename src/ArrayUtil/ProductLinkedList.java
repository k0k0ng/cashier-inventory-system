/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temp_stringlate file, choose Tools | Temp_stringlates
 * and open the temp_stringlate in the editor.
 */
package ArrayUtil;

import javax.swing.JOptionPane;


/**
 *
 * @author Claude
 */
public class ProductLinkedList {

    private Node head;
    private Node tail;
    private int size;

    public ProductLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public Boolean addProduct(Product product, double quantity) {
        if (head == null) {
            product.setQuantity(quantity);
            this.head = new Node(product, null);
            this.tail = head;
            this.size++;

            return true;
        } else {
           
            product.setQuantity(quantity);
            if (!this.productExist(product.getId(), quantity)) {
                this.tail.next = new Node(product, null);
                this.tail = this.tail.next;
                this.size++;
            } else {
                return tail.data.getStock() >= tail.data.getQuantity();
            }
            return true;
        }
    }

    public Boolean productExist(String product_id, double quantity) {
        Node tmp = head;
        double new_quantity = 0;

        while (tmp != null) {
            if (tmp.data.getId().equals(product_id)) {
                double tmp_stock = tmp.data.getStock() - (quantity + tmp.data.getQuantity());
                if (tmp_stock >=0) {
                    new_quantity = tmp.data.getQuantity() + quantity;
                    tmp.data.setQuantity(new_quantity);
                }else{
                    JOptionPane.showMessageDialog(null, "Stock is not enough!");
                } 
                return true;
            }
            tmp = tmp.next;
        }

        return false;
    }

    public void remove(String product_name) {
        Node cur = this.head;
        Node prev = null;

        if (this.head.data.getName().equals(product_name)) {
            this.head = this.head.next;
            cur.next = null;
            this.size--;

        } else {
            while (cur != null) {
                if (cur.data.getName().equals(product_name)) {
                    prev.next = cur.next;
                    cur.next = null;
                    this.size--;
                    break;
                }
                prev = cur;
                cur = cur.next;
            }

            if (cur == tail) {
                this.tail = prev;
                
                prev.next = null;
            }
        }
    }

    public String getStringDetails() {
        Node tmp = head;
        String stringDetails = "";
        String temp_string = "";
        String temp_string2 = "";
        
        while (tmp != null) {
            
            if (String.valueOf(tmp.data.getSellPrice()).length()==3) {
                temp_string = " " + tmp.data.getQuantity() + "\t \t  Php " + tmp.data.getSellPrice() + "\t \t \t \t " + tmp.data.getName() + "\n \t";
            }else if (String.valueOf(tmp.data.getSellPrice()).length()==4) {
                temp_string = " " + tmp.data.getQuantity() + "\t \t  Php " + tmp.data.getSellPrice() + "\t \t \t " + tmp.data.getName() + "\n \t";
            }else if(String.valueOf(tmp.data.getSellPrice()).length()==5){
                temp_string = " " + tmp.data.getQuantity() + "\t \t  Php " + tmp.data.getSellPrice() + "\t \t \t " +tmp.data.getName() + "\n \t";
            }else{
                temp_string = " " + tmp.data.getQuantity() + "\t \t  Php " + tmp.data.getSellPrice() + "\t \t " + tmp.data.getName() + "\n \t";
            }
            
            
            
            if (temp_string.length() > 53) {
                for (int i = 0; i < temp_string.length(); i++) {
                    
                    if (i!=54 && i<96) {
                        temp_string2 += temp_string.charAt(i);
                    }else if(i==54){
                        temp_string2 += "- \n ---------- ";
                        temp_string2 += temp_string.charAt(i);
                    }else if(i==96){
                        temp_string2 += " ...";
                    }
                }
                stringDetails += temp_string2;
            }else{
                if (String.valueOf(tmp.data.getSellPrice()).length()==3) {
                    stringDetails += " " + tmp.data.getQuantity() + "\t \t  Php " + tmp.data.getSellPrice() + "\t \t \t \t " + tmp.data.getName() + "\n \t";
                }else if (String.valueOf(tmp.data.getSellPrice()).length()==4) {
                    stringDetails += " " + tmp.data.getQuantity() + "\t \t  Php " + tmp.data.getSellPrice() + "\t \t \t " + tmp.data.getName() + "\n \t";
                }else if(String.valueOf(tmp.data.getSellPrice()).length()==5){
                    stringDetails += " " + tmp.data.getQuantity() + "\t \t  Php " + tmp.data.getSellPrice() + "\t \t \t " +tmp.data.getName() + "\n \t";
                }else{
                    stringDetails += " " + tmp.data.getQuantity() + "\t \t  Php " + tmp.data.getSellPrice() + "\t \t " + tmp.data.getName() + "\n \t";
                }
                
            }
            
            tmp = tmp.next;
        }
        return stringDetails;
    }

    public Float getGrandTotal() {
        Node tmp = head;
        float grand_total = 0;

        while (tmp != null) {
            grand_total += Float.parseFloat(String.valueOf(tmp.data.getSellPrice())) * tmp.data.getQuantity();
            tmp = tmp.next;
        }
        return grand_total;
    }

    public Node getList() {
        return this.head;
    }

    public int getSize() {
        return this.size;
    }

}
