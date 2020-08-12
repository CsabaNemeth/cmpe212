package application;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Emits sparks to form an explosion occurring at the end of a Star's life.
 * @author Csaba Nemeth
 * @version 1.0
 */
public class ExplosionEmitter extends Emitter{
	
	private final double LIFETIME = 1;
	private final int NUM_LAUNCHED = 2000;
	private Color colour;

	/**
	 * 
	 * The constructor.
	 * @param initialXPos The initial x position.
	 * @param initialYPos The initial y position.
	 * @param initialXV The initial x velocity.
	 * @param initialYV The initial y velocity.
	 * @param exitVelocity The exit velocity of particles.
	 * @param firingAngle The firing angle, irrelevant.
	 * @param variation The variation in launch angle.
	 * @throws EmitterException
	 */
	public ExplosionEmitter(double initialXPos, double initialYPos, double initialXV, double initialYV,
			double exitVelocity, double firingAngle, double variation) throws EmitterException {
		super(initialXPos, initialYPos, initialXV, initialYV, exitVelocity, firingAngle, variation);
	}

	@Override
	//Java Doc comments inherited.
	public ArrayList<Spark> launch(double time) {
		double[] position = getPosition();
		ArrayList<Spark> sparks = new ArrayList<>(NUM_LAUNCHED);
		for (int i = 0; i < NUM_LAUNCHED; i++) {
			double sparkExitVelocity = getRandomExitVelocity(Math.abs(getRandomSigned()));
			double[] sparkVelocity = {getRandomSigned()*sparkExitVelocity*Math.sin(getRandomLaunchAngle()), 
					getRandomSigned()*sparkExitVelocity*Math.cos(getRandomLaunchAngle())};
			sparks.add(new Spark(time, position[0], position[1], sparkVelocity[0], sparkVelocity[1], getRandomLifetime(), colour));
		}
		return sparks;
	}
	/**
	 * A mutator for the colour of the StarSpark to be launched.
	 * @param colour The desired StarSpark colour.
	 */
	public void setColour (Color colour) {
		this.colour = colour;
	}
	
	//Returns a random lifetime to add realism.
	private double getRandomLifetime() {
		return LIFETIME + 2*LIFETIME * (Math.random() - 0.5);
	}
	
	//Gets a random signed value to add more randomness.
	private double getRandomSigned() {
		double num = 4*(Math.random() - 0.5);
		return num;
	}

} //End class ExplosionEmitter.
