import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;


class User {
    private String userName;
    private long contactNo;
    private String mail;
    private String address;
    private String complaints;

    private ArrayList<books> borrowedBooks = new ArrayList<>();

    void menu(String n,Scanner input, Admin admin){
        int userChoice=0;
        do {
            System.out.println("Hello "+n +" ! What would you like to do ");
            System.out.println("1. See Books ");
            System.out.println("2. Borrow Books ");
            System.out.println("3. See borrowed books ");
            System.out.println("4. Update profile  ");
            System.out.println("5. See My proile ");
            System.out.println("6. Fill complain/suggestions ");
            System.out.println("7. EXIT ");
            System.out.print(">>> ");
            userChoice= input.nextInt();
            switch (userChoice) {
                case 1:
                    admin.viewBook(input);
                    break;
                case 2:
                    borrowBooks(input, admin);
                    break;
                case 3:
                    showBorrowedBooks();
                    break;
                case 4:
                    setDetails(input);
                    break;
                case 5:
                    showUserDetails();
                    break;
                case 6:
                    User temp=fillComplain(input);
                    admin.Complaints.add(temp);
                    break;
                case 7:
                System.out.println("Exiting....");
                    break;
                default:
                System.out.println("Please ENter a valid choice ");
                    break;
            }      
        } while (userChoice!=7);
    }

    User fillComplain(Scanner input){
        User temp = new User();
        temp.userName = this.userName;
        temp.mail = this.mail;
        temp.contactNo = this.contactNo;
        temp.address = this.address;
        System.out.print("Please ENter your issue >> ");
        temp.complaints = input.nextLine();
        return temp;
    }


    void userComplaints(){
        System.out.println("Name : " + this.userName);
        System.out.println("E-mail " + this.mail);
        System.out.println("Contact number : " + this.contactNo);
        System.out.println("Issue : " + this.complaints);
        System.out.println("\n-------------------------------------------------------------");      
    }

    void setDetails(Scanner input) {
        input.nextLine();
        System.out.print("Enter Name : ");
        this.userName = input.nextLine();
        System.out.print("Enter mail : ");
        this.mail = input.nextLine();
        System.out.print("Enter address : ");
        this.address = input.nextLine();
        System.out.print("Enter contact number : ");
        this.contactNo = input.nextLong();
    }

    void showUserDetails() {
        System.out.println("Name : " + this.userName);
        System.out.println("E-mail " + this.mail);
        System.out.println("Contact number : " + this.contactNo);
        System.out.println("Address : " + this.address);
    }


    void borrowBooks(Scanner input,Admin a){
        input.nextLine();
        System.out.print("Enter Name of the book : ");
        String temp = input.nextLine();
        System.out.print("Enter ISBN number of book : ");
        int tempISBN= input.nextInt();
        Iterator<books> it = a.Allbooks.iterator();
        while (it.hasNext()){
            books b = it.next();
            if (b.getName().equalsIgnoreCase(temp) && b.getISBN() == tempISBN) {
                if (b.getQuantity()>0) {
                    borrowedBooks.add(b);
                    b.setQuantity(b.getQuantity() -1 );
                }else{
                    System.out.println("Sorry, The book is not avaialble right now");
                }
            }
        }
    }

    void showBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books borrowed");
            return;
        }else{
            for (books b : borrowedBooks) {
                b.show();
            }   
        }
    }
}

class books {
    private String Name;
    private float price;
    private int ISBN;
    private String author;
    private int quantity;

    void set(Scanner input) {
        input.nextLine();
        System.out.print("Enter Name of Book : ");
        this.Name = input.nextLine();
        System.out.print("Enter Author of Book : ");
        this.author = input.nextLine();
        System.out.print("Enter price of Book : ");
        this.price = input.nextFloat();
        System.out.print("Enter ISBN of Book : ");
        this.ISBN = input.nextInt();
        System.out.print("Enter Quantity of Book : ");
        this.quantity = input.nextInt();
    }

    void show() {
        System.out.println("Name : " + this.Name);
        System.out.println("Author : " + this.author);
        System.out.println("Price : " + this.price);
        System.out.println("ISBN : " + this.ISBN);
        System.out.println("Quantity : " + this.quantity);
        System.out.println("---------------------------------------------------------------------");
    }

    String getName() {
        return this.Name;
    }
    int getISBN(){
        return this.ISBN;
    }
    int getQuantity(){
        return this.quantity;
    }
    void setQuantity(int a){
        if (a>=0)
            this.quantity=a;
        else
            System.out.println("INVALID NUMBER ! ");
    }
}

class Admin {
    public ArrayList<User> Complaints = new ArrayList<>();
    public ArrayList<books> Allbooks = new ArrayList<>();

