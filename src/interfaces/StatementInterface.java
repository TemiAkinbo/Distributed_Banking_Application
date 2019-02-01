package interfaces;

import java.io.Serializable;
import java.util.*;

public interface StatementInterface extends Serializable {
	
	public int getAccountnum();
	public Date getStartDate();
	public Date getEndDate();
	public String getAccountName();
	public List getTransactions();
}
