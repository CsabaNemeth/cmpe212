//package assignment4;

import java.util.ArrayList;

/**
 * 
 * A class used to launch a {@link Star} object. 
 * @version 2.1
 * @author Csaba Nemeth
 * @see Emitter
 */
public class LaunchTube extends Emitter{
	
	private String[] colours = {"red", "orange", "yellow", "green", "blue", "purple"};;

	/**
	 * 
	 * Constructor for the LaunchTube class.
	 * @param xPos The inital x-position.
	 * @param yPos The inital y-position.
	 * @param Vx The x component of the initial velocity.
	 * @param Vy The y component of the initial velocity.
	 * @param exitVelocity The exit velocity of particles from the emitter.
	 * @param launchAngle The launch from the vertical in degrees.
	 * @param launchAngleVariation The maximum variation on the launch angle.
	 * @throws EmitterException
	 * @see Emitter
	 * @see Star
	 */
	public LaunchTube(double xPos, double yPos, double Vx, double Vy, double exitVelocity, double launchAngle, double launchAngleVariation) throws EmitterException {
		super(new double[] {xPos, yPos}, new double[] {Vx, Vy}, exitVelocity, launchAngle, launchAngleVariation);
	} //End cosntructor.

	// Javadoc comments inherited from Emitter.
	public ArrayList<Star> launch(double time) {
		ArrayList<Star> star = new ArrayList<Star>();
		double starAngle = getRandomLaunchAngle();
		double[] starVelocity = {getExitVelocity()*Math.sin(starAngle), getExitVelocity()*Math.cos(starAngle)};
		star.add(new Star(getPosition(), starVelocity.clone(), time, getRandomColour()));
		return star;
	} //End method launch.
	
	/**
	 * Randomly selects a colour for the emitted particle.
	 * @return A string representation of a randomly selected colour.
	 */
	private String getRandomColour() {
		int index = (int)(Math.random()*6);
		return colours[index];
	} //End method getRandomColour.

	@Override
	//UNUSED but needed for extension of Emitter class.
	public String toString() {
		return "LaunchTube";
	} //End method toString.

}
