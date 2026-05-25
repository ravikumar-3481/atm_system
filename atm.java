import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class atm {
    public static class Account {
        String holderName;
        long accountNumber;
        int pin;
        double balance;
        long idnumber;
        long mobileNumber;
        String email;
        String address;

        Account(String holderName, long accountNumber, int pin, double balance, long idnumber, long mobileNumber, String email, String address) {
            this.holderName = holderName;
            this.accountNumber = accountNumber;
            this.idnumber = idnumber;
            this.mobileNumber = mobileNumber;
            this.email = email;
            this.address = address;
            this.pin = pin;
            this.balance = balance;
            
        }

        @Override
        public String toString() {
            return "\n"+
                   "======Account Detailsand Information======\n" +
                   "Holder Name: " + holderName + "\n" +
                   "Account Number: " + accountNumber + "\n" +
                   "ID Number: " + idnumber + "\n" +
                   "Mobile Number: " + mobileNumber + "\n" +
                   "Email: " + email + "\n" +
                   "Address: " + address + "\n" +
                   "Balance: " + balance + "\n" +
                   "=========================================" + "\n";
        }
    }

    private static final Map<Long, Account> accountMap = new HashMap<>();
    private static long[] accountNumbers = new long[0];

    private static void addAccountNumber(long accountNumber) {
        accountNumbers = Arrays.copyOf(accountNumbers, accountNumbers.length + 1);
        accountNumbers[accountNumbers.length - 1] = accountNumber;
    }

    public static void addAccountDetails(String holderName, long accountNumber, int pin, double balance , long idnumber, long mobileNumber, String email, String address) {
        if (accountMap.containsKey(accountNumber)) {
            System.out.println("Failed to add account: account number already exists.");
            return;
        }
        Account account = new Account(holderName, accountNumber, pin, balance, idnumber, mobileNumber, email, address);
        accountMap.put(accountNumber, account);
        addAccountNumber(accountNumber);
    }

    public static void printAccountDetails(long accountNumber) {
        Account account = accountMap.get(accountNumber);
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("Account not found for number: " + accountNumber);
        }
    }

    private static Account authenticate(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        Account account = accountMap.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return null;
        }

        System.out.print("Enter PIN: ");
        int pin = scanner.nextInt();

        if (account.pin != pin) {
            System.out.println("Authentication failed. Check account number and PIN.");
            return null;
        }

        return account;
    }

    private static void deposit(Scanner scanner) {
        Account account = authenticate(scanner);
        if (account == null) {
            return;
        }

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        account.balance += amount;
        System.out.println("Deposit successful. Updated balance: " + account.balance);
    }

    private static void withdraw(Scanner scanner) {
        Account account = authenticate(scanner);
        if (account == null) {
            return;
        }

        System.out.print("Enter withdraw amount: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }

        if (amount > account.balance) {
            System.out.println("Insufficient funds. Current balance: " + account.balance);
            return;
        }

        account.balance -= amount;
        System.out.println("Withdrawal successful. Updated balance: " + account.balance);
    }

    private static long generateUniqueAccountNumber() {
        long accountNumber;
        do {
            accountNumber = 1000000L + (long) (Math.random() * 9000000L);
        } while (accountMap.containsKey(accountNumber));
        return accountNumber;
    }

    public static void accountMenu() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println();
            System.out.println("ATM System Menu:");
            System.out.println("1. Add Account Details");
            System.out.println("2. View Account Details");
            System.out.println("3. Deposit Amount");
            System.out.println("4. Withdraw Amount");
            System.out.println("5. View Balance ");
            System.out.println("6. Change Pin ");
            System.out.println("7. Exit");
            System.out.print("\nEnter your Choice :  ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Holder Name: ");
                    String holderName = scanner.next();
                    long accountNumber = generateUniqueAccountNumber();
                    System.out.print("Enter Your Id Number: ");
                    long idnumber = scanner.nextLong();
                    System.out.print("Enter Your Mobile Number: ");
                    long mobileNumber = scanner.nextLong();
                    System.out.print("Enter Your Email: ");
                    String email = scanner.next();
                    System.out.print("Enter Your Address: ");
                    String address = scanner.next();
                    System.out.print("Enter PIN: ");
                    int pin = scanner.nextInt();
                    double balance = 0.0; // Default balance for new accounts
                    System.out.print("Enter deposit amount Balance: ");
                    double depositamount = scanner.nextDouble();
                    balance += depositamount;
                    addAccountDetails(holderName, accountNumber, pin, balance, idnumber, mobileNumber, email, address);
                    System.out.println("Account added successfully! Your Account Number: " + accountNumber);
                    break;
                    
                case 2:
                    System.out.println();
                    Account authenticatedAccount = authenticate(scanner);
                    if (authenticatedAccount != null) {
                        printAccountDetails(authenticatedAccount.accountNumber);
                    }
                    break;
                case 3:
                    System.out.println();
                    deposit(scanner);
                    break;
                case 4:
                    System.out.println();
                    withdraw(scanner);
                    break;
                case 5:
                    System.out.println();
                    authenticatedAccount = authenticate(scanner);
                    if (authenticatedAccount != null) {
                        System.out.println("Current Balance: " + authenticatedAccount.balance);
                    }
                    break;
                case 6:
                    System.out.println();
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextLong();
                    Account account = accountMap.get(accountNumber);
                    if (account != null) {
                        System.out.print("Enter current PIN: ");
                        int currentPin = scanner.nextInt();
                        if (account.pin == currentPin) {
                            System.out.print("Enter new PIN: ");
                            int newPin = scanner.nextInt();
                            account.pin = newPin;
                            System.out.println("PIN changed successfully!");
                        } else {
                            System.out.println("Incorrect current PIN.");
                        }
                    } else {
                        System.out.println("Account not found for number: " + accountNumber);
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        
    }

}