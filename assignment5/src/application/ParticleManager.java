package application;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * <pre>
 * This class manages the simulation.  It draws the Roman candle and launches 8 stars of various colours.
 * The class also manages all the other particle effects: the sparks emitted by the star, the 
 * launch sparks and the delay charge sparks.
 * 
 * Version 2.0 Changes by Csaba Nemeth:
 *   - Colour implementation changed from String to use javaFX Color class to allow for drawing. 
 *   	This includes an additional parameter in the start method to allow user choice of colour.
 *   - Changed launchSparkEmitter to launch at 10 m/s from 20 m/s. This adds realism with the 
 *   	added randomness implementation found in {@link LaunchSparkEmitter}.
 *   - Added boolean attributes that are true for one update iteration when a star is launched or removed from list.
 *   	These boolean attributes come with added accessor methods. These are needed to implement
 *   	synchronized sound with the javaFX simulation.
 *   - Added mutators for the Environment env wind and LaunchTube tube angle to allow for live changes throughout
 *   	the simulation.
 *   - Added the usage of an ExplosionEmitter class to create particles when a star has finished its flight.
 *   
 * @author Alan McLeod
 * @author Csaba Nemeth
 * @version 2.0
 */
public class ParticleManager {

	private double deltaTime;		// seconds
	// Stores the time of the last star launch
	private double lastTime;		// seconds
	private Environment env;
	private LaunchTube tube;
	private StarSparkEmitter starSparkEmit;
	private LaunchSparkEmitter launchSparkEmit;
	private DelaySparkEmitter delaySparkEmit;
	// This ArrayList will hold all the generated particles.
	private ArrayList<Particle> fireworks = new ArrayList<>();
	private int numStars = 8;
	private int countStars = 0;
	private double starLaunchTime;
	private double maxExitVelocity;
	
	//Attributes added for version 2.0
	private Color colour = Color.RED;
	private boolean starJustLaunched = false;
	private boolean starJustExploded = false;
	private ExplosionEmitter expEmitter;
	
	/**
	 * The ParticleManager constructor
	 * @param windVelocity The wind velocity in m/sec.
	 * @param launchAngle The launch angle of the Roman candle in degrees off the vertical.
	 * @throws EnvironmentException If the wind velocity is not between -20 and 20 m/sec.
	 * @throws EmitterException If the launch angle is not between -15 and 15 degrees.
	 */
	public ParticleManager(double windVelocity, double launchAngle) throws EnvironmentException, EmitterException {
		env = new Environment(windVelocity);
		double la = Math.PI * launchAngle / 180.0;	// radians
		// Position the star emitter at the end of the roman candle.  Use a launch velocity of 22 m/sec
		// and add a 2 degree random variation to the launch angle.
		maxExitVelocity = 22;
		//Version 2.0 adds a 2 coefficient to align the tube with the launch-tube sprite.
		tube = new LaunchTube(2*Math.sin(la), 2*Math.cos(la), 0, 0, maxExitVelocity, launchAngle, 2);
		lastTime = 0;
	}
	
	/**
	 * Launches a single star at the supplied absolute time and adds one set of launch sparks.
	 * @param time The absolute time in seconds.  The first star will be launched at time=0.
	 */
	public void start(double time, Color colour) {
		// Add some variation to the star's exit velocity
		tube.setExitVelocity(maxExitVelocity - 2 * Math.random());
		this.colour = colour;
		tube.setColour(colour);
		// Launch the star
		ArrayList<Star> starSet = tube.launch(time);
		if (starSet.size() == 0) 
			return;
		Star singleStar = starSet.get(0);
		starLaunchTime = time;
		// Add the star to the particles collection.
		fireworks.add(singleStar);
		// Create the spark emitters using the initial position and velocity of the star.
		double[] position = singleStar.getPosition();
		double[] velocity = singleStar.getVelocity();
		double launchAngle = tube.getLaunchAngle();
		try {
			// Star sparks of the same colour as the star will be launched at 3 m/sec in all directions.
			starSparkEmit = new StarSparkEmitter(position[0], position[1], velocity[0], velocity[1], 3, 0, 180);
			starSparkEmit.setColour(singleStar.getColour());
			// Launch sparks will be launched at 10 m/sec (V2.0) within 3 degrees of the star's launch angle.
			launchSparkEmit = new LaunchSparkEmitter(position[0], position[1], 0, 0, 10, launchAngle, 15);
			// Delay charge sparks will be sprayed out at 2.2 m/sec.
			delaySparkEmit = new DelaySparkEmitter(position[0], position[1], 0, 0, 2.2, launchAngle, 180);		
		} catch (EmitterException e) {
			// Not likely to get here unless the angles are not legal.
			System.out.println(e.getMessage());
			return;
		}
		// Add launch sparks to "push" the star out.
		fireworks.addAll(launchSparkEmit.launch(time));		
	} // end start method
	
