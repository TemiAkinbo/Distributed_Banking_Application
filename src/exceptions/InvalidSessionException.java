package exceptions;

public class InvalidSessionException extends Exception {
	public InvalidSessionException() {
		super("Your session has timed out, Please Log In again");
	}
}
