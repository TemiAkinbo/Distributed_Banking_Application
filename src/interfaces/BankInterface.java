package interfaces;

import java.rmi.*;
import java.util.Date;
import server.Account;
import exceptions.*;

public interface BankInterface extends Remote {
	long login(String username, String password) throws RemoteException, InvalidLoginException;
	double deposit(int accountnum, double amount, long sessionID) throws RemoteException, InvalidSessionException;
	double withdraw(int accountnum, double amount, long sessionID) throws RemoteException, InvalidSessionException, InsufficientFundsException;
	int inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSessionException;
	StatementInterface getStatement(int accountnum, Date from, Date to, long sessionID) throws RemoteException, InvalidSessionException;
	Account accountDetails(long sessionID) throws RemoteException, InvalidSessionException;
	
}
