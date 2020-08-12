//package assignment4;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * A console based testing program for assignment 4.  This program will run the simulation
 * and display the number of particles generated at each time interval as well as a "snapshot"
 * of the particle locations at 2.0 seconds into the simulation.  Due to the the random aspects
 * of the simulation your numbers are unlikely to match the ones shown in the sample output.
 * @author Alan McLeod
 * @version 1.0
 */
public class Assn4_Testing {

	private static Scanner input = new Scanner(System.in);
	
	/**
	 * Obtains the wind velocity from the user between -20 and 20 km/hour.
	 * @return The wind velocity in km/hour.
	 */
	public static double getWindVelocity() {
		boolean inputOK = false;
		double wind = 0;
		while (!inputOK) {
			try {
				System.out.print("Enter the cross-wind velocity in km/hour, between -20 and 20: ");
				wind = input.nextDouble();
				inputOK = true;
			} catch (InputMismatchException e) {
				System.out.println("You did not enter a valid double!");
				@SuppressWarnings("unused")
				String dummy = input.nextLine();
			}
		} // end while
		return wind;
	} // end getWindVelocity
	
	/**
	 * Obtains the launch angle in degrees between -15 and 15.
	 * @return The launch angle in degrees away from the vertical.
	 */
	public static double getLaunchAngle() {
		boolean inputOK = false;
		double launchAngle = 0;
		while (!inputOK) {
			try {
				System.out.print("Enter the firing angle in degrees between -15 and 15: ");
				launchAngle = input.nextDouble();
				inputOK = true;
			} catch (InputMismatchException e) {
				System.out.println("You did not enter a valid double!");
				@SuppressWarnings("unused")
				String dummy = input.nextLine();
			}
		} // end while
		return launchAngle;
	} // end getLaunchAngle
	
	/**
	 * Displays a count of all the particle types in the supplied collection at the
	 * given time.
	 * @param fireworks An ArrayList<Particle> collection.
	 * @param time The time in seconds.
	 */
	public static void showTypesCount(ArrayList<Particle> fireworks, double time) {
		int starCount = 0;
		int sparkCount = 0;
		int launchSparkCount = 0;
		for (Particle firework : fireworks) {
			if (firework instanceof Star)
				starCount++;
			else if (firework instanceof LaunchSpark)
				launchSparkCount++;
			else
				sparkCount++;
		}
		System.out.printf("%5.2f\t", time);
		System.out.println(starCount + "\t" + sparkCount + "\t" + launchSparkCount);
	} // end showTypesCount
	
	/**
	 * Prints out each firework in the supplied collection and its position at the given time.
	 * @param fireworks A snapshot of the fireworks collection.
	 * @param time The time the snapshot was taken in seconds.
	 */
	public static void showFireworks(ArrayList<Particle> fireworks, double time) {
		if (fireworks == null)
			return;
		System.out.printf("\nAt time%5.2f seconds:\n", time);
		System.out.println("Type\t\tPosition (metres)");
		for (Particle firework : fireworks)
			System.out.println(firework);
	}
	
	/**
	 * Obtains the required parameters, runs the simulation and displays some results.
	 * @param args Not used...
	 */
	public static void main(String[] args) {
		double wind = getWindVelocity();
		double launchAngle = getLaunchAngle();
		ArrayList<Particle> fireworks;
		ArrayList<Particle> snapshot = null;
		input.close();
		double timeInterval = 0.05;		// seconds
		ParticleManager manager = null;
		try {
			manager = new ParticleManager(wind, launchAngle);
		} catch (EnvironmentException except) {
			System.out.println(except.getMessage());
			return;
		} catch (EmitterException except) {
			System.out.println(except.getMessage());			
			return;
		}
		System.out.println("Counts:");
		System.out.println("time\tStars\tSparks\tLaunchSparks");
		double time = 0;
		manager.start(time);
		fireworks = manager.getFireworks(time);
		do {
			//if (time % 0.5 == 0)
				showTypesCount(fireworks, time);
			if (Math.abs(time - 2.0) < timeInterval)
				snapshot = fireworks;
			time += timeInterval;
			fireworks = manager.getFireworks(time);
		} while (fireworks.size() > 0);
		showFireworks(snapshot, 2.0);
	} // end main

} // end Assn4_Testing