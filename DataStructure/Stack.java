/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure;

import java.util.Arrays;

/**
 *
 * @author Asus
 */
public class Stack {
    public Node top;
    public int size;
    public Stack(){
        this.top = null;
        this.size = 0;
    }
    
    public boolean isEmpty(){
        return (top == null);
    }
    
    public void push(String [] data){
        Node n = new Node(data);
        if(top == null){
            top = n;
            return;
        }
        n.next = top;
        top = n;
        size++;
    }
    
    public void pop(){
        if(isEmpty()){
            System.out.println("Stack is empty.");
            return;
        }
        top = top.next;
        size--;
    }
    
    public void peek(){
        System.out.println("Top: "+Arrays.toString(top.data));
    }
    
    public void show(){
        Node temp = top;
        while(temp!=null){
            System.out.println(Arrays.toString(temp.data));
            temp = temp.next;
        }
    }
    
    public int getSize(){
        return size;
    }
}
