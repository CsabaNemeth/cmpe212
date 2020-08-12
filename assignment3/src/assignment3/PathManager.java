//package assignment3;

/**
 * 
 * The PathManager class holds an instance of a {@link Star} object and tracks it over the flight path.
 * The class contains a single public method to retrieve the tracked data.
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see Environment
 * @see LaunchTube
 */
//The PathManager class is instantiated by the main method and manages all particles in the simulation.
public class PathManager {
	
	//Attribute declaration.
	private double DT;
	private Environment currentEnvironment; //Not used in the current version, will be used later when environment changes throughout sim.
	private LaunchTube launchTube;
	
	/**
	 * 
	 * Constructor for the {@link PathManager} class.
	 * 
	 * @param environment An instance of the {@link Environment} class.
	 * @param launchTube An instance of the {@link LaunchTube} class.
	 * @param timeInterval The time step used to solve (ie the variation in time between points).
	 * @throws IllegalTimeIntervalException if timeInterval is negative or greater than 0.05.
	 */
	//Checks to see if timeInterval is within expected range. Throws exception if needed.
	public PathManager(Environment environment, LaunchTube launchTube, double timeInterval) throws IllegalTimeIntervalException {
		if (timeInterval <= 0 || timeInterval > 0.05) {
			throw new IllegalTimeIntervalException("Illegal Time Interval: " + timeInterval);
		}
		DT = timeInterval;
		currentEnvironment = environment;
		this.launchTube = launchTube; //This keyword used for argument with same name as attribute.
	} //End constructor
	
	/**
	 * 
	 * Creates a new instance of {@link Star} using the {@link LaunchTube} contained in the class and tracks the flight path, 
	 * returning a 2D array of doubles defining the position over time. The number of points is found using the flight time of a 
	 * Star object and the time interval supplied to the constructor.
	 * 
	 * @return A 2D array of doubles holding the time, x, and y coordinates for each step across the flight path.
	 * @see LaunchTube
	 * @see Star
	 */
	//Instantiates star and stores data along path into array.
	//Time is currently tracked internal to the method assuming time starts at 0 for each particle instance.
	public double[][] getPathData() {
		double time = 0;
		int numPoints = (int)Math.round(Star.getTimeOfFlight() / DT) + 1;
		Star currentStar = launchTube.getNewStar(0, 0);
		double[][] pathData = new double[numPoints][3];
		for (int i = 0; i < numPoints; i++) {
			
			pathData[i][0] = time;
			pathData[i][1] = currentStar.getCurrentPosition()[0];
			pathData[i][2] = currentStar.getCurrentPosition()[1];
			
			time = time + DT;
			currentStar.updatePosition(time, DT);
			
		} //End for loop.
		
		return pathData.clone();
	} //End method.
		

} //End PathManager class.
