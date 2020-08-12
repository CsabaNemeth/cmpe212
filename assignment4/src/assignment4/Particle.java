//package assignment4;


/**
 * 
 * A class defining all launched objects in a firework simulation, storing all internal characteristics. 
 * Particle implements the {@link ODESystem} interface, to allow updates to the particle position.
 * 
 * @version 1.0
 * @author Csaba Nemeth
 * @see Emitter
 * @see ODESystem
 */
public abstract class Particle extends Firework implements ODESystem{
	
	//Private store 
	private double startingRadius;
	private double startingMass;
	private double wind;
	private double creationTime;
	private double lifeTime;
	private String colour;
	
	public static final int SYSTEM_SIZE = 2;
	public static final double DRAG_COEFF = 0.4; // Assume all particles are spheres

	/**
	 * 
	 * Constructor for the Particle class. 
	 * @param position The inital position of the particle as (x, y) in meters. 
	 * @param velocity The initial velocity of the particle as (Vx, Vy) in m/s.
	 * @param creationTime The simulation time that the particle was creation.
	 */
	public Particle(double[] position, double[] velocity, double creationTime, double lifeTime, double startingRadius, double startingMass, String colour) {
		super(position, velocity);
		this.lifeTime = lifeTime;
		this.creationTime = creationTime;
		this.startingRadius = startingRadius;
		this.startingMass = startingMass;
		this.colour = colour;
	} //End constructor.

	/*Abstract Methods*/
	/**
	 * Returns the current mass of the particle.
	 * @param time The current simulation time in seconds.
	 * @return The mass the particle.
	 */
	public abstract double getMass(double time);
	
	/**
	 * Returns the current radius of the particle.
	 * @param time The current simulation time in seconds.
	 * @return The radius of the particle.
	 */
	public abstract double getRadius(double time);
	
	/*Accessors*/
	
	/** @return The initial particle radius.*/
	public double getStartingRadius() {
		return startingRadius;
	} //End method getStartingRadius.
	
	/** @return The initial particle mass.*/
	public double getStartingMass() {
		return startingMass;
	}//End method getStartingMass.
	
	/** @return The lifetime of the particle.*/
	public double getLifetime() {
		return lifeTime;
	} //End method getLifetime.
	
	/** @return The simulation time when the particle was created.*/
	public double getCreationTime() {
		return creationTime;
	} //End method getCreationTime.
	
	/** @return A string representation of the particle colour.*/
	public String getColour() {
		return colour;
	} //End method getColour.
	
	// Utility Methods
	
	/**
	 * A mutator that updates the current position of the star.
	 * @param newValues An array containing the current velocity components for the star in m/sec.
	 * @param deltaTime The time interval in seconds.
	 * @param env An instance of the current Environment object is needed to supply the
	 * wind velocity, which is used to calculate the apparent velocity. This allows the wind
	 * velocity to change during a simulation.
	 */
	public void updatePosition(double time, double deltaTime, Environment env) {
		wind = env.getWindVelocity();
		double[] newValues = RungeKuttaSolver.getNextPoint(this, time, deltaTime);
		setVelocity(newValues);
		setPosition(new double[] {getPosition()[0] + getVelocity()[0] * deltaTime, getPosition()[1] + getVelocity()[1] * deltaTime});
	} // end updatePosition
	
	/**
	 * Concrete implementation of Java.lang.class.toString() for an
	 * object of type Particle.
	 */
	public String toString() {
		return ", " + colour + ", at (" + Math.round(getPosition()[0]*100)/100.0 + ", " + Math.round(getPosition()[1]*100)/100.0 + ")";
	}
	
	//ODE System Interface Implementation
	/**
	 * A method for the {@link ODESystem} interface.
	 * @return The system size 2.
	 */
	public int getSystemSize() {
		return SYSTEM_SIZE;
	} //End method getSystemSize.
	
	/**
	 * A method for the {@link ODESystem} interface.
	 * @return The current particle velocity;
	 */
	public double[] getCurrentValues() {
		return getVelocity();
	} //End method getCurrentValues.
	
	/**
	 * A method for the {@link ODESystem} interface that evaluates the given values
	 * at the specified time.
	 * @return An array of doubles with solutions to the differential equations.
	 */
	public double[] getFunction(double time, double[] values) {
		double[] functionVal = new double[SYSTEM_SIZE];
		double vX = values[0];
		double vY = values[1];
		functionVal[0] = xDE(time, vX, vY);
		functionVal[1] = yDE(time, vX, vY);
		return functionVal;
	} //End method getFunction.
	

	//Helpers for ODE Implementation
	
	// This method returns the value of the fx function, given the 
	// time in seconds and the two velocity components in m/sec.
	// The meaning of fx is described in the assignment statement.
	private double xDE(double time, double vx, double vy) {
		// Use apparent x velocity to calculate drag.
		double vxa = vx - wind;
		double velocityMag = getVelocityMag(vxa, vy);
		double mass = getMass(time);
		double dragForce = getDragForce(time, vxa, vy);
		return -dragForce * vxa / (mass * velocityMag);
	} // end xDE
	
	// This method returns the value of the fy function, given the 
	// time in seconds and the two velocity components in m/sec.
	// The meaning of fy is described in the assignment statement.
	private double yDE(double time, double vx, double vy) {
		// Use apparent x velocity to calculate drag.
		double vxa = vx - wind;
		double velocityMag = getVelocityMag(vxa, vy);
		double mass = getMass(time);
		double dragForce = getDragForce(time, vxa, vy);
		return -Environment.G - dragForce * vy / (mass * velocityMag);
	} // end yDE
	
	// This method finds the drag force acting on the particle
	// accounting for the wind velocity.
	private double getDragForce(double time, double vx, double vy) {
		double velocityMag = getVelocityMag(vx, vy);
		double radius = getRadius(time);
		double area = Math.PI * radius * radius;
		return Environment.DENSITY_AIR * velocityMag * velocityMag * area * DRAG_COEFF / 2;
	} // end getDragForce
	
	// Gets the magnitude of the velocity.
	private double getVelocityMag(double vx, double vy) {
		return Math.sqrt(vx * vx + vy * vy);
	} // end getVelocity
		
} //End class Particle.
