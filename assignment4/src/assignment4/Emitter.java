//package assignment4;

import java.util.ArrayList;

/**
 * 
 * Abstract class containing information on the launch angles and launch velocities of emitters,
 * as well as random generators to add realism to the simulation. Emitters return launch {@link Particle}s 
 * as an ArrayList.
 * 
 * @version 1.0
 * @author Csaba Nemeth
 * @see Firework
 */
public abstract class Emitter extends Firework{
	
	private double launchAngle;
	private double launchAngleVariation;
	private double exitVelocity;
	
	/**
	 * 
	 * Constructor for the Emitter class.
	 * 
	 * @param position The inital position of the Emitter.
	 * @param velocity The inital velocity of the Emitter.
	 * @param exitVelocity The particle exit velocity from the Emitter.
	 * @param launchAngle The launch angle from the vertical in degrees.
	 * @param launchAngleVariation The maximum variation in the launch angle.
	 * @throws EmitterException
	 * @see Firework
	 */
	public Emitter(double[] position, double[] velocity, double exitVelocity, double launchAngle, double launchAngleVariation) throws EmitterException{
		super(position, velocity);
		if (launchAngle < -15 || launchAngle > 15) {
			throw new EmitterException("Firing Angle Outside of Specified Range: " + launchAngle);
		}
		else {
			this.launchAngle = launchAngle * Math.PI/180.0;
			this.launchAngleVariation = launchAngleVariation * Math.PI/180.0;
			setExitVelocity(exitVelocity);
		}
	} //End constructor.

	/**
	 * Mutator for the exit velocity of the Emitter.
	 * @param exitVelocity The exit velocity in m/s.
	 */
	public void setExitVelocity(double exitVelocity) {
		this.exitVelocity = exitVelocity;
	} //End method setExitVelocity.
	
	/**
	 * An accessor for the exit velocity of the Emitter.
	 * @return The exit velocity in m/s.
	 */
	public double getExitVelocity() {
		return exitVelocity;
	} //End method getExitVelocity.
	
	/**
	 * An accessor for the launch angle of the Emitter.
	 * @return The launch angle in radians.
	 */
	public double getLaunchAngle() {
		return launchAngle;
	} //End getLaunchAngle.
	
	/**
	 * Method to return a random launch angle within the internal variation near the inital launch angle.
	 * @return A random launch angle in radians.
	 */
	public double getRandomLaunchAngle() {
		return launchAngle + launchAngleVariation * 2 * (Math.random() - 0.5);
	} // end getRandomLaunchAngle
	
	/**
	 * Method toreturn a random exit velocity within the internal variation near the inital exit velocity.
	 * @return A random exit velocity.
	 */
	public double getRandomExitVelocity() {
		return exitVelocity - 0.1 * exitVelocity * (Math.random() - 0.5);
	} // end getRandomExitVelocity
	
	/**
	 * A static utility method to return an integer within a variation.
	 * @param number The central value.
	 * @param variation The variation on the central value.
	 * @return An integer within the variation.
	 */
	public static int getRandomNumber(int number, int variation) {
		return number + (int)(variation*2*(Math.random() - 0.5));
	} //End method getRandomNumber.
	
	/**
	 * 
	 * An ArrayList containing instances of the particle type launched by the emitter.
	 * 
	 * @param time The simulation time in seconds.
	 * @return An ArrayList containing classes extending Particle.
	 * @see Particle
	 */
	public abstract ArrayList<? extends Particle> launch(double time);
	
} //End class Emitter.
