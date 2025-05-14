/*
Problem Statement: Banking System
Create a banking system with:

1.Different account types (Savings, Current)
2.Transaction management
3.Customer details
4.Interest calculation for savings accounts
5.Overdraft facility for current accounts
6.Exception handling for invalid transactions
*/


import java.util.ArrayList;
import java.util.List;

// Custom exception for banking operations
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Interface for transactional operations
interface Transactional {
    void deposit(double amount);
    void withdraw(double amount) throws InsufficientFundsException;
}

// Abstract class for Bank Account (Abstraction)
abstract class Account implements Transactional {
    private String accountNumber;
    private double balance;
    private Customer customer;
    private List<Transaction> transactions = new ArrayList<>();

    // Encapsulation with constructor/getters
    public Account(String accountNumber, Customer customer) {
        this.accountNumber = accountNumber;
        this.customer = customer;
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public Customer getCustomer() { return customer; }
    protected void setBalance(double balance) { this.balance = balance; }

    // Composition: Account contains transactions
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History for " + accountNumber);
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // Abstract method for interest calculation
    public abstract void calculateInterest();
}

// Inheritance: SavingsAccount extends Account
class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 3.5;

    public SavingsAccount(String accountNumber, Customer customer) {
        super(accountNumber, customer);
    }

    // Polymorphism through method overriding
    @Override
    public void calculateInterest() {
        double interest = getBalance() * INTEREST_RATE / 100;
        deposit(interest);
        System.out.println("Interest added: ₹" + interest);
    }

    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
        addTransaction(new Transaction("DEPOSIT", amount));
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > getBalance()) {
            throw new InsufficientFundsException("Insufficient funds in savings account");
        }
        setBalance(getBalance() - amount);
        addTransaction(new Transaction("WITHDRAWAL", -amount));
    }
}

// Inheritance: CurrentAccount extends Account
class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, Customer customer, double overdraftLimit) {
        super(accountNumber, customer);
        this.overdraftLimit = overdraftLimit;
    }

    // Polymorphism with overdraft facility
    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > (getBalance() + overdraftLimit)) {
            throw new InsufficientFundsException("Exceeds overdraft limit");
        }
        setBalance(getBalance() - amount);
        addTransaction(new Transaction("WITHDRAWAL", -amount));
    }

    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
        addTransaction(new Transaction("DEPOSIT", amount));
    }

    @Override
    public void calculateInterest() {
        System.out.println("Current accounts don't earn interest");
    }
}

// Composition: Transaction record
record Transaction(String type, double amount) {
    @Override
    public String toString() {
        return type + ": ₹" + (amount >= 0 ? amount : -amount);
    }
}

// Association: Customer class
class Customer {
    private String name;
    private String panNumber;
    private List<Account> accounts = new ArrayList<>();

    public Customer(String name, String panNumber) {
        this.name = name;
        this.panNumber = panNumber;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void displayAccounts() {
        System.out.println("\nAccounts for " + name);
        for (Account acc : accounts) {
            System.out.println(acc.getAccountNumber() + " - Balance: ₹" + acc.getBalance());
        }
    }
}

// Main class to test the banking system
public class BankingSystem {
    public static void main(String[] args) {
        // Create customers
        Customer customer1 = new Customer("Rahul Sharma", "ABCDE1234F");
        
        // Create accounts
        SavingsAccount savings = new SavingsAccount("SA001", customer1);
        CurrentAccount current = new CurrentAccount("CA001", customer1, 25000);
        
        customer1.addAccount(savings);
        customer1.addAccount(current);

        // Perform transactions
        try {
            savings.deposit(50000);
            savings.withdraw(10000);
            savings.calculateInterest();

            current.deposit(100000);
            current.withdraw(120000);
            current.withdraw(10000);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display results
        customer1.displayAccounts();
        savings.printTransactionHistory();
        current.printTransactionHistory();
    }
}