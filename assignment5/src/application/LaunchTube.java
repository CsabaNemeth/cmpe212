package application;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Emitter for Roman candle stars.
 * 
 * Version 2.0 by Csaba Nemeth:
 * 	- Added javaFX Color class implementation.
 * 	- Added a mutator for the colour to allow for changes during the simulation.
 * @author Alan McLeod
 * @author Csaba Nemeth
 * @version 2.0
 */
public class LaunchTube extends Emitter {

	// Keeps track of the number of stars launched so the colours can change.
	private int numLaunched = 0;
	private Color colour;
	
	/**
	 * The LaunchTube constructor.
	 * @param initialXPos The initial X position of the emitter.
	 * @param initialYPos The initial Y position of the emitter.
	 * @param initialXV The initial X velocity component of the emitter.
	 * @param initialYV The initial Y velocity component of the emitter.
	 * @param exitVelocity The launch velocity of the sparks from the emitter.
	 * @param firingAngle The launch angle of the emitter, from the vertical in degrees.
	 * @param variation The random variation range for the launch angle in degrees.
	 * @throws EmitterException If the two angles are not legal.  The firing angle must be between -15 and 15
	 * degrees and the variation must lie between 0 and 10 degrees.
	 */
	public LaunchTube(double initialXPos, double initialYPos, double initialXV, double initialYV,
			double exitVelocity, double firingAngle, double variation) throws EmitterException {
		super(initialXPos, initialYPos, initialXV, initialYV, exitVelocity, firingAngle, variation);
		if (firingAngle < -15 || firingAngle > 15)
			throw new EmitterException("Launch angle out of range.");
		if (variation < 0 || variation > 10)
			throw new EmitterException("Variation angle out of range");
	}
	
	/**
	 * Creates and "launches" a star at the given time in seconds.
	 * @param time The absolute time in seconds.
	 * @return An instance of a Star object in an ArrayList, which will contain its initial position,
	 * initial velocity components and the desired colour.
	 */
	public ArrayList<Star> launch(double time) {
		double angle = getRandomLaunchAngle();
		double[] position = getPosition();
		double[] velocity = getVelocity();
		double vXInitial = velocity[0] + getExitVelocity() * Math.sin(angle);
		double vYInitial = velocity[1] + getExitVelocity() * Math.cos(angle);
		ArrayList<Star> star = new ArrayList<>();
		if (numLaunched != 8)
			star.add(new Star(time, position[0], position[1], vXInitial, vYInitial, colour));
		numLaunched++;
		return star;
	} // end launch

	/**
	 * Sets the launch-tube colour to the desired value.
	 * @param colour The desired javaFX Color object.
	 */
	public void setColour(Color colour) {
		this.colour = colour;
	}
	
} // end LaunchTube
