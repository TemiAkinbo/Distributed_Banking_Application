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
import interfaces.StatementInterface;

public class Bank extends UnicastRemoteObject implements BankInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Account> accounts;

	public Bank(int port) throws RemoteException {
		super(port);

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
		System.setProperty("java.rmi.server.hostname", "localhost");
		System.setProperty("java.security.policy", "allAccess.policy");
		int port = Integer.parseInt(args[0]);

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Bank bank = new Bank(port);
			Registry registry = LocateRegistry.getRegistry();
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
			throws RemoteException, InvalidSessionException {
		Account account = getAccount(accountnum, sessionID);
		
		Transaction deposit = new Transaction(account, "Deposit");
		deposit.setAmount(amount);
		
		account.makeTransaction(deposit);
		
		return account.getBalance();
	}

	@Override
	public double withdraw(int accountnum, double amount, long sessionID)
			throws RemoteException, InvalidSessionException, InsufficientFundsException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSessionException {
		Account account = getAccount(accountnum, sessionID);

		return account.getBalance();
	}

	@Override
	public StatementInterface getStatement(int accountnum, Date from, Date to, long sessionID)
			throws RemoteException, InvalidSessionException {
		// TODO Auto-generated method stub
		return null;
	}

	public Account getAccount(int accountNumber, long sessionID) throws RemoteException, InvalidSessionException {
		for(Account acc : accounts) {
			if(acc.getAccNum() == accountNumber && acc.getSessionID() == sessionID) {
				return acc;
			}
		}
		throw new InvalidSessionException();
	}

}
