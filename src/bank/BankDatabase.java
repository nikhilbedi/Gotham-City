package bank;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BankDatabase {
	double safeBalance;
	int nextAccountNumber;
	Map<Integer, BankAccount> accounts = new HashMap<Integer, BankAccount>();
	Map<String, LinkedList<Double>> accountNumbers = new HashMap<String, LinkedList<Double>>();
	//public loanHolders;
	
	public BankDatabase() {
		safeBalance = 500000;
		nextAccountNumber = 101;
	}
	
	public BankDatabase(double sB) {
		safeBalance = sB;
		nextAccountNumber = 101;
	}
	
	public BankAccount addAccount(double amount, String customerName) {
		BankAccount newAccount = new BankAccount(amount, nextAccountNumber, customerName);
		accounts.put(nextAccountNumber, newAccount);
		if(!accountNumbers.containsKey(customerName))
			accountNumbers.put(customerName, new LinkedList<Double>());
		accountNumbers.get(customerName).add(amount);
		nextAccountNumber++;
		return newAccount;
	}
	
	public BankAccount removeAccount(String customerName, int accountNumber) {
		BankAccount removedAccount = accounts.get(accountNumber);
		accounts.remove(accountNumber);
		accountNumbers.get(customerName).remove(accountNumber);
		return removedAccount;
	}
}
