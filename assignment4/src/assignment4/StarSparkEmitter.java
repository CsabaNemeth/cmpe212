//package assignment4;

import java.util.ArrayList;

/**
 * A class used to generate sparks as a {@link Star} object traverses its path.
 * @version 1.0
 * @author Csaba Nemeth
 * @see Emitter
 *
 */
public class StarSparkEmitter extends Emitter{
	
	public static final double STAR_SPARK_LIFE = 0.1;
	public static final int SPARKS = 20;
	public static final int VARIATION = 4;
	private String colour = "red"; //Default colour

	/**
	 * Constructor for the StarSparkEmitter class.
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
	public StarSparkEmitter(double xPos, double yPos, double Vx, double Vy, double exitVelocity, double launchAngle, double launchAngleVariation) throws EmitterException{
		super(new double[] {xPos, yPos}, new double[] {Vx, Vy}, exitVelocity, launchAngle, launchAngleVariation);
	} //End constructor.

	// Javadoc comments inherited from Emitter.
	public ArrayList<Spark> launch(double time) {
		ArrayList<Spark> starSparks = new ArrayList<Spark>();
		for (int i = 0; i < getRandomNumber(SPARKS, VARIATION); i++) {
			double sparkAngle = getRandomLaunchAngle();
			double sparkExitVelocity = getRandomExitVelocity();
			double[] sparkVelocity = {sparkExitVelocity*Math.sin(sparkAngle), sparkExitVelocity*Math.cos(sparkAngle)};
			starSparks.add(new Spark(getPosition(), sparkVelocity.clone(), time, STAR_SPARK_LIFE, colour));
		}
		return starSparks;
	} //End method launch.

	/**
	 * A mutator for the colour of the emitted sparks.
	 * @param colour A string representation of the colour.
	 */
	public void setColour(String colour) {
		this.colour = colour;
	} //End method setColour.
	
	@Override
	//UNUSED but needed for extension of Emitter class.
	public String toString() {
		return "StarkSparkEmitter: Emits" +colour+ "sparks";
	} //End method toString.

} //End class StarSparkEmitter.
