//package assignment3;
/**
 * A utility class to be used to store information about the external environment
 * in Non-Newtonian projectile simulation.
 * 
 * The class contains static attributes for air density and the acceleration due to gravity, and an instance variable holding the wind speed.
 * 
 * @author Csaba Nemeth
 * @version 1.0
 *
 */
/*The Environment class stores information about the natural/external environment that the
 * simulation is taking place in. 
 */
public class Environment {
	
	public static final double G = 9.807; 			// m/s^2
	public static final double DENSITY_AIR = 1.2; 	// kg/m^3
	private double WIND; 							// m/s
	
	/**
	 * Constructor for the Environment class.
	 * @param wind the wind speed in km/h.
	 * @throws IllegalWindVelocityException if wind is outside the range [-20,20].
	 */
	/*
	 * The constructor calls upon an helper function to set the wind attribute.
	 */
	public Environment(double wind) throws IllegalWindVelocityException {
		setWind(wind);
	} //End constructor.
	
	//Checks to see if the passed wind parameter is within the specified range.
	//Throws an exception if it isn't, otherwise, sets the WIND variable to the passed value.
	private void setWind(double wind) throws IllegalWindVelocityException {
		if (wind < -20 || wind > 20) {
			throw new IllegalWindVelocityException("Illegal Windspeed: " + wind);
		}
		
		WIND = (wind * 1000. / 3600.);
	}//End setWind method.
	
	/**
	 * @return The wind speed used to instantiate the Environment class in m/s.
	 */
	//Accessor to return the wind speed variable.
	public double getWind() {
		return WIND;
	} //End getWind method.

} //End Environment Class.
