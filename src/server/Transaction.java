package server;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private double amount;
	private String type;
	private Account account;
	private double balance;
	private Date date;
	
	public Transaction(Account acc, String type) {
		this.type = type;
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
	
	public void setAmount(double amount){
		this.amount = amount;
		
		if (this.type.equals("Deposit")) {
			this.balance = this.account.getBalance() + amount;
		}
		else if (this.type.equals("Withdraw")) {
			//if(this.account.getBalance() >= amount) {
			this.balance = this.account.getBalance() - amount;
			/*} else {
				throw new InsufficientFundsException();
			}*/
		}		
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	@Override
	public String toString() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");		
		return dateFormat.format(this.date) + " " + this.type + "\t" + "amount: " + String.format("%.2f", this.amount) + " account balance: " + String.format("%.2f", this.balance);
	}
	

}
