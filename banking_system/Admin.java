/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banking_system;

import DataStructure.LinkedList;
import DataStructure.Node;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class Admin {

    static String databaseAdmin = "C:\\Users\\Asus\\Documents\\NetBeansProjects\\Banking_System\\src\\banking_system\\databaseAdmin.txt";
    static String databaseCustomer = "C:\\Users\\Asus\\Documents\\NetBeansProjects\\Banking_System\\src\\banking_system\\databaseCustomer.txt";
    static String databaseTransactions = "C:\\Users\\Asus\\Documents\\NetBeansProjects\\Banking_System\\src\\banking_system\\TotalTransactions.txt";
    static Random rand = new Random();
    static LinkedList admin = new LinkedList();

    public static void AdminMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        admin = readAdminDatabase(admin);
        if (admin.getSize() == 0) {
            System.out.println("Enroll Master Account");
            EnrollMaster();
            AdminMenu();
        } else {
            while (true) {
                System.out.println("EMPLOYEE TAB");
                System.out.println("[1] Log In");
                System.out.println("[2] Enroll Admin");
                System.out.println("[3] Exit");
                String input = sc.nextLine();
                if (input.equals("1")) {
                    StaffMenu(admin);
                } else if (input.equals("2")) {
                    String adminData[] = admin.head.getData();
                    System.out.println(adminData[0] + "user");
                    System.out.println(adminData[1] + "pass");
                    System.out.println("Enter Master Username");
                    String user = sc.nextLine();
                    if (user.equals(adminData[0])) {
                        System.out.println("Enter Master Password");
                        String pass = sc.nextLine();
                        if (pass.equals(adminData[1])) {
                            register(admin);
                        } else {
                            System.out.println("Wrong Password, Try Again");
                        }
                    } else {
                        System.out.println("Wrong Username, Try Again");
                    }
                } else if (input.equals("3")) {
                    break;
                } else {
                    System.out.println("Wrong Input, Try Again");
                }
            }
        }
    }

    public static void EnrollMaster() throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedWriter var = new BufferedWriter(new FileWriter(databaseAdmin));
        System.out.println("Enter Full Name");
        String name = sc.nextLine();
        System.out.println("Enter Master Username");
        String user = sc.nextLine();
        System.out.println("Enter Master Password");
        String pass = sc.nextLine();
        var.append(user + "/" + pass + "/" + name + "/" + randomAdminID() + "/" + LocalDate.now() + "/" + LocalTime.now());
        var.newLine();
        var.close();
    }

    public static void StaffMenu(LinkedList admin) throws IOException {
        boolean LogInCondition = login(admin);
        if (LogInCondition) {
            Scanner sc = new Scanner(System.in);
            System.out.println("[1] Enroll Customer Account");
            System.out.println("[2] Edit Customer Account");
            System.out.println("[3] Check Customer Transactions");
            System.out.println("[4] Exit");
            String input = sc.nextLine();
            if (input.equals("1")) {
                EnrollCustomerAccount();
            } else if (input.equals("2")) {
                EditCustomerAccount();
            } else if (input.equals("3")) {
                CheckTransactions();
            } else if (input.equals("4")) {
                System.out.println("Exiting Menu....");
            } else {
                System.out.println("Invalid Input, Try Again");
                StaffMenu(admin);
            }
        } else {
            System.out.println("Returning Main Menu");
        }
    }

    public static boolean login(LinkedList database) {
        if (database.getSize() != 0) {
            System.out.println("Typing 'Exit' will return you to the Main Menu");
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Username");
            String user = sc.nextLine();
            if (user.equalsIgnoreCase("Exit")) {
                return false;
            }
            System.out.println("Enter Password");
            String pass = sc.nextLine();
            if (pass.equalsIgnoreCase("Exit")) {
                return false;
            }
            Node temp = database.head;
            while (temp != null) {
                String arr[] = temp.getData();
                System.out.println(Arrays.toString(arr));
                if (arr[0].equals(user) && arr[1].equals(pass)) {
                    System.out.println("Logged in!");
                    System.out.println("Hello " + arr[2]);
                    System.out.println("ID Number: " + arr[3]);
                    return true;
                }
                temp = temp.getNext();
            }
            System.out.println("Log in Error, Try Again");
        } else {
            System.out.println("No Admins Enrolled");
            return false;
        }
        return false;
    }

    public static LinkedList readAdminDatabase(LinkedList database) throws FileNotFoundException, IOException {
        try (BufferedReader var = new BufferedReader(new FileReader(databaseAdmin))) {
            String line;
            Node data;
            while ((line = var.readLine()) != null) {
                String arr[] = line.split("/");
                data = new Node(arr);
                database.insertAtEnd(data);
            }
        }
        return database;
    }

    public static LinkedList register(LinkedList database) throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'Exit' to end process");
        System.out.println("Enter Staff Name");
        String StaffName = sc.nextLine();
        if (StaffName.equalsIgnoreCase("Exit")) {
            return database;
        }
        System.out.println("Enter Username");
        String user = sc.nextLine();
        if (user.equalsIgnoreCase("Exit")) {
            return database;
        }
        System.out.println("Password should be 7 characters");
        String pass = sc.nextLine();
        if (pass.equalsIgnoreCase("Exit")) {
            return database;
        }

        if (verify(database, user, pass) == true) {
            String arr[] = {user, pass, StaffName, randomAdminID(), LocalDate.now() + "", LocalTime.now() + ""};
            Node data = new Node(arr);
            database.insertAtEnd(data);
            try (BufferedWriter write = new BufferedWriter(new FileWriter(databaseAdmin, true))) {
                write.append(user + "/" + pass + "/" + StaffName + "/" + randomAdminID() + "/" + LocalDate.now() + "/" + LocalTime.now());
                write.newLine();
                write.close();
            }
            System.out.println("Registered!");
        } else {
            System.out.println("Registratoin Failed!");
            register(database);
        }

        return database;
    }

    public static boolean verify(LinkedList database, String user, String pass) {
        boolean condition = true;
        if (passwordChecker(pass) == false) {
            System.out.println("Password is does not contain 7 characters");
            condition = false;
            return condition;
        }
        Node temp = database.head;
        String arr[] = temp.getData();
        while (temp != null) {
            if (arr[0].equals(user) && arr[1].equals(pass)) {
                System.out.println("Account Already Exists");
                condition = false;
                break;
            }
            temp = temp.getNext();
        }
        return condition;
    }

    public static boolean passwordChecker(String password) {
        boolean condition = true;
        if (password.length() < 7) {
            condition = false;
            return condition;
        } else {
            return condition;
        }
    }

    public static void EnrollCustomerAccount() throws IOException {
        BufferedWriter var = new BufferedWriter(new FileWriter(databaseCustomer, true));
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter Customer Full Name");
            String name = sc.nextLine();
            System.out.println("Enter Customer Birth Date MM-DD-YYYY");
            String birthday = sc.nextLine();
            System.out.println("Enter Customer Age");
            String age = sc.nextLine();
            System.out.println("Enter Customer Address");
            String address = sc.nextLine();
            System.out.println("Enter Customer Contact Number");
            String contactNumber = sc.nextLine();
            System.out.println("Enter Initial Deposit");
            String initialDeposit = sc.nextLine();
            //1234567 is the initial pin
            var.write(randomCustomerID() + "/" + "123456" + "/" + name + "/" + age + "/" + birthday + "/" + address + "/" + contactNumber + "/" + initialDeposit);
            var.newLine();
            var.close();
        } catch (Exception e) {
            System.out.println("Wrong input, Try Again");
            EnrollCustomerAccount();
        }

    }

    public static String randomCustomerID() throws IOException {
        ArrayList<String> array = new ArrayList<>();
        array = readCustomerDatabase(array);
        String line;
        int random = rand.nextInt(9999);
        String accNum = LocalDate.now() + "" + random;
        if(random<1000){
            random+=1000;
        }
        for (int i = 1; i < array.size(); i += 7) {
            if (array.get(i).equals(accNum)) {
                random = rand.nextInt(9999);
                accNum = LocalDate.now() + "" + random;
            }
        }

        return accNum.replaceAll("[-]","");
    }

    public static String randomAdminID() throws IOException {
        LinkedList array = new LinkedList();
        array = readAdminDatabase(array);
        int random = rand.nextInt(9999);
        Node temp = array.head;
        while (temp != null) {
            String data[] = temp.getData();
            if (data[3].equals(random + "")) {
                random = rand.nextInt(9999);
            }
            temp = temp.getNext();
        }

        return LocalDate.now() + "" + random + "";
    }

    public static void EditCustomerAccount() throws FileNotFoundException, IOException {
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Account Number");
        String AccNumber = in.nextLine();
        ArrayList<String[]> updateDB = new ArrayList<>();
        String line;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            if (arr[0].equals(AccNumber)) {
                for (int i = 0; i < arr.length; i++) {
                    System.out.println(arr[i]);
                }
                System.out.println("What to edit?");
                System.out.println("[1] Password");
                System.out.println("[2] Name");
                System.out.println("[3] Age");
                System.out.println("[4] Birth Date");
                System.out.println("[5] Address");
                System.out.println("[6] Contact Number");
                System.out.println("[7] Exit");
                String input = in.nextLine();
                if (input.equals("1")) {
                    String password = in.nextLine();
                    arr[1] = password;
                } else if (input.equals("2")) {
                    String name = in.nextLine();
                    arr[2] = name;
                } else if (input.equals("3")) {
                    String age = in.nextLine();
                    arr[3] = age;
                } else if (input.equals("4")) {
                    String BirthDate = in.nextLine();
                    arr[4] = BirthDate;
                } else if (input.equals("5")) {
                    String Address = in.nextLine();
                    arr[5] = Address;
                } else if (input.equals("6")) {
                    String ContactNumber = in.nextLine();
                    arr[6] = ContactNumber;
                } else if (input.equals("7")) {
                    break;
                } else {
                    System.out.println("Invalid Input Try Again");
                    EditCustomerAccount();
                }
            }
            updateDB.add(arr);
            update(updateDB);
        }

    }

    public static void update(ArrayList<String[]> var) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Asus\\Documents\\NetBeansProjects\\FirstYr\\src\\firstyr\\inventory.txt"))) {
            for (int i = 0; i < var.size(); i++) {
                for (int j = 0; j < var.get(i).length; j++) {
                    if (i == 0) {
                        writer.write(var.get(i)[j]);
                    } else {
                        if (j + 1 == var.get(i).length) {
                            writer.append(var.get(i)[j]);
                        } else {
                            writer.append(var.get(i)[j] + ",");
                        }
                    }
                }
                writer.append("\n");
            }
        }
    }

    public static void CheckTransactions() throws IOException {
        Scanner sc = new Scanner(System.in);
        LinkedList admin = new LinkedList();
        admin = readAdminDatabase(admin);
        System.out.println("Enter Master Username");
        String user = sc.nextLine();
        String adminData[] = admin.head.getData();
        if (user.equals(adminData[0])) {
            System.out.println("Enter Master Password");
            String pass = sc.nextLine();
            if (pass.equals(adminData[1])) {
                System.out.println("Enter Account Number");
                String input = sc.nextLine();
                BufferedReader transactions = new BufferedReader(new FileReader(databaseTransactions));
                String line;
                while ((line = transactions.readLine()) != null) {
                    String arr[] = line.split("/");
                    if (arr[0].equals(input)) {
                        System.out.println(Arrays.toString(arr));
                    }
                }
            } else {
                System.out.println("Wrong Password, Try Again");
                CheckTransactions();
            }
        } else {
            System.out.println("Wrong Username, Try Again");
            CheckTransactions();
        }

    }

    public static ArrayList readCustomerDatabase(ArrayList database) throws FileNotFoundException, IOException {
        BufferedReader var = new BufferedReader(new FileReader(databaseAdmin));
        String line = null;
        while ((line = var.readLine()) != null) {
            String arr[] = line.split("/");
            for (String string : arr) {
                database.add(string);
            }
        }
        var.close();
        return database;
    }

    public static void main(String[] args) throws IOException {
        AdminMenu();
    }

}
