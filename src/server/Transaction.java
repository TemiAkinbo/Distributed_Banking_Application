package server;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import exceptions.InsufficientFundsException;

public class Transaction implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private double amount;
	private String type;
	private Account account;
	private double balance;
	private Date date;
	
	public Transaction(Account acc) {
		this.balance = 0;
		this.account = acc;
		this.date = new Date(System.currentTimeMillis());
		
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setAmount(double amount) throws InsufficientFundsException{
		this.amount = amount;
		
		if (this.type.equals("Deposit")) {
			this.balance = this.account.getBalance() + amount;
		}
		else if (this.type.equals("Withdraw")) {
			if(this.account.getBalance() >= amount) {
			this.balance = this.account.getBalance() - amount;
			} else {
				throw new InsufficientFundsException();
			}
		}
		
	}
	
	public void deposit(double amount) {		
		this.type = "Deposit";
		
		this.amount = amount;
		this.balance = this.account.getBalance() + amount;
	}
	
	public void withdraw(double amount) throws InsufficientFundsException{
		this.type = "Withdraw";		
		this.amount = amount;
		
		if(this.account.getBalance() >= amount) {			
			this.balance = this.account.getBalance() - amount;			
		} 
			
		else {
			throw new InsufficientFundsException();
		}
			
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	@Override
	public String toString() {		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");		
		return dateFormat.format(this.date) + " " + this.type + "\t" + "amount: " + String.format("%.2f", this.amount) + " account balance: " + String.format("%.2f", this.balance);
	}
	

}
