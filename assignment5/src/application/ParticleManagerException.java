package application;

/**
 * An exception used by the ParticleManager class.
 * @author Alan McLeod
 * @version 1.0
 */
public class ParticleManagerException extends Exception {

	/**
	 * Obtains a useful string message when thrown.
	 * @param message
	 */
	public ParticleManagerException (String message) {
		super(message);
	} // end constructor
	
} // end ParticleManagerException