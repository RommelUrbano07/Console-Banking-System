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
public class LinkedList {
    public Node head;
    public Node next;
    int size;
    
    public LinkedList(){
        this.head = null;
        this.next = null;
        this.size = 0;
    }
    
    public int getSize(){
        return this.size;
    }
    
    public void insertAtBeginning(Node n){
        if(this.head == null){
            this.head = n;
            this.next = n.next;
        }
        else{
            n.next = head;
            head = n;
        }
        size++;
    }
    
    public void insertAtIndex(Node n, int index){
        
        if(index == 0){
            insertAtBeginning(n);
            return;
        }
        
        Node tmp = head;
        int ctr = 1;
        while(tmp.getNext() != null){
            if(ctr == index){
                Node node = tmp.getNext();
                tmp.setAsNext(n);
                n.setAsNext(node);
                size++;
                return;
            }
            tmp = tmp.getNext();
            ctr++;
        }
        tmp.setAsNext(n);
        size++;
        
        if(index > size-1 || index < 0){
            System.out.println("Error: Index not found.");
        }
    }
    
    public void insertAtEnd(Node n){
        Node temp = head;
        if(temp==null) insertAtBeginning(n);
        while(temp!= null){
            if(temp.getNext()==null){
                temp.setAsNext(n);
                break;
            }
            temp = temp.getNext();
        }
        
    }
    
    public void deleteKey(String [] data){
        Node temp = head;
        Node prev = null;
        int ctr = 0;
        if (temp != null && temp.getData() == data) {
            if (ctr == 0) {
                if (size == 1) {
                    head = null;
                    size--;
                    return;
                }
                head = temp.getNext();
                size--;
                return;
            }
        }
            
        while (temp != null && temp.getData() != data) {
            prev = temp;
            temp = temp.getNext();
        }
        
        if(temp == null){
            System.out.println("Key not in the list.");
        }
        prev.setAsNext(temp.next);
        size--;
    }
    
    public void deleteAtIndex(int index){
        Node temp = head;
        Node prev = null;
        int ind = 0;
        if(index > size -1){
            System.out.println("Invalid index.");
            return;
        }
        
        if (temp != null && index == 0) {
                if (size == 1) {
                    head = null;
                    size--;
                    return;
                }
                head = temp.getNext();
                size--;
                return;
        }
        
        while (temp != null && ind != index) {
            prev = temp;
            temp = temp.getNext();
            ind++;
        }
        
        prev.setAsNext(temp.next);
        size--;
        
    }
    
    public void display(){
        Node tmp = head;
        while(tmp.next != null){
            System.out.print(tmp.getData()+" <-> ");
            tmp = tmp.getNext();
        }
        System.out.println(tmp.getData());
    }
    
    public int search(String [] data){
        int index = 0;
        Node temp = head;
        while(temp.getNext() != null){
            if(temp.getData() == data){
                return index;
            }
            index++;
            temp = temp.getNext();
        }
        if(temp.getData() == data) return index;
        return -1;
    }
}
