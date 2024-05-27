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
public class Node {

    public Product data;
    public Node next;

    public Node(Product data, Node next) {
        this.data = data;
        this.next = next;
    }
}