    public void menu(Scanner input) {
        int Adminchoice = 0;
        do {
            System.out.println("----Welcome to Admin Panel----");
            System.out.println("1. Add Book(s) ");
            System.out.println("2. Remove Book(s) ");
            System.out.println("3. View book(s) ");
            System.out.println("4. See Due book(s) ");
            System.out.println("4. View Complaints/Suggestion ");
            System.out.println("5. Exit ");
            System.out.print(">>> ");
            while (!input.hasNextInt()) {
                System.out.println("Please enter a valid choice ");
                input.nextLine();
                System.out.print(">>> ");
            }
            Adminchoice = input.nextInt();
            switch (Adminchoice) {
                case 1:
                    addBook(input);
                    break;
                case 2:
                    removeBook(input);
                    break;
                case 3:
                    viewBook(input);
                    break;
                case 4:
                    dueBooks();
                    break;
                case 5:
                    System.out.println("EXITING...");
                    break;
                default:
                    break;
            }
        } while (Adminchoice != 5);

    }

    public boolean authenticate(Scanner input) {

        System.out.print("Enter Your username : ");
        String userName = input.nextLine();

        System.out.print("Enter Your password : ");
        String pass = input.nextLine();

        String hashedEnteredPassword = Password.hashPassword(pass);
        String hashedEntereduserName = Password.hashUsername(userName);

        // Get the stored hashed password from the file
        String storedPassword = Password.getStoredPassword();

        // Get stpred username for admin
        String storedUserName = Password.getStoredUserName();

        if (storedPassword != null && storedUserName.equals(hashedEntereduserName)
                && storedPassword.equals(hashedEnteredPassword))
            return true;
        else
            return false;
    };


    public void viewComplaints(){
        Iterator<User> it = Complaints.iterator();    
        while (it.hasNext()) {
            User u = it.next();
            u.userComplaints();
        } 
    }

    public void addBook(Scanner input) {
        books b = new books();
        b.set(input);
        Allbooks.add(b);
    }

    public void removeBook(Scanner input) {
        String n;
        input.nextLine();
        System.out.print("ENter Name of the book : ");
        n = input.nextLine();

        Iterator<books> it = Allbooks.iterator();
        boolean found = false;

        while (it.hasNext()) {
            books b = it.next();
            if (b.getName().equalsIgnoreCase(n)) {
                b.show();
                it.remove();
                found = true;
                System.out.println("----BOOK DELETED SUCCESSFULLY----");
                break;
            }
        }
        if (found == false) {
            System.out.println("----BOOK \" " + n + " \" Not Found----");
        }
    }

    public void viewBook(Scanner input) {
        String n;
        input.nextLine();
        System.out.println("ENter name of the book : ");
        n = input.nextLine();

        Iterator<books> it = Allbooks.iterator();
        boolean found = false;
        while (it.hasNext()) {
            books b = it.next();
            if (b.getName().equalsIgnoreCase(n)) {
                b.show();
                found = true;
            }
        }
        if (found == false)
            System.out.println("No book found with name \"" + n + "\" ");
    }

    public void dueBooks() {

    }
}

public class LMS {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        Admin admin = new Admin();
        User user = new User();
        // if ( admin.authenticate(input)) {
        // System.out.println("Access granted");
        // }else
        // System.out.println("Access not granted");
        int Mchoice = 0;
        do {
            System.out.println("----------------WELCOME TO LIBRARY------------------------");
            System.out.println("Select any choice :- ");
            System.out.println("1. Admin ");
            System.out.println("2. User ");
            System.out.println("3. Exit ");
            System.out.print(">>> ");

            // To check for numeric inputs only
            while (!input.hasNextInt()) {
                System.out.println("Please enter a valid number ");
                input.next();
                System.out.print(">>>");
            }

            Mchoice = input.nextInt();
            input.nextLine();
            switch (Mchoice) {
                case 1:
                    System.out.print("Do you want to set a new admin password? (yes/no): ");
                    String choice = input.nextLine().toLowerCase();

                    if (choice.equalsIgnoreCase("yes")) {
                        // For new userName
                        System.out.print("Enter new UserName : ");
                        String newUserName = input.nextLine();

                        String hashedUserName = Password.hashUsername(newUserName);
                        Password.saveUserName(hashedUserName);

                        System.out.println("UserName set successfully!");

                        // For setting new password
                        System.out.print("Enter new password: ");
                        String newPassword = input.nextLine();

                        String hashedPassword = Password.hashPassword(newPassword);
                        Password.savePassword(hashedPassword);

                        System.out.println(" Password set successfully!");

                    } else {
                        if (admin.authenticate(input))
                            admin.menu(input);
                        else
                            System.out.print("all not ok ");
                    }
                    break;
                case 2:
                    
                    break;
                case 3:
                    break;
                case 4:
                    System.out.print("EXITING");
                    break;
                default:
                    System.out.println("Invalid choice.. Please Select from given options ");
                    break;
            }
        } while (Mchoice != 3);

        input.close();
    }

}
