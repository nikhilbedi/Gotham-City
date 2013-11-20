package bank;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BankDatabase {
	double safeBalance;
	int nextAccountNumber;
	Map<Integer, BankAccount> accounts = new HashMap<Integer, BankAccount>();
<<<<<<< HEAD
	Map<String, LinkedList<Integer>> accountNumbers = new HashMap<String, LinkedList<Integer>>();
	//public loanHolders;
	public Map<String, Double> loanHolders = new HashMap<String, Double>();
=======
	Map<String, LinkedList<Double>> accountNumbers = new HashMap<String, LinkedList<Double>>();
	//public loanHolders;
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	
	public BankDatabase() {
		safeBalance = 500000;
		nextAccountNumber = 101;
	}
	
	public BankDatabase(double sB) {
		safeBalance = sB;
		nextAccountNumber = 101;
	}
	
<<<<<<< HEAD
	//Adds new account to the database; updates both lists
	public BankAccount addAccount(double amount, String customerName) {
		
		BankAccount newAccount = new BankAccount(amount, nextAccountNumber, customerName);
		accounts.put(nextAccountNumber, newAccount);
		
		if(!accountNumbers.containsKey(customerName))
			accountNumbers.put(customerName, new LinkedList<Integer>());
		accountNumbers.get(customerName).add(nextAccountNumber);
		
=======
	public BankAccount addAccount(double amount, String customerName) {
		BankAccount newAccount = new BankAccount(amount, nextAccountNumber, customerName);
		accounts.put(nextAccountNumber, newAccount);
		if(!accountNumbers.containsKey(customerName))
			accountNumbers.put(customerName, new LinkedList<Double>());
		accountNumbers.get(customerName).add(amount);
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
		nextAccountNumber++;
		return newAccount;
	}
	
<<<<<<< HEAD
	//Removes an account from the database; updates both lists
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	public BankAccount removeAccount(String customerName, int accountNumber) {
		BankAccount removedAccount = accounts.get(accountNumber);
		accounts.remove(accountNumber);
		accountNumbers.get(customerName).remove(accountNumber);
		return removedAccount;
	}
<<<<<<< HEAD
	
	//returns total value of person's accounts. Used for determining loan qualifiers
	public double getTotalAccountBalance(String name) {
		double total = 0;
		LinkedList<Integer> custAccountNumbers= accountNumbers.get(name);
		
		for(Integer i : custAccountNumbers)
			total += accounts.get(i).accountBalance;
		
		return total;
	}
}
=======
}
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
