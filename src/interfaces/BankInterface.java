package interfaces;

import java.rmi.*;
import java.util.Date;
import server.Account;
import exceptions.*;

public interface BankInterface extends Remote {
	long login(String username, String password) throws RemoteException, InvalidLoginException;
	double deposit(int accountnum, double amount, long sessionID) throws RemoteException, InvalidSessionException, InsufficientFundsException;
	double withdraw(int accountnum, double amount, long sessionID) throws RemoteException, InvalidSessionException, InsufficientFundsException;
	double inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSessionException;
	StatementInterface getStatement(int accountnum, Date from, Date to, long sessionID) throws RemoteException, InvalidSessionException;

}
