//package assignment4;

/**
 * This class describes a star from a Roman Candle
 * @version 1.0
 * @author Csaba Nemeth
 * @see Particle
 *
 */
public class Star extends Particle{

	public static final double BURN_RATE = 0.0030;
	public static final double DENSITY_STAR = 1900;
	public static final double MASS_STAR = 0.008;
	public static final double LIFETIME = MASS_STAR / BURN_RATE;

	/**
	 * 
	 * Constructor for the Star class.
	 * 
	 * @param position The inital position of the Star.
	 * @param velocity The inital velocity of the Star.
	 * @param creationTime The simulation time of creation.
	 * @param colour A string representation of the Star colour.
	 * @see Particle
	 */
	public Star(double[] position, double[] velocity, double creationTime, String colour) {
		super(position, velocity, creationTime, LIFETIME, MASS_STAR, getInitialRadius(), colour);
	} //End constructor.
	
	// Javadoc comments inherited from Particle.
	public double getMass(double time) {
		return getStartingMass() - (time - getCreationTime()) * BURN_RATE;
	} // end getMass
	
	// Javadoc comments inherited from Particle.
	public double getRadius(double time) {
		double volume = getMass(time) / DENSITY_STAR;
		return Math.pow(3 * volume / (4 * Math.PI), 1.0 / 3.0);
	}
	
	//Static method to return the initial radius of the star.
	private static double getInitialRadius() {
		double volume = MASS_STAR / DENSITY_STAR;
		return Math.pow(3 * volume / (4 * Math.PI), 1.0 / 3.0);
	} //End method getInitialRadius.
	
	@Override
	/**
	 * Extends override of Java.lang.class.toString() to type Star.
	 */
	public String toString() {
		return "Star" + super.toString();
	} //End method toString.

}
