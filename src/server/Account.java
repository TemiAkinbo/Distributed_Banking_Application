package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

//Account class holds information on account details 
public class Account implements Serializable {

	private static final long serialVersionUID = 227L;

	private String username, password;
	private double balance;
	public int accountNum;
	private List<Transaction> transactions;	

	private Session session;
	
	private static int nxtAccNum = 5628291;
	
	//Constructor
	public Account (String username, String password ) throws RemoteException {
		this.transactions = new ArrayList<>();
		this.username = username;
		this.password = password;
		this.accountNum = nxtAccNum;
		this.balance = 0;
		
		nxtAccNum++;
		
	}
	
	//add Transaction to transaction list and update account balance
	public void makeTransaction(Transaction t) {
		this.transactions.add(t);
		this.setBalance(t.getBalance());
	}
	
	//called when a User logs in to start a new session
	public void startNewSession() {
		
		session = new Session();
	}
	
	
	//GETTERS AND SETTERS
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username; 
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getAccNum() {
		return this.accountNum;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void setBalance(double amount) {
		this.balance = amount;
	}
	
	public boolean getSessionStatus() {
		return this.session.getSessionActive();
	}
	
	public long getSessionID() {
		return this.session.getSessionID();
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}


}
