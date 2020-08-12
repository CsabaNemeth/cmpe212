package application;
import java.util.ArrayList;

/**
 * <pre>
 * The base class for Emitter objects.
 * 
 * Version 2.0 changes by Csaba Nemeth:
 * 	- Added mutator for the launchAngle attribute to allow for live updates during the simulation.
 * 	- Added a method to return a random variation on the exit velocity for added simulation realism.
 * @author Alan McLeod
 * @author Csaba Nemeth
 * @version 2.0
 */
public abstract class Emitter extends Firework {

	private double launchAngle = 0;				// radians
	private double launchAngleVariation = 0;	// radians
	private double exitVelocity;				// m/sec	
	
	/**
	 * The constructor for an Emitter object.
	 * @param initialXPos The initial X position of the emitter.
	 * @param initialYPos The initial Y position of the emitter.
	 * @param initialXV The initial X velocity component of the emitter.
	 * @param initialYV The initial Y velocity component of the emitter.
	 * @param exitVelocity The launch velocity of the sparks from the emitter.
	 * @param firingAngle The launch angle of the emitter, from the vertical in degrees.
	 * @param variation The random variation range for the launch angle in degrees.
	 * @throws EmitterException If the two angles are not legal. The firing angle must lie
	 * between -180 and 180 degrees, and the variation angle between 0 and 180 degrees, inclusive.
	 */
	public Emitter (double initialXPos, double initialYPos, double initialXV, double initialYV,
			double exitVelocity, double firingAngle, double variation) throws EmitterException {
		super(initialXPos, initialYPos, initialXV, initialYV);
		this.exitVelocity = exitVelocity;
		if (firingAngle < -180 || firingAngle > 180)
			throw new EmitterException("Firing angle out of range: " + firingAngle);
		else
			launchAngle = firingAngle * Math.PI / 180.0;
		if (variation < 0 || variation > 180)
			throw new EmitterException("Firing angle variation out of range: " + variation);
		else
			launchAngleVariation = variation * Math.PI / 180.0;
	} // end constructor
	
	/**
	 * An accessor that calculates and returns an angle randomly generated between (firing angle - variation)
	 * and (firing angle + variation) in radians.
	 * @return The launch angle in radians.
	 */
	public double getRandomLaunchAngle() {
		return launchAngle + launchAngleVariation * 2 * (Math.random() - 0.5);
	}
	
	/**
	 * Method toreturn a random exit velocity within the internal variation near the inital exit velocity.
	 * @return A random exit velocity.
	 */
	public double getRandomExitVelocity() {
		return exitVelocity - 0.2 * exitVelocity * (Math.random() - 0.5);
	} // end getRandomExitVelocity
	
	/**
	 * Returns a random exit velocity.
	 * @param variance The variance on the exit velocity.
	 * @return A random exit velocity near the original value.
	 */
	public double getRandomExitVelocity(double variance) {
		return exitVelocity - variance * exitVelocity * (Math.random() - 0.5);
	}
	
	/**
	 * An accessor for the exit (or launch) velocity of the emitter.
	 * @return The exit velocity in m/sec.
	 */
	public double getExitVelocity() { return exitVelocity; }
	
	/**
	 * A mutator for the exit (or launch) velocity.
	 * @param exitVelocity The desired exit velocity in m/sec.
	 */
	public void setExitVelocity(double exitVelocity) {
		this.exitVelocity = exitVelocity;
	}
	
	/**
	 * Mutates the internal firing angle.
	 * @param firingAngle The desired firing angle in degrees.
	 */
	public void setLaunchAngle(double firingAngle) {
		launchAngle = firingAngle * Math.PI / 180.0;
	}
	
	/**
	 * An accessor for the launch angle.
	 * @return The launch angle in degrees.
	 */
	public double getLaunchAngle() { 
		return launchAngle * 180.0 / Math.PI; 
	}
	
	/**
	 * Launches particles at the supplied time.
	 * @param time Time in seconds
	 * @return A collection of launched particles.  If the list is empty then the emitter has
	 * expired.
	 */
	public abstract ArrayList<? extends Particle> launch (double time);
	
} // end Emitter class
