package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import exceptions.InsufficientFundsException;
import exceptions.InvalidLoginException;
import exceptions.InvalidSessionException;
import interfaces.BankInterface;

public class ATM {

	public static void main (String[] args) {
		String host = args[0];
		String port = args[1];
		int portNumber = Integer.parseInt(port);

		System.setProperty("java.security.policy", "file:./allAccess.policy");

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		BankInterface bank = null;
		String operation = args[2];

		try {
			Registry registry = LocateRegistry.getRegistry(host, portNumber);
			bank = (BankInterface) registry.lookup("Bank");
		}
		catch (Exception e) {
			System.err.println("ATM client exception: ");
			e.printStackTrace();
		}
		

		switch(operation) {
			case "login": 
				try {
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

			default: 
				System.out.println("Invalid operation");
			
		}
	}
	
	

}

