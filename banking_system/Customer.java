/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banking_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import DataStructure.Stack;
import static banking_system.Admin.databaseTransactions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

/**
 *
 * @author Asus
 */
public class Customer {

    static String databaseCustomer = "C:\\Users\\Asus\\Documents\\NetBeansProjects\\Banking_System\\src\\banking_system\\databaseCustomer.txt";
    static Scanner sc = new Scanner(System.in);

    public static void CustomerMenu() throws IOException {
        System.out.println("Hello! Welcome to our Banking System");
        System.out.println("Enter Account Number");
        String user = sc.nextLine();
        String[] loginDetails = login(user);
        if (loginDetails != null) {
            while (true) {
                System.out.println("[1] See Account Details/ Check Balance");
                System.out.println("[2] Withdraw Money");
                System.out.println("[3] See Account Transactions");
                System.out.println("[4] Change Password");
                System.out.println("[5] Exit");
                String input = sc.nextLine();
                if (input.equals("1")) {
                    DisplayAccount(user);
                } else if (input.equals("2")) {
                    Withdraw(user);
                } else if (input.equals("3")) {
                    CheckTransactions(user);
                } else if (input.equals("4")) {
                    ChangePassword(user);
                } else if (input.equals("5")) {
                    System.out.println("Closing Program");
                } else {
                    System.out.println("Invalid Input");
                    CustomerMenu();
                }
            }
        } else {
            CustomerMenu();
        }
    }

    public static void Withdraw(String user) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        BufferedWriter write = new BufferedWriter(new FileWriter(databaseTransactions, true));
        ArrayList<String[]> database = new ArrayList<>();
        String line;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            if (arr[0].equals(user)) {
                System.out.println("Account Balance: " + arr[arr.length - 1]);
                System.out.println("Enter Amount to Withdraw");
                try {
                    double input = sc.nextDouble();
                    if (input <= Double.parseDouble(arr[arr.length - 1])) {
                        arr[arr.length - 1] = Double.parseDouble(arr[arr.length - 1]) - input + "";
                        write.append(user + "/" + "Withdraw" + "/" + input);
                        write.newLine();
                        write.close();
                    } else {
                        System.out.println("Insufficient Account Balance, Cannot Withdraw.");
                    }
                } catch (Exception e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
            }
            database.add(arr);
        }
        update(database);

    }

    public static void DisplayAccount(String user) throws FileNotFoundException, IOException {
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        String line;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            if (arr[0].equals(user)) {
                System.out.println("Account Number: " + arr[0]);
                System.out.println("Name: " + arr[2]);
                System.out.println("Age: " + arr[3]);
                System.out.println("Birth Date: " + arr[4]);
                System.out.println("Address: " + arr[5]);
                System.out.println("Contact Number: " + arr[6]);
                System.out.println("Balance: " + arr[7]);
            }
        }
    }

    public static void ChangePassword(String user) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        BufferedWriter write = new BufferedWriter(new FileWriter(databaseTransactions, true));
        ArrayList<String[]> database = new ArrayList<>();
        String line;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            if (arr[0].equals(user)) {
                System.out.println("Current Password: " + arr[1]);
                System.out.println("Enter New Password");
                String input = sc.nextLine();
                if (Pattern.matches("([0-9]{6})", input)) {
                    arr[1] = input;
                    write.append(user + "/" + "Change Password" + "/" + input);
                }else{
                    System.out.println("Password must be 6 digits with no characters/special characters");
                }
            }
            database.add(arr);
        }
        update(database);
        write.newLine();
        write.close();
    }

    public static void update(ArrayList<String[]> var) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(databaseCustomer))) {
            for (int i = 0; i < var.size(); i++) {
                for (int j = 0; j < var.get(i).length; j++) {
                    if (j + 1 == var.get(i).length) {
                        writer.append(var.get(i)[j]);
                    } else {
                        writer.append(var.get(i)[j] + "/");
                    }
                }
                writer.append("\n");
            }
        }
    }

    public static void CheckTransactions(String accNum) throws IOException {
        BufferedReader transactions = new BufferedReader(new FileReader(databaseTransactions));
        Stack print = new Stack();
        String line;
        while ((line = transactions.readLine()) != null) {
            String arr[] = line.split("/");
            print.push(arr);
        }

        while (!print.isEmpty()) {
            String arr[] = print.top.getData();
            if (accNum.equals(arr[0])) {
                for (String string : arr) {
                    System.out.println(string);
                }
                print.pop();
            } else {
                print.pop();
            }
        }

    }

    public static Stack readCustomerDB() throws IOException {
        Stack database = new Stack();
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        String line;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            database.push(arr);
        }
        return database;
    }

    public static String[] login(String user) throws IOException {
        System.out.println("Enter Pin Code");
        String password = sc.nextLine();
        Stack database = readCustomerDB();
        while (!database.isEmpty()) {
            String arr[] = database.top.getData();
            if (user.equals(arr[0]) && password.equals(arr[1])) {
                System.out.println("Hello: " + arr[2]);
                database.pop();
                return arr;
            } else if (!database.isEmpty()) {
                database.pop();
            } else {
                return null;
            }
        }
        System.out.println("No Accounts found, try again.");
        return null;
    }

    public static void main(String[] args) throws IOException {
        CustomerMenu();
    }
}
