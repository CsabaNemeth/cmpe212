//package assignment3;
/**
 * An exception used by the {@link LaunchTube} class.
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see LaunchTube
 */
//A class that extends Exception and is thrown by 
//the launchtube upon encountering an incorrect user input for launch angle.
public class IllegalLaunchAngleException extends Exception{
	
	/**
	 * Constructor for the exception.
	 * @param message A message related to the error located.
	 */
	//Constructor that sets the argument message to be accessible by getMessage().
	public IllegalLaunchAngleException(String message) {
		super(message);
	} //End constructor.
} //End exception class.
