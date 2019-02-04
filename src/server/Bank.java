package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.InsufficientFundsException;
import exceptions.InvalidLoginException;
import exceptions.InvalidSessionException;
import interfaces.BankInterface;

public class Bank extends UnicastRemoteObject implements BankInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Account> accounts;

	public Bank() throws RemoteException {
		super();

		Account account1 = new Account("User1", "pass1");
		account1.setBalance(1500);

		Account account2 = new Account("User2", "pass2");
		account2.setBalance(1000);

		Account account3 = new Account("User2", "pass2");
		account3.setBalance(50);
		
		accounts = new ArrayList<Account>();		
		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
	}
	
	public static void main (String[] args) throws RemoteException {
	    String port = args[0];
		int portNumber = Integer.parseInt(port);

		System.setProperty("java.security.policy", "file:allAccess.policy");

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Bank bank = new Bank();
			Registry registry = LocateRegistry.getRegistry(portNumber);
			registry.rebind("Bank", bank);
			
			System.out.println("Bank Server ready");
		}catch (Exception e) {
			System.out.println("Bank Server failed: " + e);
		}
	}

	@Override
	public long login(String username, String password) throws RemoteException, InvalidLoginException {
		//Iterate through list of accounts in bank
		for(Account account : accounts) {
			if (username.equals(account.getUsername()) && password.equals(account.getPassword())) {
				
				account.startNewSession();

				System.out.println("Account: " + account.getAccNum() + " just logged in with Session ID " + account.getSessionID());

				return account.getSessionID();
			}
		}
		throw new InvalidLoginException();
	}

	@Override
	public double deposit(int accountnum, double amount, long sessionID)
			throws RemoteException, InvalidSessionException{
		Account account = getAccount(accountnum, sessionID);
		
		Transaction t = new Transaction(account);
		t.deposit(amount);
		
		account.makeTransaction(t);
		
		return account.getBalance();
	}

	@Override
	public double withdraw(int accountnum, double amount, long sessionID) 
			throws RemoteException, InvalidSessionException, InsufficientFundsException {
		
		Account account = getAccount(accountnum, sessionID);
		
		Transaction t = new Transaction(account);
		t.withdraw(amount);
		
		account.makeTransaction(t);
				
		return account.getBalance();
	}

	@Override
	public double inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSessionException {
		Account account = getAccount(accountnum, sessionID);

		return account.getBalance();
	}

	@Override
	public String getStatement(int accountnum, Date startDate, Date endDate, long sessionID)
			throws RemoteException, InvalidSessionException {
		Account account = getAccount(accountnum, sessionID);

		List<Transaction> transactions = account.getTransactions();
		String returnString = "*** Account Statement **** \n" +
				"Account Number: " + accountnum + "\n" +
				"Start Date: " + startDate.toString() + "\n" +
				"End Date: " + endDate.toString() + "\n\n" +
				"";

		for (Transaction transaction : transactions) {
			if (transaction.getDate().after(startDate) && transaction.getDate().before(endDate)) {
				returnString += transaction.getDate().toString() + " " + transaction.getType() + " " +
						transaction.getAmount() + " balance " + transaction.getBalance() + "\n";
			}
		}

		return returnString;
	}

	public Account getAccount(int accountNumber, long sessionID) throws RemoteException, InvalidSessionException {
		for(Account acc : accounts) {
			if(acc.getAccNum() == accountNumber && acc.getSessionID() == sessionID && acc.getSessionStatus()) {
				return acc;
			}
		}
		throw new InvalidSessionException();
	}

}
