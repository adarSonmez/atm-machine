import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Scanner;

strictfp class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        GregorianCalendar calendar = new GregorianCalendar();
        int hourOfDay = calendar.get(GregorianCalendar.HOUR_OF_DAY);

        // Display current time
        System.out.println(calendar.getTime().toString());

        // Create 100 accounts in an array
        int id = 0;
        Account[] accounts = new Account[1000];
        File file = new File("accounts.txt");
        if (file.delete() && file.createNewFile()) nothing();

        // Determine the working hours of the system
        while (hourOfDay >= 9 && hourOfDay < 17) {
            System.out.print("Enter 1 to create a new account, or enter 2 to trade your current account: ");
            int userStatus = input.nextInt();
            while (userStatus != 1 && userStatus != 2 && userStatus != 3) {
                System.out.print("Invalid input, enter again: ");
                userStatus = input.nextInt();
            }

            if (userStatus == 1) {
                // Determine user limit
                if (id >= 999) {
                    System.out.println("Sorry, we cannot accept a new user.");
                } else {
                    newAccount(accounts, id, input, file);
                    id++;
                }
            } else {
                currentAccount(accounts, input, file);
            }
            // Once you exit, the system will prompt for an id again
            hourOfDay = calendar.get(GregorianCalendar.HOUR_OF_DAY);
        }
        // Out of working hours
        System.out.println("The System will open at 09.00");
        System.exit(0);
    }

    public static void newAccount(Account[] a, int id, Scanner input, File file) throws IOException {
        a[id] = new Account(id, 0);
        long password = 0;

        while (String.valueOf(password).length() != 4) {
            System.out.print("Set your password (must be a 4-digit number): ");
            password = input.nextInt();
            a[id].setPassword(password);
        }

        System.out.println("You have successfully registered in our system.");
        System.out.println("Your id is " + a[id].getId());
        System.out.println("Your password is " + password);

        write(id, 0, file);
    }

    public static void currentAccount(Account[] accounts, Scanner input, File file) throws IOException {
        // Prompt user to enter an id
        System.out.print("Enter your id: ");
        int id = input.nextInt();
        System.out.print("Enter your password: ");
        long num = input.nextInt();

        if (isValidAccount(id, accounts, num)) {
            int choice;
            do {
                // Get user choice
                choice = displayMainMenu(input);
                if (isTransaction(choice)) {
                    executeTransaction(choice, accounts, id, input, file);
                }
            } while (choice != 4); // If 4 exit main menu
        }
    }

    public static boolean isTransaction(int choice) {
        return choice > 0 && choice < 4;
    }

    // Check password
    public static boolean isValidAccount(int id, Account[] a, long num) {
        if (id > Account.getNumberOfAccounts()) {
            System.out.println("This account is not valid!");
            return false;
        }

        if (a[id].getPassword() == num)
            return true;

        System.out.println("Incorrect password!");
        return false;
    }

    public static int displayMainMenu(Scanner input) {
        System.out.print(
                "\nMain menu\n1: check balance\n2: withdraw" +
                        "\n3: deposit\n4: exit\nEnter a choice: ");
        return input.nextInt();
    }

    public static void executeTransaction(int c, Account[] a, int id, Scanner input, File file) throws IOException {
        switch (c) {
            // Viewing current balance
            case 1 -> System.out.println("The balance is " + a[id].getBalance());
            // Withdraw money
            case 2 -> {
                System.out.print("Enter an amount to withdraw: ");
                double withdraw = input.nextDouble();
                if (a[id].getBalance() < withdraw) {
                    System.out.println("Insufficient balance!");
                } else
                    a[id].withdraw(withdraw);
            }
            // Deposit money
            case 3 -> {
                System.out.print("Enter an amount to deposit: ");
                a[id].deposit(input.nextDouble());
            }
        }
        write(id, a[id].getBalance(), file);
    }

    public static void write(int id, double balance, File file) throws IOException {
        FileWriter printer = new FileWriter(file, true);
        printer.write("ID: " + id + ", Balance: " + balance + "\n");
        printer.close();
    }

    public static void nothing() {
        // nothing
    }
}