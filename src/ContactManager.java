//package warmups.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


public class ContactManager {

    public static Scanner scanner = new Scanner(System.in);

    final static String dir = "contacts";
    final static String fileName = "contacts.txt";
    private static boolean run = true;
    private static boolean contactExists = false;


    public static Path directory = Paths.get(dir);
    public static Path path = Paths.get(dir, fileName);

    public static List<Person> allContacts = new ArrayList<>(); // contact Person Objects in List
    public static List<String> pulledContacts = new ArrayList<>(); // pulled all contacts from contacts.txt

    public static void displayMenu() {
        System.out.printf("Options Menu [Enter value between 1-5]%n");
        System.out.println("1: View all contacts");
        System.out.println("2: Add new contact");
        System.out.println("3: Search contacts");
        System.out.println("4: Delete a contact");
        System.out.println("5: exit");
    }

    public static int isInteger(String aNumber) {
        try {
            return Integer.parseInt(aNumber);
        } catch (Exception e) {
            return -1;
        }
    }

    public static int optionChecker() {
        String number = scanner.nextLine();
        int option = isInteger(number);

        if (option >= 1) {
            if (option <= 5) {
                return option;
            } else {
                System.out.printf("That's not an option bro.%nPlease select an option between 1-5%n");
                scanner.nextLine();
                return optionChecker();
            }
        } else {
            System.out.printf("I do not like that response.%n");
            System.out.printf("Please select an option between 1-5.%n");
            return optionChecker();
        }
    }

    public static void optionSelected(int option) {
        switch (option) {
            case 1:
                displayAllContacts();
                confirm();
                break;
            case 2:
                addToContacts();
                confirm();
                break;
            case 3:
                searchContacts();
                confirm();
                break;
            case 4:
                deleteContact();
                confirm();
                break;
            case 5:
                System.out.println("Goodbye..... {In my Alexa voice}");
                run = false;
                break;
            default:
                System.out.println("I do not like that response.");
                runProgram();
                break;
        }
    }

