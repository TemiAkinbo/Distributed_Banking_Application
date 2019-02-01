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
	private long sessionID = 0;
	private Timer timer;
	private List<Transaction> transactions;
	
	private static final int MAX_SESSION_LENGTH = 5*60;
	private static final long DELAY = 1000;
	
	private boolean sessionAlive;
	private int timeAlive;
	private static int nxtAccNum = 5628291;
	
	
	public Account (String username, String password ) throws RemoteException {
		this.transactions = new ArrayList<>();
		this.username = username;
		this.password = password;
		this.accountNum = nxtAccNum;
		this.balance = 0;
		this.timer = new Timer();
		
		nxtAccNum++;
		
	}
	
	public void makeTransaction(Transaction t) {
		this.transactions.add(t);
	}
	
	public void startNewSession() {
		
		this.sessionID = (long) (Math.random() * 9000000 + 1) ;
		this.sessionAlive = true;
		
		this.timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				timeAlive++;
				
				if(timeAlive == MAX_SESSION_LENGTH) {
					sessionAlive = false;
					timer.cancel();
				}
				
								
			}
			
		}, new Date(System.currentTimeMillis()), DELAY);
		
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
		return this.sessionAlive;
	}
	
	public long getSessionID() {
		return this.sessionID;
	}

}
