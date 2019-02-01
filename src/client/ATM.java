package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import exceptions.InvalidLoginException;
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
						System.out.println("Login Succefull new sessionID: " + id);
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (InvalidLoginException e) {
						e.printStackTrace();
					}
					
				break;
				
			default: 
				System.out.println("Invalid operation");
			
		}
	}
	
	

}

