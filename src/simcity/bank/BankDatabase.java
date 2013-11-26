package simcity.bank;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BankDatabase {
	public double safeBalance;
	int nextAccountNumber;
	public Map<Integer, BankAccount> accounts = new HashMap<Integer, BankAccount>();
	public Map<String, LinkedList<Integer>> accountNumbers = new HashMap<String, LinkedList<Integer>>();
	//public loanHolders;
	public Map<String, Double> loanHolders = new HashMap<String, Double>();
	
	public BankDatabase() {
		safeBalance = 500000;
		nextAccountNumber = 101;
	}
	
	public BankDatabase(double sB) {
		safeBalance = sB;
		nextAccountNumber = 101;
	}
	
	
	//Adds new account to the database; updates both lists
	
	public BankAccount addAccount(double amount, String customerName) {
		
		BankAccount newAccount = new BankAccount(amount, nextAccountNumber, customerName);
		accounts.put(nextAccountNumber, newAccount);
		
		if(!accountNumbers.containsKey(customerName))
			accountNumbers.put(customerName, new LinkedList<Integer>());
		accountNumbers.get(customerName).add(nextAccountNumber);
		
		nextAccountNumber++;
		return newAccount;
	}
	
	
	//Removes an account from the database; updates both lists
	
	public void removeAccount(String customerName, int accountNumber) {
		accounts.remove(accountNumber);
		accountNumbers.get(customerName).remove(0);
		accountNumbers.remove(customerName);
	}
	
	
	//returns total value of person's accounts. Used for determining loan qualifiers
	
	public double getTotalAccountBalance(String name) {
		double total = 0;
		LinkedList<Integer> custAccountNumbers= accountNumbers.get(name);
		
		for(Integer i : custAccountNumbers)
			total += accounts.get(i).accountBalance;
		
		return total;
	}
}
