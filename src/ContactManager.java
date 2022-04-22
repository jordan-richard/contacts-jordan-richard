import java.util.Scanner;

public class ContactManager {

    public static void main(String[] args) {

        Input input = new Input();
        mainMenu();

    }

    private static void mainMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1. Show All Contacts");
        System.out.println("2. Add New Contact");
        Scanner s = new Scanner(System.in);
        String answer = s.nextLine();

        if (answer.equals("2")) {
            Input.addContact(Contact.name, Contact.phoneNumber);
            mainMenu();
        }
    }
}
