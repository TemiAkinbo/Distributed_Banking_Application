package server;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import interfaces.StatementInterface;

public class Statement implements StatementInterface, Serializable {

	@Override
	public int getAccountnum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccountName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

}
