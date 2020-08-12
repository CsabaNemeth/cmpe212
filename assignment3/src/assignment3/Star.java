//package assignment3;

/**
 * 
 * A class used to simulate an instance of a Roman Candle firework.
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see RungeKuttaODE
 *
 */
//The Star class holds internal information regarding a simulation of a roman candle firework.
//This class consists mainly of helper function used to find the derivatives of velocity with
//respect to time. These are needed so that the Star can update its own position when called upon
//to do so.
public class Star implements ODESystem{

	private static final double BURN_RATE = 0.0030; 	// kg/second
	private static final double DENSITY_STAR = 1900; 	// kg/m^3
	private static final double STARTING_MASS = 0.008;	// kg
	private static final double DRAG_COEFF = 0.4;
	
	private double xPosition;
	private double yPosition;
	private double vX;
	private double vY;
	
	private double currentMass;
	private Environment currentEnvironment;
	
	/**
	 * 
	 * Constructor for the {@link Star} class. 
	 * 
	 * @param xInitial The initial x position of the Star.
	 * @param yInitial The initial y position of the Star.
	 * @param vxInitial The initial x velocity of the Star.
	 * @param vyInitial The initial y velocity of the Star.
	 * @param currentEnvironment An instance of the Environment object.
	 */
	//Constructor calls upon three private methods to set the private attributes.
	public Star(double xInitial, double yInitial, double vxInitial, double vyInitial, Environment currentEnvironment) {
		setEnvironment(currentEnvironment);
		setPosition(xInitial, yInitial);
		setVelocity(vxInitial, vyInitial);
		currentMass = STARTING_MASS;
		
	}
	
	//Sets the positions attributes. Not necessary just used for organizational purposes.
	private void setPosition(double x, double y) {
		xPosition = x;
		yPosition = y;
	}
	
	//Sets the velocity attributes. Not necessary just used for organizational purposes.
	private void setVelocity(double xVelocity, double yVelocity) {
		vX = xVelocity;
		vY = yVelocity;
	}
	
	//Sets the environment attribute. Not necessary just used for organization purpose.
	private void setEnvironment(Environment currEnvironment) {
		currentEnvironment = currEnvironment;
	}
	
	/**
	 * @return The time of flight of the star instance.
	 */
	//Calculates the time of flight of the star.
	public static double getTimeOfFlight() {
		return STARTING_MASS / BURN_RATE;
	}
	
	/**
	 * @return An array of doubles containing the current (x,y) position.
	 */
	//Allows external access to the current position of the star (private attributes).
	public double[] getCurrentPosition() {
		return new double[] {xPosition, yPosition};
	}
	
	/**
	 * 
	 * If called, the next position of the Star is found using numerical methods. The current 
	 * position of the star is updated with these values.
	 * 
	 * @param time The current time.
	 * @param dt Specifies that the updated position should be true for time + dt.
	 */
	//If called upon externally, the instance of the star will call the RungeKutta ODE to 
	//update its position.
	public void updatePosition(double time, double dt) {
		double[] nextV = RungeKuttaODE.solve(this, time, dt);
		setPosition(xPosition + nextV[0]*dt, yPosition + nextV[1]*dt);
		setVelocity(nextV[0], nextV[1]);
		updateMass(dt);
	}
	
	
	//ODESystem Implementation Starts Here
	
	/**
	 * A method for the {@link ODESystem} interface.
	 * 
	 * @return The system size 2.
	 */
	//Returns the system size. For two dimensional plane this is 2.
	public int getSystemSize() {
		return 2;
	}
	
	/**
	 * A method for the {@link ODESystem} interface.
	 * 
	 * @return An array of doubles specifying the current velocity.
	 */
	//Allows external access to the private attribute velocity.
	public double[] getCurrentValues() {
		return new double[] {vX, vY};
	}
	
	/**
	 * A method for the {@link ODESystem} interface.
	 * 
	 * @return An array of doubles with solutions to the function for (x,y).
	 */
	//Calculates the derivative of velocity with respect in time for the 2D system and returns as an array fo doubles.
	public double[] getFunction(double time, double[] values) {
		return new double[] {dVx_dt(findRelativeVx(values[0]), values[1]), dVy_dt(findRelativeVx(values[0]), values[1])};
	}
	
	
	//Helper Functions for ODESystem getFunction Implementation.
	//Returns the derivative of velocity.
	private double dVx_dt(double vxa, double vy) {
		return (-findDragForce(vxa, vy)*vxa/(currentMass * findMagnitude(vxa, vy)));
	}
	
	//Returns the derivative of velocity.
	private double dVy_dt(double vxa, double vy) {
		return (- Environment.G - findDragForce(vxa, vy) * vy /(currentMass * findMagnitude(vxa, vy)));
	}
	
	//Returns relative windspeed.
	private double findRelativeVx(double vx) {
		return (vx - currentEnvironment.getWind());
	}
	
	//Finds the magnitude for two values.
	private double findMagnitude(double a, double b) {
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b,  2));
	}
	
	//Finds cross section of star as a function of mass and density.
	private double findCrossSection() {
		double volume = currentMass/Star.DENSITY_STAR;
		double r = Math.cbrt(volume*3/4/Math.PI);
		double A = (Math.PI) * (Math.pow(r, 2));
		return A;
	}
	
	//Finds drag force relative to wind speed.
	private double findDragForce(double vxa, double vy) {
		double v = findMagnitude(vxa, vy);
		double A = findCrossSection();
		double FD = (Star.DRAG_COEFF * Math.pow(v, 2) * A * Environment.DENSITY_AIR)/2;
		return FD;
	}
	
	//Updates the mass at each timestep.
	private void updateMass(double dt) {
		currentMass = (currentMass - (dt * Star.BURN_RATE));
	}

} //End Star Class.
