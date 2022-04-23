import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class Contact {
    String name;
    String phoneNumber;
    static HashMap<String, String> newPerson = new HashMap<>();
//    static ArrayList<HashMap> newPersonList = new ArrayList<>();
    public final Scanner scanner = new Scanner(System.in);

    //   TODO: Constructor function
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    //    TODO: Getters and Setters
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    static ArrayList<Contact> contactList = new ArrayList<Contact>();
    static Scanner scan = new Scanner(System.in);
    static boolean isRunning = true;

    public static void runApp(){
        while(isRunning){
            System.out.println("Please enter contact name or (quit to exit program): ");
            String name = scan.next();
            if(name.equalsIgnoreCase("quit")) {
                System.out.println("Thank you. GoodBye!");
                break;
            }
            System.out.println("Please enter phone number: ");
            String phoneNumber = scan.next();
            Contact newContact = new Contact(name, phoneNumber);
            contactList.add(newContact);
    }
        for(Contact t: contactList) {
            System.out.println("Name: " + t.getName() + "\nAmount: " + t.getPhoneNumber());
            System.out.println();
        }
}


//    public static void showContacts() {
//
//    }

    //    TODO: Add number to memory
//    public static void addContact(String newName,String phoneNumber){
////        HashMap<String,String> newPerson = new HashMap<>();
//        newPerson.put(newName,phoneNumber);
////        newPerson.put("fff","phoneNumber");
////        newPersonList.add(newPerson);
//        System.out.println(newPerson);
////        System.out.println(newPerson.get("richard jackson"));
////        for(int i=0;i < newPerson.size();i++){
////            System.out.println(newPerson.get(i));
////        }
//    }
//    public static void getContact(){
//        System.out.println("enter new contact...");
//        System.out.println("Enter first and last name.");
//        String nameInput = scanner.nextLine();
//        System.out.println("Enter phone number.");
//        String phNumberInput = scanner.nextLine();
//        addContact(nameInput,phNumberInput);
//    }

//public static void writeFile(String fileName, HashMap<String,String> personData ){
//try{
//    FileWriter fw = new FileWriter();
//}
//}
//catch( IOException ex){

}




