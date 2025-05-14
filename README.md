This Java banking system demonstrates core OOP concepts through a simplified banking model. The abstract class Account (using abstraction) defines common properties like account number, balance, and transaction history, while the Transactional interface enforces deposit/withdraw operations. Encapsulation is achieved via private fields (e.g., balance) with controlled access through methods like deposit()/withdraw().

Inheritance is shown through SavingsAccount and CurrentAccount subclasses extending Account. Each overrides methods polymorphically:

SavingsAccount calculates interest (3.5%) and restricts withdrawals to available balance.

CurrentAccount allows overdraft withdrawals up to a limit, demonstrating polymorphism via unique withdraw() logic.

The Customer class associates with multiple accounts (association), managing user details and linked accounts. Composition is seen in Account, which aggregates Transaction records (immutable data via record) to track deposits/withdrawals.

A custom InsufficientFundsException handles invalid transactions, ensuring robustness. The main method tests functionality:

Creates a Customer with a savings and current account.

Performs transactions:

Savings: Deposits ₹50k, withdraws ₹10k, earns interest.

Current: Deposits ₹1L, withdraws ₹1.2L (using overdraft), then attempts another withdrawal (throws error if limit exceeded).

Displays balances and transaction histories.

Key OOP Concepts:

Encapsulation: Data (e.g., balance) is protected via methods.

Inheritance & Polymorphism: Subclasses override withdraw()/calculateInterest().

Abstraction: Account hides implementation details, exposing only essential features.

Interface: Transactional standardizes transaction methods.

Association: Customer interacts with Account objects.

Composition: Account owns Transaction objects.

The code outputs balances and transaction histories, showcasing real-world banking operations. It can be extended with features like user authentication, GUI, or database integration while maintaining OOP principles.

### OUTPUT
Interest added: ₹1400.0
Error: Exceeds overdraft limit

Accounts for Rahul Sharma
SA001 - Balance: ₹41400.0
CA001 - Balance: ₹-20000.0

Transaction History for SA001
DEPOSIT: ₹50000.0
WITHDRAWAL: ₹10000.0
DEPOSIT: ₹1400.0

Transaction History for CA001
DEPOSIT: ₹100000.0
WITHDRAWAL: ₹120000.0