    // ----- grab all existing contacts -----
    public static void grabContacts() {
        try {
            pulledContacts = Files.readAllLines(Paths.get(dir, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----- formats text from .txt into person objects -----
    public static void formatPulledContacts() {
        allContacts = new ArrayList<>();
        for (String contact : pulledContacts) {
            String name = contact.substring(contact.indexOf(" "), contact.indexOf("Phone")).trim();
            String number = contact.substring(contact.lastIndexOf(" ")).trim();
            allContacts.add(new Person(name, number));
        }
    }

    // ----- display all contacts -----
    public static void displayAllContacts() {
        grabContacts(); // grab the current contacts
        if (pulledContacts.isEmpty()) {
            System.out.println("There are no contacts.");
        } else {
            System.out.format("%1s%2s%8s%2s%8s\n", "|", "       Name", "|", "       Phone", "|");
            System.out.println("----------------------------------------");
            // formatPulledContacts(); // format those contacts into person objects stored into all contacts for manipulation
            for (Person contact : allContacts) { // print each contact formatted properly
                System.out.println(formatWriteContact(contact));
            }
        }
    }

    // ----- create contact -----
    public static Person createContact() {
        System.out.println("Enter a name");
//        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Enter a phone number");
        String phoneNumber = scanner.next();
        // return new Person(name, phoneNumber);
        while((phoneNumber.length() != 7) && (phoneNumber.length() != 10)){
            System.out.println("Not enough digits. Enter a valid phone number (7 or 10 digits)");
            phoneNumber = scanner.next();
        }
        return new Person(name, phoneNumber);
    }

    // ----- add to existing contact list -----
    public static void addToContacts() {
        Person person = createContact();
        String phoneFormat = person.getPhoneNumber();
        allContacts.add(person); // add to allContacts List
        try {
            Files.write(path, Collections.singletonList(formatWriteContact(person)), StandardOpenOption.APPEND); // writes to .txt file
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(person.getPhoneNumber().length() == 10){
            String phone1 = phoneFormat.substring(0, 3);
            String phone2 = phoneFormat.substring(3, 6);
            String phone3 = phoneFormat.substring(6);
//        System.out.printf("Added (Name: %s Phone: %s)%n", person.getName(), person.getPhoneNumber());
            System.out.printf("Added (Name: %s phone: " + phone1 + "-" + phone2 + "-" + phone3 + ") %n", person.getName());
        } else if (person.getPhoneNumber().length() == 7) {
            String phone1 = phoneFormat.substring(0, 3);
            String phone2 = phoneFormat.substring(3);
//            String phone3 = phoneFormat.substring(6);
//        System.out.printf("Added (Name: %s Phone: %s)%n", person.getName(), person.getPhoneNumber());
            System.out.printf("Added (Name: %s phone: " + phone1 + "-" + phone2 + ") %n", person.getName());
        }
        // System.out.printf("Added (Name: %s Phone: %s)%n", person.getName(), person.getPhoneNumber());
    }

    public static String formatWriteContact(Person contact) { // formats person object to display properly
        String phone = contact.getPhoneNumber();
        if (phone.length() == 7) {
            String phone1 = phone.substring(0, 3);
            String phone2 = phone.substring(3, 7);
            String formattedPhone = phone1 + "-" + phone2;
            return String.format("%1s%2s%15s%2s%11s", "| ", contact.getName(), "| ", formattedPhone, "|");
        } else {
            String phone1 = phone.substring(0, 3);
            String phone2 = phone.substring(3, 6);
            String phone3 = phone.substring(6, 10);
            String formattedPhone = phone1 + "-" + phone2 + "-" + phone3;
            return String.format("%1s%2s%15s%2s%7s", "| ", contact.getName(), "| ", formattedPhone, "|");
        }
    }

    public static void searchContacts() {
        grabContacts();
        if (pulledContacts.isEmpty()) {
            System.out.println("No contact found.");
        } else {
            contactExists = false;
            System.out.println("Search by name or phone number.");
            String search = scanner.nextLine().trim().toLowerCase();
            // formatPulledContacts(); // formats and adds pulled contacts to allContacts list
            for (Person contact : allContacts) { // TODO: make its own method
                if (search(contact, search)) {
                    System.out.println(formatWriteContact(contact));
                    contactExists = true;
                }
            }
            if (!contactExists) {
                System.out.printf("No contact was found with that information.%n");
                deleteContinue();
            }
        }
    }

    public static boolean search(Person contact, String search) {
        if (contact.getName().toLowerCase().contains(search)) {
            return true;
        } else return contact.getPhoneNumber().contains(search);
    }

    public static void deleteContinue() {
        System.out.printf("Would you like to search again... or nahh? (y/n)%n");
        String userResponse = scanner.nextLine();
        if (userResponse.equalsIgnoreCase("y") || userResponse.equalsIgnoreCase("yes")) {
            searchContacts();
        } else if (userResponse.equalsIgnoreCase("n") || userResponse.equalsIgnoreCase("no")) {
            runProgram();
        } else {
            System.out.println("Sorry did not catch that.");
            deleteContinue();
        }
    }

    public static void deleteSearch() {
        System.out.printf("Would you like to search again... or nahh? (y/n)%n");
        String userResponse = scanner.nextLine();
        if (userResponse.equalsIgnoreCase("y") || userResponse.equalsIgnoreCase("yes")) {
            deleteContact();
        } else if (userResponse.equalsIgnoreCase("n") || userResponse.equalsIgnoreCase("no")) {
            runProgram();
        } else {
            System.out.println("Sorry did not catch that.");
            deleteSearch();
        }
    }

    public static boolean deleteConfirm(Person contact) { // confirm to delete contact
        System.out.printf("Delete %s? (y/n)%n", formatWriteContact(contact));
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes");
    }

    public static void deleteContact() {
        grabContacts(); // checks .txt file status
        if (pulledContacts.isEmpty()) {
            System.out.println("There are no contacts to search/delete.");
        } else {
            contactExists = false; // reset static variable
            System.out.println("Search by name or phone number.");
            String search = scanner.nextLine().trim().toLowerCase();
            // formatPulledContacts(); // add pulled contacts into a static manipulable variable
            ArrayList<Integer> temp = new ArrayList<>(); // method temporary list of search result indices
            for (Person contact : allContacts) {
                if (search(contact, search)) { // run search method
                    temp.add(allContacts.indexOf(contact));
                    contactExists = true;
                }
            }
            if (!contactExists) { // first check if contact exists
                System.out.printf("No contact was found with that information.%n");
                deleteSearch();
            }
            if (temp.size() == 1) { // if only one contact was found
                if (deleteConfirm(allContacts.get(temp.get(0)))) {
                    allContacts.remove(temp.get(0).intValue());
                    try {
                        Files.writeString(path, ""); // clear existing .txt file
                        for (Person contact : allContacts) {
                            Files.write(path, Arrays.asList(formatWriteContact(contact)), StandardOpenOption.APPEND);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    runProgram();
                }
            } else if (temp.size() > 1) {
                int userSelected = isolateContact(temp);
                if (deleteConfirm(allContacts.get(temp.get(userSelected)))) {
                    allContacts.remove(temp.get(userSelected).intValue());
                    try {
                        Files.writeString(path, ""); // clear existing .txt file
                        for (Person contact : allContacts) {
                            Files.write(path, List.of(formatWriteContact(contact)), StandardOpenOption.APPEND); //
                            // intellij says List.of is better than Arrays.asList
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    runProgram();
                }
            }
        }
    }

    public static int isolateContact(ArrayList<Integer> indices) { // isolate the exact contact to delete
        System.out.printf("Select contact to delete. 1-%d%n", indices.size());
        for (int i = 0; i < indices.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, formatWriteContact(allContacts.get(indices.get(i))));
        }

        String number = scanner.nextLine();
        int selection1 = isInteger(number);

        if (selection1 >= 1) {
            if (selection1 <= indices.size()) {
                return selection1 - 1;
            } else {
                System.out.println("Not a valid selection");
                return isolateContact(indices);
            }
        } else {
            System.out.printf("Sorry did not catch that.%n");
            return isolateContact(indices);
        }
    }

    public static void checkFiles() {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void confirm() {
        System.out.println("Would you like to continue..or nahh? (y/n)");
        String userResponse = scanner.next();
        scanner.nextLine(); // clear out scanner for optionChecker scanner that is 'SCANNER.NEXTLINE()'
        if (userResponse.equalsIgnoreCase("y") || userResponse.equalsIgnoreCase("yes")) {
            runProgram();
        } else if (userResponse.equalsIgnoreCase("n") || userResponse.equalsIgnoreCase("no")) {
            run = false;
        } else {
            System.out.println("Not a valid input...try again.");
            confirm();
        }
    }

    public static void runProgram() {
        displayMenu();
        optionSelected(optionChecker());
    }

    public static void main(String[] args) {
        while (run) {
            checkFiles();
            runProgram();
        }
    }
}