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

	public Bank() throws RemoteException {
		super();
		
		accounts = new ArrayList<Account>();		
		accounts.add(new Account("User1", "pass1"));
		accounts.add(new Account("User2", "pass2"));
		accounts.add(new Account("User3", "pass3"));
	}
	
	public static void main (String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			Bank bank = new Bank();
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
				
				System.out.println("Account: " + account.getAccNum() + " just logged in" );
				account.startNewSession();
								
				return account.getSessionID();
			}
		}
		throw new InvalidLoginException();
	}

	@Override
	public double deposit(int accountnum, double amount, long sessionID)
			throws RemoteException, InvalidSessionException {
		System.out.println("making a deposit");
		return 10;
	}

	@Override
	public double withdraw(int accountnum, double amount, long sessionID)
			throws RemoteException, InvalidSessionException, InsufficientFundsException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Account inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementInterface getStatement(int accountnum, Date from, Date to, long sessionID)
			throws RemoteException, InvalidSessionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account accountDetails(long sessionID) throws RemoteException, InvalidSessionException {
		for(Account acc : accounts) {
			if(sessionID == acc.getSessionID()) {
				return acc;
			}
		}
		throw new InvalidSessionException();
	}

}
