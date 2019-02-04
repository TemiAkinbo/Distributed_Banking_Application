package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfaces.StatementInterface;

public class Statement implements StatementInterface, Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Transaction> transactions;
	private Date from;
	private Date to;
	private Account account;
	
	public Statement (Account acc, Date from, Date to) {
		this.account = acc;
		this.from = from;
		this.to = to;
		this.transactions = new ArrayList<>();
	}
	

	@Override
	public int getAccountnum() {
		return this.account.accountNum ;
	}

	@Override
	public Date getStartDate() {
		return this.from;
	}

	@Override
	public Date getEndDate() {
		return this.to;
	}

	@Override
	public String getAccountName() {
		return "Account Username: " + this.account.getUsername();
	}

	@Override
	public List<Transaction> getTransactions() {
		
		for(Transaction t : this.account.getTransactions()) {
			
			if(t.getDate().after(from) && t.getDate().before(to)) {
				this.transactions.add(t);
			}
		}
		
		return this.transactions;
	}

}
