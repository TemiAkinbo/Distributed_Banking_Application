package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;

import exceptions.InsufficientFundsException;
import exceptions.InvalidLoginException;
import exceptions.InvalidSessionException;
import interfaces.BankInterface;
import interfaces.StatementInterface;

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
					System.out.println("Your Session ID is invalid. Try logging in again.");
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
					System.out.println("Your Session ID is invalid. Try logging in again.");
				} catch (NumberFormatException nfe) {
					System.out.println("Your Account number seems to be invalid. Please try re-entering it.");
				}
				break;				
				
			case "withdraw":
				try {
					double balance = bank.withdraw(Integer.parseInt(args[1]), Double.parseDouble(args[2]), Long.parseLong(args[3]));
					System.out.println("Successfully withdrawn $" + args[2] + " current balance: $" + balance);
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
				StatementInterface s = null;
				try {
				Date from = new SimpleDateFormat("dd/MM/yyyy").parse(args[2]);
				Date to = new SimpleDateFormat("dd/MM/yyyy").parse(args[3]);
				long sessionID = Long.parseLong(args[4]);
				
					s = bank.getStatement(Integer.parseInt(args[1]), from, to, sessionID);
					System.out.println("Statement from " + args[2] + " to " + args[3]);
					System.out.println("\n\n");
					for(Object t : s.getTransactions()) {
						System.out.println(t.toString());						
					}
					System.out.println("End of Statement");
				} catch(RemoteException e) {
					e.printStackTrace();
				}catch(InvalidSessionException e) {
					System.out.println("Your Session ID is invalid. Try logging in again.");
				}catch(Exception e) {
					e.printStackTrace();
				}
			break;
				
			default: 
				System.out.println("Invalid operation");
			
		}
	}
	
	

}

