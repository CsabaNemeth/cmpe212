//package assignment3;
/**
 * An exception used by the {@link PathManager} class.
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see PathManager
 */
//A class that extends Exception and is thrown by the pathManager
//upon encountering an illegal time interval.
public class IllegalTimeIntervalException extends Exception {
	
	/**
	 * 
	 * Constructor for the exception.
	 * @param message A message related to the error located.
	 */
	//Constructor that sets the argument message to be accessible by getMessage().
	public IllegalTimeIntervalException(String message) {
		super(message);
	} //End constructor.
} //End exception class.
