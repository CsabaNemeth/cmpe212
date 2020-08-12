//package assignment3;

/**
 * An exception used by the {@link Environment} class.
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see Environment
 */
/*
 * A class that extends Exception and is thrown by the Environment
 * object upon encountering an illegal wind velocity from user input.
 */
public class IllegalWindVelocityException extends Exception {

	/**
	 * Constructor for the exception.
	 * @param message A message related to the error located.
	 */
	//Constructor that sets the argument message to be accessible by getMessage().
	public IllegalWindVelocityException(String message) {
		super(message);
	} //End constructor.
} //End exception class.