	/**
	 * This method updates the simulation.
	 * @param time The absolute time in seconds. The simulation was started at time = 0;
	 * @throws EmitterException 
	 */
	private void update(double time){
		deltaTime = time - lastTime;
		lastTime = time;
		int index = 0;
		
		//Added for version 2.0 to allow for sound synchronization.
		if (starJustExploded) {
			starJustExploded = false;
		}
		if (starJustLaunched) {
			starJustLaunched = false;
		}
		
		Particle firework;
		// Clean out dead fireworks
		do {
			firework = fireworks.get(index);
			if (time - firework.getCreationTime() >= firework.getLifetime()) {
				// Get rid of the star spark emitter if the star is gone.
				if (firework instanceof Star) {
					starSparkEmit = null;
					
					//Implementation of an ExplosionEmitter to create an explosion when the 
					//star has finished is path.
					try {
						expEmitter = new ExplosionEmitter(
								firework.getPosition()[0], firework.getPosition()[1], 
								firework.getVelocity()[0], firework.getVelocity()[1],
								200, 10, 180);
						expEmitter.setColour(firework.getColour());
						starJustExploded = true;
					} catch (EmitterException e) {
						System.out.println(e.getMessage());
					} //End try catch block.
				}
				fireworks.remove(index);

			} else
				index++;
		} while (fireworks.size() > 0 && index < fireworks.size());
		// Update positions
		
		for (Particle fire : fireworks) {
			fire.updatePosition(time, deltaTime, env);
			// Move the star spark emitter adn explosion emitter along with the star.
			if (fire instanceof Star) {
				starSparkEmit.setPosition(fire.getPosition());
			}
		}
		// Keep adding delay charge sparks until 3.5 seconds are up.
		if (time - starLaunchTime < 3.5) {
			fireworks.addAll(delaySparkEmit.launch(time));
		}
		// Add star sparks as long as the starSpark emitter exists
		if (starSparkEmit != null)
			fireworks.addAll(starSparkEmit.launch(time));
		
		//Implementation added for version 2.0, launches particles from the star death location.
		if (expEmitter != null) {
			fireworks.addAll(expEmitter.launch(time));
			expEmitter = null;
		}
		
		// If all the particles associated with the previous star are all gone, then prevent the particle 
		// collection from becoming empty by adding delay charge sparks, and then start the launch
		// of another star.
		if (fireworks.size() == 0) {
			if (countStars < numStars - 1) {
				fireworks.addAll(delaySparkEmit.launch(time));
				countStars++;
				// Launch another star
				starJustLaunched = true;
				start(time, colour);
			} else {
				// Stop the simulation after 8 stars have been launched.  The collection will be empty.
				return;
			}
		}		
	} // end update
	
	/**
	 * An accessor for the collection of particles.
	 * For this to work properly, each particle type should have have its own clone method.  (But they
	 * do not, which is OK...)
	 * @param time The absolute time in seconds. The simulation started at time = 0.
	 * @return The collection of particles in an ArrayList.
	 */
	public ArrayList<Particle> getFireworks(double time) {
		update(time);
		ArrayList<Particle> copy = new ArrayList<>(fireworks.size());
		for (Particle firework : fireworks)
			copy.add(firework);
		return copy;
	}
	
	/**
	 * Mutates the wind speed of the internal environment class.
	 * @param wind The desired wind speed in km/h/
	 */
	public void setEnvironmentWind(double wind) {
		env.setWindVelocity(wind);
	}
	
	/**
	 * Mutates the angle of the internal launch-tube and repositions it to match the sprite in {@link Controller}.
	 * @param angle The desired angle in degrees.
	 */
	public void setLaunchTubeAngle(double angle) {
		tube.setLaunchAngle(angle);
		double la = Math.PI * angle / 180.0;
		double[] position = new double[] {2*Math.sin(la), 2*Math.cos(la)};
		tube.setPosition(position);
		delaySparkEmit.setPosition(position);
	}
	
	/**
	 * @return If a star has been launched in the current iteration.
	 */
	public boolean starJustLaunched() {
		return starJustLaunched;
	}
	
	/**
	 * @return If a star has just exploded in the current iteration.
	 */
	public boolean starJustExploded() {
		return starJustExploded;
	}
	
} // end ParticleManager class