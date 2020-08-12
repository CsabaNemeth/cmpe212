//package assignment4;

import java.util.ArrayList;

/**
 * A class used to generate sparks as an {@link Star} object launches.
 * @version 1.0
 * @author Csaba Nemeth
 * @see Emitter
 *
 */
public class LaunchSparkEmitter extends Emitter{

	public static final double LAUNCH_SPARK_LIFE = 0.13;
	public static final int SPARKS = 20;
	public static final int VARIATION = 4;
	private static String sparkColour = "orange";

	/**
	 * 
	 * Constructor for the LaunchSparkEmitter class.
	 * @param xPos The inital x-position.
	 * @param yPos The inital y-position.
	 * @param Vx The x component of the initial velocity.
	 * @param Vy The y component of the initial velocity.
	 * @param exitVelocity The exit velocity of particles from the emitter.
	 * @param launchAngle The launch from the vertical in degrees.
	 * @param launchAngleVariation The maximum variation on the launch angle.
	 * @throws EmitterException
	 * @see Emitter
	 * @see LaunchSpark
	 */
	public LaunchSparkEmitter(double xPos, double yPos, double Vx, double Vy, double exitVelocity, double launchAngle, double launchAngleVariation) throws EmitterException{
		super(new double[] {xPos, yPos}, new double[] {Vx, Vy}, exitVelocity, launchAngle, launchAngleVariation);
	} //End constructor.

	// Javadoc Comments inherited from Emitter.
	public ArrayList<LaunchSpark> launch(double time) {
		ArrayList<LaunchSpark> launchSparks = new ArrayList<LaunchSpark>();
		for (int i = 0; i < Emitter.getRandomNumber(SPARKS, VARIATION); i++) {
			double sparkAngle = getRandomLaunchAngle();
			double sparkExitVelocity = getRandomExitVelocity();
			double[] sparkVelocity = {sparkExitVelocity*Math.sin(sparkAngle), sparkExitVelocity*Math.cos(sparkAngle)};
			launchSparks.add(new LaunchSpark(getPosition(), sparkVelocity.clone(), time, LAUNCH_SPARK_LIFE, sparkColour));
		}
		return launchSparks;
	} //End method launch.

	@Override
	//UNUSED but needed for extension of Emitter class.
	public String toString() {
		return "LaunchSparkEmitter: Emits orange sparks";
	} //End method toString

} //End class LaunchSparkEmitter.
