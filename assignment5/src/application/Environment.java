package application;

/**
 * <pre>
 * Contains all the information about the environment in which the Roman Candle is being fired.
 * This consists of the wind velocity and the air density and gravity constants.
 * 
 * Version 2.0 adds a wind-velocity mutator.
 * @author Alan McLeod
 * @author Csaba Nemeth
 * @version 2.0
 */
public class Environment {

	private double windVelocity;					// m/sec
	/**
	 * Air density in kg per cubic meter at close to sea level.
	 */
	public final static double DENSITY_AIR = 1.2;	// kg/m*m*m
	/**
	 * The acceleration due to Earth's gravity in metres per second squared.
	 */
	public final static double G = 9.807;  			// m/s*s
	
	/**
	 * The constructor for the Environment object.
	 * @param wind The wind velocity in km/hour.
	 * @throws EnvironmentException If the magnitude of the wind velocity is above 20 km/hour.
	 */
	public Environment(double wind) throws EnvironmentException {
		if (wind < -20 || wind > 20)
			throw new EnvironmentException("Wind too high: " + wind);
		windVelocity = wind / 3.6;
	}
	
	/**
	 * The wind velocity accessor.
	 * @return The wind velocity in m/sec.
	 */
	public double getWindVelocity() {
		return windVelocity;
	}
	
	/**
	 * @param wind The desired wind speed in km/h.
	 */
	public void setWindVelocity(double wind) {
		this.windVelocity = wind/3.6;
	}
	
} // end Environment