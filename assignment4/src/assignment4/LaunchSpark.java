//package assignment4;

/**
 * Describes sparks created at the launch of a {@link Star}.
 * @version 1.0
 * @author Csaba Nemeth
 * @see Spark
 *
 */
public class LaunchSpark extends Spark{
	
	public static final double BASE_RADIUS = 0.0005;
	public static final double RADIUS_VARIATION = 0.0001;

	/**
	 * 
	 * Constructor for the LaunchSpark class.
	 * 
	 * @param position The inital position of the LaunchSpark.
	 * @param velocity The inital velocity of the LaunchSpark.
	 * @param creationTime The simulation creation time.
	 * @param lifeTime The LaunchSpark lifetime.
	 * @param colour A string representation of the LaunchSpark colour.
	 * @see Spark
	 */
	public LaunchSpark(double[] position, double[] velocity, double creationTime, double lifeTime, String colour) {
		super(position, velocity, creationTime, lifeTime, Spark.getRandomRadius(BASE_RADIUS, RADIUS_VARIATION), colour);
	} //End constructor.
	
} //End class LauncSpark.
