package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import exceptions.InvalidLoginException;
import exceptions.InvalidSessionException;
import interfaces.BankInterface;

public class ATM {


	private static long session_id;

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
					session_id = id;
					System.out.println("Login Successful new sessionID: " + id);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InvalidLoginException e) {
					e.printStackTrace();
				}

				break;

			case "inquiry":
				try{
					double balance = bank.inquiry(Integer.parseInt(args[1]), session_id);
					System.out.println("SessionID: " + session_id);
					System.out.println("Balance: " + balance);
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

