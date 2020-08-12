//package assignment4;

import java.util.ArrayList;

/**
 * 
 * A class used to generate sparks as the delay charge burns before a {@link Star} launch.
 * @version 1.0
 * @author Csaba Nemeth
 * @see Emitter
 *
 */
public class DelaySparkEmitter extends Emitter{

	public static final double DELAY_SPARK_LIFE = 0.6;
	public static final int SPARKS = 5;
	public static final int VARIATION = 1;
	private static String sparkColour = "orange";

	/**
	 * 
	 * Constructor for the DelaySparkEmitter class.
	 * 
	 * @param xPos The inital x-position.
	 * @param yPos The inital y-position.
	 * @param Vx The x component of the initial velocity.
	 * @param Vy The y component of the initial velocity.
	 * @param exitVelocity The exit velocity of particles from the emitter.
	 * @param launchAngle The launch from the vertical in degrees.
	 * @param launchAngleVariation The maximum variation on the launch angle.
	 * @throws EmitterException
	 * @see Emitter
	 * @see Spark
	 */
	public DelaySparkEmitter(double xPos, double yPos, double Vx, double Vy, double exitVelocity, double launchAngle, double launchAngleVariation) throws EmitterException {
		super(new double[] {xPos, yPos}, new double[] {Vx, Vy}, exitVelocity, launchAngle, launchAngleVariation);
	} //End constructor.

	// Javadoc comment inherited from Emitter.
	public ArrayList<Spark> launch(double time) {
		ArrayList<Spark> delaySparks = new ArrayList<Spark>();
		for (int i = 0; i < Emitter.getRandomNumber(SPARKS, VARIATION); i++) {
			double sparkAngle = getRandomLaunchAngle();
			double sparkExitVelocity = getRandomExitVelocity();
			double[] sparkVelocity = {sparkExitVelocity*Math.sin(sparkAngle), sparkExitVelocity*Math.cos(sparkAngle)};
			delaySparks.add(new Spark(getPosition(), sparkVelocity.clone(), time, DELAY_SPARK_LIFE, sparkColour));
		}
		return delaySparks;
	} //End method launch.

	@Override
	//UNUSED but needed for extension of Emitter class.
	public String toString() {
		return "DelaySparkEmitter: Emits Orange Sparks.";
	} //End method toString.

} //End class DelaySparkEmitter.
