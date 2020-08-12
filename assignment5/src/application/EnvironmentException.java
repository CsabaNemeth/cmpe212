package application;

/**
 * An exception used by the {@link Environment} class.
 * @author Csaba Nemeth
 * @version 1.0
 */
public class EnvironmentException extends Exception {

	/**
	 * Accepts a specific message about the problem.
	 * @param message
	 */
	public EnvironmentException (String message ) {
		super(message);
	} // end constructor
	
} // end EnvironmentException class