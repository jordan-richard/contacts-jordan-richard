import java.util.HashMap;
import java.util.Scanner;

public class Contact {
    static String name;
    static String phoneNumber;
    private static final Scanner scanner = new Scanner(System.in);
    public Contact(String name, String phoneNumber ){
        Contact.name = name;
        Contact.phoneNumber = phoneNumber;
    }

    public static void showContacts() {
        
    }

    //    TODO: Add number to memory
    public static void addContact(String newName,String phoneNumber){
        HashMap<String,String> newPerson = new HashMap<>();
        newPerson.put(newName,phoneNumber);
        System.out.println(newPerson);
    }
    public static void getContact(){
        System.out.println("enter new contact...");
        System.out.println("Enter first and last name.");
        String nameInput = scanner.nextLine();
        System.out.println("Enter phone number.");
        String phNumberInput = scanner.nextLine();
        addContact(nameInput,phNumberInput);
    }

}
