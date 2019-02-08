package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import exceptions.InsufficientFundsException;
import exceptions.InvalidLoginException;
import exceptions.InvalidSessionException;
import interfaces.BankInterface;

public class ATM {

	/***
	 * The ATM class is the client program of the RMI application 
	 * within the program we can interact with methods on the server 
	 * the program must take in parameters for the hostname, port
	 * method call and the parameters required by the method
	 * @param args
	 */
	
	// MAIN METHOD
	public static void main (String[] args) {
		String host = args[0];
		String port = args[1];
		int portNumber = Integer.parseInt(port);
		
		System.setProperty("java.security.policy", "file:allAccess.policy"); // Setting the security policy system property to the programs policy file  

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager()); // initialise Security manager
		}

		
		BankInterface bank = null;
		String operation = args[2];

		try {
			Registry registry = LocateRegistry.getRegistry(host, portNumber);
			bank = (BankInterface) registry.lookup("Bank"); // get remote bank object
		}
		catch (Exception e) {
			System.err.println("ATM client exception: ");
			e.printStackTrace();
		}
		

		switch(operation) {
			case "login": 
				try {
					// login method takes in two string parameters username and password, 
					// these are entered as command line arguments. The method call returns 
					// a randomly generated session ID for the account.
					long id = bank.login(args[3], args[4]); 
					System.out.println("Login Successful new sessionID: " + id);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidLoginException e) {
					e.printStackTrace();
				}

				break;

			case "inquiry":
				try{
					// inquiry method takes in an integer and long parameter; account number and session ID, 
					// these are entered as command line arguments. The method call returns 
					// the accounts current balance.
					double balance = bank.inquiry(Integer.parseInt(args[3]), Long.parseLong(args[4]));
					System.out.println("Balance: " + balance);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidSessionException e) {
					System.out.println("Your Session ID is invalid. Try logging in again.");
				} catch (NumberFormatException nfe) {
					System.out.println("Your Account number seems to be invalid. Please try re-entering it.");
				}

				break;
				
			case "deposit":
				try {
					// deposit method takes in integer, double and long parameters account number,  
					// amount to be deposited and session ID,these are entered as command line arguments. 
					// The method call returns the accounts current balance.
					double balance = bank.deposit(Integer.parseInt(args[3]), Double.parseDouble(args[4]), Long.parseLong(args[5]));
					System.out.println("Successfully deposited $" + args[4] + " current balance: $" + balance);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidSessionException e) {
					System.out.println("Your Session ID is invalid. Try logging in again.");
				} catch (NumberFormatException nfe) {
					System.out.println("Your Account number seems to be invalid. Please try re-entering it.");
				}
				break;				
				
			case "withdraw":
				try {
					// deposit method takes in integer, double and long parameters account number,  
					// amount to be deposited and session ID,these are entered as command line arguments. 
					// The method call returns the accounts current balance.
					double balance = bank.withdraw(Integer.parseInt(args[3]), Double.parseDouble(args[4]), Long.parseLong(args[5]));
					System.out.println("Successfully withdrawn $" + args[4] + " current balance: $" + balance);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidSessionException e) {
					System.out.println("Your Session ID is invalid. Try logging in again.");
				} catch (NumberFormatException nfe) {
					System.out.println("Your Account number seems to be invalid. Please try re-entering it.");
				} catch (InsufficientFundsException e) {
					System.out.println("Insufficient funds for this transaction");
				}
				break;

			case "statement":
				try {
					// getStatement method takes in integer, two Dates and long parameters account number,  
					// statement start and end dates, and session ID. These are entered as command line arguments. 
					// The method call returns an account statement of the transactions made with the period.
					SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
					Date startDate = dateFormatter.parse(args[4]);
					Date endDate = dateFormatter.parse(args[5]);
					String statement = bank.getStatement(Integer.parseInt(args[3]), startDate, endDate, Integer.parseInt(args[6]));
					System.out.println(statement);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidSessionException e) {
					System.out.println("Your Session ID is invalid. Try logging in again.");
				} catch (NumberFormatException nfe) {
					System.out.println("Your Account number seems to be invalid. Please try re-entering it.");
				} catch (ParseException e) {
					System.out.println("Your start or end date is incorrectly formatted. Ensure all dates are " +
							"formatted as 'dd/MM/yyyy'.");
				}
				break;

			default:
				System.out.println("Invalid operation");
			
		}
	}
	
	

}

