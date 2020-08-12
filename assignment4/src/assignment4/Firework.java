//package assignment4;

/**
 * 
 * An abstract class used to store the position and velocity of 
 * objects in a Roman-Candle simulation. Acts as the super class for 
 * the two main object types: {@link Emitter} and {@link Particle}.
 * 
 * @version 1.0
 * @author Csaba Nemeth
 */
public abstract class Firework {
	
	private double[] position; //Stores Position of Object as (x, y)
	private double[] velocity; //Stores Velocity of Object as (Vx, Vy);
	
	/**
	 * Constructor for the abstract Firework class.
	 * @param position Array of doubles with position (x, y)
	 * @param velocity Array of doubles with velocity (Vx, Vy)
	 */
	public Firework(double[] position, double[] velocity) {
		setPosition(position);
		setVelocity(velocity);
	} //End Constructor
	
	/**
	 * Mutator for the Firework object position.
	 * @param position Array of doubles with position (x, y)
	 */
	public void setPosition(double[] position) {
		this.position = position.clone();
	} //End method setPosition.
	
	/**
	 * Mutator for the Firework object velocity.
	 * @param velocity Array of doubles with velocity (Vx, Vy)
	 */
	public void setVelocity(double[] velocity) {
		this.velocity = velocity.clone();
	} //End method setVelocity.
	
	/**
	 * Accessor for the Firework object position.
	 * @return Array of doubles with position (x, y)
	 */
	public double[] getPosition() {
		return position.clone();
	} //End method getPosition.
	
	/**
	 * Accessor for the Fireowrk object velocity.
	 * @return Array of doubles with velocity (Vx, Vy)
	 */
	public double[] getVelocity() {
		return velocity.clone();
	} //End method getVelocity.
	
	@Override
	/**
	 * Abstract override of Java.lang.class.toString()
	 */
	public abstract String toString();
	
} //End class Firework.
