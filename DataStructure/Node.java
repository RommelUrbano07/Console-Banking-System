/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure;

/**
 *
 * @author Asus
 */
public class Node {
    public String [] data;
    public Node next;
    
    public Node(){
        this.data = null;
        this.next = null;
    }
    
    public Node(String [] data){
        this.data = data;
        this.next = null;
    }
    
    public Node(String [] data, Node n){
        this.data = data;
        this.next = n;
    }
    
    public void setAsNext(Node n){
        this.next = n;
    }
    
    public void setData(String [] data){
        this.data = data;
    }
    
    public Node getNext(){
        return this.next;
    }
    
    public String[] getData(){
        return this.data;
    }
}

