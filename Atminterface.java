/*Atm interface :-
1.Create a class to represent the ATM machine.

2. Design the user interface for the ATM, including options such as withdrawing, depositing, trasfer fund, transaction history and
checking the balance.

3. Implement methods for each option, such as withdraw(amount), deposit(amount), and
checkBalance().

4. Create a class to represent the user's bank account, which stores the account balance.

5. Connect the ATM class with the user's bank account class to access and modify the account
balance.

6. Validate user input to ensure it is within acceptable limits (e.g., sufficient balance for withdrawals).

7. Display appropriate messages to the user based on their chosen options and the success or failure
of their transactions. */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BankAccount {
    private double balance;
    private List<String> transactionHistory;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amt) {
        balance += amt;
        transactionHistory.add("Deposit: +" + amt);
    }

    public boolean withdraw(double amt) {
        if (amt <= balance) {
            balance -= amt;
            transactionHistory.add("Withdrawal: -" + amt);
            return true;
        } else {
            System.out.println("Insufficient funds!");
            return false;
        }
    }

    public boolean transfer(BankAccount rec, double amt) {
        if (withdraw(amt)) {
            rec.deposit(amt);
            transactionHistory.add("Transfer to " + rec + ": -" + amt);
            return true;
        } else {
            System.out.println("Transfer failed. Insufficient funds!");
            return false;
        }
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}

class ATM {
    private BankAccount userAccount;
    private Scanner scanner;

    public ATM(BankAccount userAcc) {
        this.userAccount = userAcc;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Transfer Funds");
        System.out.println("4. Check Balance");
        System.out.println("5. View Transaction History");
        System.out.println("6. Exit");
    }

    public void performTransaction(int choice) {
        switch (choice) {
            case 1:
                withdraw();
                break;
            case 2:
                deposit();
                break;
            case 3:
                transferFunds();
                break;
            case 4:
                checkBalance();
                break;
            case 5:
                viewTransactionHistory();
                break;
            case 6:
                System.out.println("Exiting. Thank you!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void withdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            if (userAccount.withdraw(amount)) {
                System.out.println("Withdrawal successful. Remaining balance: $" + userAccount.getBalance());
            }
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    private void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            userAccount.deposit(amount);
            System.out.println("Deposit successful. Updated balance: $" + userAccount.getBalance());
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    private void transferFunds() {
        System.out.print("Enter recipient's account balance: ");
        double recipientAmount = scanner.nextDouble();
        if (recipientAmount > 0) {
            System.out.print("Enter transfer amount: ");
            double transferAmount = scanner.nextDouble();
            if (transferAmount > 0) {
                BankAccount recipient = new BankAccount(recipientAmount);
                if (userAccount.transfer(recipient, transferAmount)) {
                    System.out.println("Transfer successful. Remaining balance: $" + userAccount.getBalance());
                }
            } else {
                System.out.println("Invalid transfer amount. Please enter a positive value.");
            }
        } else {
            System.out.println("Invalid recipient account balance. Please enter a positive value.");
        }
    }

    private void checkBalance() {
        System.out.println("Current balance: $" + userAccount.getBalance());
    }

    private void viewTransactionHistory() {
        List<String> history = userAccount.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("Transaction history is empty.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : history) {
                System.out.println(transaction);
            }
        }
    }
}

public class Atminterface
 {
    public static void main(String[] args) {

        BankAccount userAccount = new BankAccount(10000.0);

        
        ATM atm = new ATM(userAccount);

        while (true) {
            
            atm.displayMenu();
            System.out.print("Enter your choice: ");
            int choice = new Scanner(System.in).nextInt();

            
            atm.performTransaction(choice);
        }
    }
}
