package application;
import java.util.ArrayList;
import javafx.scene.paint.Color;
/**
 * <pre>
 * Emitter for launch sparks.
 * 
 * Version 2.0 adds more particles launched and increased randomness.
 * @author Alan McLeod
 * @author Csaba Nemeth
 * @version 2.0
 */
public class LaunchSparkEmitter extends Emitter {

	private final double LIFETIME = 0.5;		// seconds
	private final int NUM_LAUNCHED = 200;		// how many to launch
	
	/**
	 * The LaunchSparkEmitter constructor.
	 * @param initialXPos The initial X position of the emitter.
	 * @param initialYPos The initial Y position of the emitter.
	 * @param initialXV The initial X velocity component of the emitter.
	 * @param initialYV The initial Y velocity component of the emitter.
	 * @param exitVelocity The launch velocity of the sparks from the emitter.
	 * @param firingAngle The launch angle of the emitter, from the vertical in degrees.
	 * @param variation The random variation range for the launch angle in degrees.
	 * @throws EmitterException If the two angles are not legal.
	 */
	public LaunchSparkEmitter(double initialXPos, double initialYPos, double initialXV, double initialYV,
			double exitVelocity, double firingAngle, double variation) throws EmitterException {
		super(initialXPos, initialYPos, initialXV, initialYV, exitVelocity, firingAngle, variation);
	}
	
	/**
	 * Launches (returns) LaunchSpark objects at the supplied time.
	 * @param time The absolute launch time in seconds.
	 * @return An ArrayList containing LaunchSpark objects.
	 */
	public ArrayList<LaunchSpark> launch(double time) {
		double[] position = getPosition();
		ArrayList<LaunchSpark> sparks = new ArrayList<>(NUM_LAUNCHED);
		for (int i = 0; i < NUM_LAUNCHED; i++) {
			double sparkExitVelocity = getRandomExitVelocity(2);
			double[] sparkVelocity = {sparkExitVelocity*Math.sin(getRandomLaunchAngle()), sparkExitVelocity*Math.cos(getRandomLaunchAngle())};
			sparks.add(new LaunchSpark(time, position[0], position[1], sparkVelocity[0], sparkVelocity[1], getRandomLifetime(), Color.ORANGE));
		}
		return sparks;
	}
	
	//Returns a randomized lifetime.
	private double getRandomLifetime() {
		return LIFETIME + 2*LIFETIME * (Math.random() - 0.5);
	}

} // end LaunchSparkEmitter
