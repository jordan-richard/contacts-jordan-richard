public class Person {
    //constructor
    public Person(String nameP, String numP) {
        this.name = nameP;
        this.phoneNumber = numP;
    }

    //info
    private String name;
    private String phoneNumber;

    //getters
    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getContactInfo() {
        return this.name + " " + this.phoneNumber + "\n";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String number) {
        this.phoneNumber = number;
    }}