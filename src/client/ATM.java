package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import exceptions.InvalidLoginException;
import exceptions.InvalidSessionException;
import interfaces.BankInterface;

public class ATM {

	public static void main (String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		BankInterface bank = null;
		String operation = args[0];

		try {
			Registry registry = LocateRegistry.getRegistry();
			bank = (BankInterface) registry.lookup("Bank");
		}
		catch (Exception e) {
			System.err.println("ATM client exception: ");
			e.printStackTrace();
		}
		

		switch(operation) {
			case "login": 
				try {
					long id = bank.login(args[1], args[2]);
					System.out.println("Login Successful new sessionID: " + id);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidLoginException e) {
					e.printStackTrace();
				}

				break;

			case "inquiry":
				try{
					double balance = bank.inquiry(Integer.parseInt(args[1]), Long.parseLong(args[2]));
					System.out.println("Balance: " + balance);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidSessionException e) {
					e.printStackTrace();
				} catch (NumberFormatException nfe) {
					System.out.println("Your Account number seems to be invalid. Please try re-entering it.");
				}

				break;
				
			case "deposit":
				try {
					double balance = bank.deposit(Integer.parseInt(args[1]), Double.parseDouble(args[2]), Long.parseLong(args[3]));
					System.out.println("Successfully deposited $" + args[2] + " current balance: $" + balance);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidSessionException e) {
					e.printStackTrace();
				} catch (NumberFormatException nfe) {
					System.out.println("Your Account number seems to be invalid. Please try re-entering it.");
				}
				break;				
				

			default: 
				System.out.println("Invalid operation");
			
		}
	}
	
	

}

