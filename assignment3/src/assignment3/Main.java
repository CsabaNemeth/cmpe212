//package assignment3;

/**
 * 
 * Contains the main method and deals with all user interaction to initialize the simulation.
 * 
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see IOHelper
 * @see WriteData
 */
//Contains the main method for running the simulation.
public class Main {
	
	/**
	 * 
	 * The main method contains all user interaction to specify wind speed and launcher firing angle.
	 * It creates instances of {@link Environment}, {@link LaunchTube} and {@link PathManager}, and runs the simulation.
	 * Also handles all uncaught exceptions from classes. The time interval is currently hard coded at 0.05 seconds.
	 * 
	 * @param args Command line argument.
	 * @see Environment
	 * @see LaunchTube
	 * @see PathManager
	 */
	//Main method. Contains all user interaction needed to create instances 
	//of the environment and launcher. 
	public static void main(String[] args) {
		
		//Attribute declarations.
		Environment currentEnvironment = null;
		LaunchTube currentLauncher = null;
		PathManager nonNewtonianPath = null;
		double timeInterval = 0.05; //Currently hard coded as instruction did not specify this should be user interaction.
		
		//Create environment instance, held in a try catch block that
		//catches an illegal wind velocity Exception if the user input is outside the range.
		try {
			String prompt = "Enter the cross-wind velocity in km/hour, between -20 and 20: ";
			double wind = IOHelper.getDouble(-20, prompt, 20);
			currentEnvironment = new Environment(wind);
		} catch (IllegalWindVelocityException error) {
			System.err.println(error.getMessage());
		} //End try-catch.
		
		//Create launch tube instance, held in a try catch block that
		//catches an illegal angle exception if the user input is outside the range.
		try {
			String prompt = "Enter the firing angle in degrees between -15 and 15: ";
			double angle = IOHelper.getDouble(-15, prompt, 15);
			currentLauncher = new LaunchTube(angle, currentEnvironment);
		} catch (IllegalLaunchAngleException error) {
			System.err.println(error.getMessage());
		} //End try-catch.
		
		//Create path manager instance and call getPath to receive data for file writing. Held in a 
		//try catch block that returns an illegal time interval exception.
		//For user interaction regarding the time interval implement this code in the try-catch block:
		/*
		 * String prompt = "Enter the time step interval between 0 (exclusive) and 0.05:";
		 * double timeInterval = IOHelper.getDouble(0, prompt, 0.05);
		 */
		try {
			nonNewtonianPath = new PathManager(currentEnvironment, currentLauncher, timeInterval);
			WriteData.write("NonNewtonian.txt", nonNewtonianPath.getPathData());
			System.out.println("Done");
		} catch (IllegalTimeIntervalException error) {
			System.err.println(error.getMessage());
		} //End try-catch.
		
	} //End main method.
	
} //End Main class.
