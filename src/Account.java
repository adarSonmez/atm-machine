import java.util.Date;

public class Account {
	// Data fields
	private static final double MONTHLY_INTEREST_RATE = 8.24;
	private static int numberOfAccounts = 0;
	private final int id;
	private double balance;
	private long password;
	private final long dateCreated;

	// Constructor
	Account(int id, double newBalance) {
		this.id = id;
		balance = newBalance;
		password = 0; // default password
		dateCreated = new Date().getTime();
		numberOfAccounts++;
	}

	// Mutator methods
	public void setPassword(long newPassword) {
		password = newPassword;
	}

	public double setBalance(double balance) {
		long currentTime = new Date().getTime();
		double interestedAmount = (int)(currentTime - dateCreated) / 1000.0 * (MONTHLY_INTEREST_RATE / 2_629_743);
		return balance + interestedAmount;
	}

	// Accessor methods
	public int getId() {
		return id;
	}

	public double getBalance() {
		balance = setBalance(balance);
		return balance;
	}

	public long getPassword() {
		return password;
	}

	public static int getNumberOfAccounts() {
		return numberOfAccounts;
	}

	// Decrease the balance by the given amount
	public void withdraw(double amount) {
		balance -= amount;
	}

	// Increase the balance by the given amount
	public void deposit(double amount) {
		balance += amount;
	}
}