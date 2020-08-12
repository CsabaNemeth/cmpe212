//package assignment3;

/**
 * A utility class used to instantiate the {@link Star} class under the current environment conditions.
 * 
 * The class contains a static attribute defining the exit velocity of the projectile, and variable instances holding 
 * the x, y position, and the launch angle. Upon instantiation of the Star class, the exit velocity and initial position is passed to
 * the constructor.
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see Star
 * @see Environment
 */
/*
 * Class holds internal information on launch specifications and instantiates stars.
 * The instances of the LaunchTube are created by the PathManager class. Currently the
 * exit velocity is a hard coded value, but can be easily altered to accept user input through the
 * constructor.
 */
public class LaunchTube {
	
	//Attribute declaration.
	public static final double EXIT_VELOCITY = 22; // m/s -> Currently a hard coded value.
	private double xPosition = 0; //Default value
	private double yPosition = 0; //Default value
	private double angle;							// rad
	
	private Environment currentEnvironment;
	private double vxInitial;
	private double vyInitial;

	/**
	 * Constructor for the LaunchTube class.
	 * 
	 * @param launchAngle The angle in degrees as measured from the vertical.
	 * @param currentEnvironment An instance of the Environment class.
	 * @throws IllegalLaunchAngleException if the launch angle is outside the range [-15, 15].
	 */
	//Constructor calls upon two methods to set private attributes.
	public LaunchTube(double launchAngle, Environment currentEnvironment) throws IllegalLaunchAngleException {
		setLaunchAngle(launchAngle);
		setEnvironment(currentEnvironment);
	} //End constructor.
	
	/* Checks to see if the passed launch angle is within the specified range. If not, throws 
	 * an exception. Otherwise, converts the angle to radians and modifies the angle instance variable.
	 */
	private void setLaunchAngle(double launchAngle) throws IllegalLaunchAngleException{
		if (launchAngle < -15 || launchAngle > 15) {
			throw new IllegalLaunchAngleException("Illegal Launch Angle: " + launchAngle);
		} //End if statement.
		angle = launchAngle * Math.PI / 180.;
	} //End method.
	
	/*
	 * Sets the environment instance to the passed instance. 'this.environment' is used 
	 * to differentiate between the argument and the local attribute.
	 */
	private void setEnvironment(Environment currentEnvironment) {
		this.currentEnvironment = currentEnvironment;
	} //End method.
	
	/**
	 * @return Angle of launcher in radians.
	 */
	//Allows external access to private attribute angle.
	public double getAngle() {
		return angle;
	} //End method.
	
	/**
	 * @return An array of doubles storing the (x,y) position of the launcher.
	 */
	//Allows external access to the private attributes defining the current position of the launcher.
	public double[] getPosition() {
		return new double[] {xPosition, yPosition};
	} //End method.
	
	/**
	 * Instantiates and returns a Star object. Position to launch from is passed 
	 * to the method, which becomes the current position of the launcher.
	 * 
	 * @param xPosition The x position (m) to launch the star from.
	 * @param yPosition The y position (m) to launch the star from.
	 * @return An instance of the Star object.
	 * @see Star
	 */
	//The method instantiates a star object with the arguments of position. "this" keyword
	//is used to differentiate between external argument and internal attributes. It is assumed 
	//that the angle is measured from the vertical.
	public Star getNewStar(double xPosition, double yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		vxInitial = EXIT_VELOCITY * Math.sin(angle);
		vyInitial = EXIT_VELOCITY * Math.cos(angle);
		return new Star(xPosition, yPosition, vxInitial, vyInitial, currentEnvironment);
	} //End method.
} //End LaunchTube class.
