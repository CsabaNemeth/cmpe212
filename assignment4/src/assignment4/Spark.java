//package assignment4;

/**
 * This class describes any {@link Particle} that is small enough to be considered a spark in the simulation.
 * @verion 1.0
 * @author Csaba Nemeth
 *
 */
public class Spark extends Particle{
	
	public static final double BASE_RADIUS = 0.0015;
	public static final double BASE_MASS = 0.000002;
	public static final double RADIUS_VARIATION = 0.0001;
	public static final double MASS_VARIATION = 0.0000001;
	
	/**
	 * 
	 * Overloaded constructor for the Spark class.
	 * 
	 * @param position The initial position.
	 * @param velocity The initial velocity.
	 * @param creationTime The simulation time of creation.
	 * @param lifeTime The spark lifetime.
	 * @param colour The spark colour.
	 * @see Particle
	 */
	public Spark(double[] position, double[] velocity, double creationTime, double lifeTime, String colour) {
		super(position, velocity, creationTime, lifeTime, Spark.getRandomRadius(BASE_RADIUS, RADIUS_VARIATION), Spark.getRandomMass(BASE_MASS, MASS_VARIATION), colour);		
	} //End constructor
	
	/**
	 * 
	 * Overloaded constructor for the Spark class.
	 * 
	 * @param position The inital position.
	 * @param velocity The initial velocity.
	 * @param creationTime The simulation time of creation.
	 * @param lifeTime The spark lifetime.
	 * @param radius The initial spark radius.
	 * @param colour The spark colour.
	 * @see Particle
	 */
	public Spark(double[] position, double[] velocity, double creationTime, double lifeTime, double radius, String colour) {
		super(position, velocity, creationTime, lifeTime, radius, Spark.getRandomMass(BASE_MASS, MASS_VARIATION), colour);
	} //End constructor.

	// Javadoc comments inherited from Particle.
	public double getMass(double time) {
		return getStartingMass();
	} //End method getMass.
	
	// Javadoc comments inherited from Particle.
	public double getRadius(double time) {
		return getStartingRadius();
	} //End method getRadius.
	
	/**
	 * Returns a random radius within the specified variation parameter.
	 * @param radius The central value for the radius.
	 * @param variation The maximum variation.
	 * @return A random radius.
	 */
	public static double getRandomRadius(double radius, double variation) {
		return radius + variation * 2 * (Math.random() - 0.5);
	} //End method getRandomRadius.
	
	/**
	 * Returns a random mass within the specified variation parameter.
	 * @param mass The central value for the mass.
	 * @param variation The maximum variation.
	 * @return A random mass.
	 */
	private static double getRandomMass(double mass, double variation) {
		return mass + variation * 2 * (Math.random() - 0.5);
	} //End method getRandomMass.
		
	@Override 
	/**
	 * Extends override of Java.lang.class.toString() to type Spark.
	 */
	public String toString() {
		return "Spark" + super.toString();
	} //End method toString.
	
} //End class Spark.
