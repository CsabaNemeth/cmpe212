package assignment2;

/*
 * CMPE 212 Assignment 2 - Roman Candle Modeling
 * Written By: Csaba Nemeth 20090753
 * 
 * The following program models the flight of a Roman candle using both 
 * Newtonian and non-Newtonian physics. The program saves two .txt files 
 * containing t, x, y data points to the local directory.
 * 
 * Method: For non-Newtonian modeling, the runge-kutta method was used to solve
 * the partial differential equation to calculate the velocity change from 
 * t -> t + delta_t.
 * 
 * The method: getPath updates and writes all points to arrays across a 
 * for loop. The method findNextV applies the runge-kutta approximation 
 * to calculate the next velocity step as described above. 
 * NOTE: The Roman candle is assumed to be a perfect sphere whose mass, 
 * and thus cross section recede at a steady rate. Therefore the cross
 * sectional area is calculated as a function of density and mass. This process 
 * is completed in the method findCrossSection.
 */

//Import buffered writer for file writing.
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Assn2_17cmn {
	
	//Declare attributes.
	public static final double G = 9.807;  				// m/s*s
	public static final double DENSITY_AIR = 1.2;		// kg/m*m*m
	public static final double DRAG_COEFF = 0.4;		// unitless
	public static final double EXIT_VELOCITY = 22;		// m/sec
	public static final double BURN_RATE = 0.0030;		// kg/second
	public static final double DENSITY_STAR = 1900;		// kg/m*m*m
	public static final double STARTING_MASS = 0.008;	// kg
	public static final double DELTA_T = 0.05;			// seconds
	
	
	// This method calculates and returns the estimated path for a single star using the
	// non-Newtonian equations of motion.
	// The method must be supplied with the two initial velocity components for the
	// star at time=0, in m/sec, as well as the wind velocity which is assumed to be constant. 
	// The method also assumes that the starting position of the star is (0, 0).
	// The path is returned as a two-dimensional array, where the first column is time in
	// seconds, the second column is x in metres and the third column is y in metres.
	public static double[][] getPath (double vxInitial, double vyInitial, double vWind) {
		int numPoints = (int)Math.round(STARTING_MASS / BURN_RATE / DELTA_T) + 1;
		double[][] points = new double[numPoints][3];

		//Declare initial conditions.
		double currX = 0;
		double currY = 0;
		double time = 0;
		double currMass = STARTING_MASS;
		double[] currV = {vxInitial, vyInitial};
		
		//Loop and update all points
		for (int i = 0; i < numPoints; i++) {
			points[i][0] = time;
			points[i][1] = currX;
			points[i][2] = currY;
			
			//Update values.
			time = time + DELTA_T;
			currMass = findNextMass(currMass);
			currV = findNextV(currV[0], currV[1], currMass, vWind);
			System.out.println(currV[0]);
			currX = currX + currV[0]*DELTA_T;
			currY = currY + currV[1]*DELTA_T;
			
		}
		
		return points;
		
		
	} // end getPath
	
	//Find next velocity components in iteration using the runge-kutta method.The method 
	//must be supplied with velocities relative to the ground, as well as a mass and 
	//a wind-speed. The method returns and array/vector of the form [vx, vy].
	public static double[] findNextV(double vxk, double vyk, double mass, double wind) {
		double[] v = new double[2];
		double vxak = findRelativeVx(vxk, wind);
		
		double q1x = dVx_dt(vxak, vyk, mass);
		double q1y = dVy_dt(vxak, vyk, mass);
		double q2x = dVx_dt(vxak + (DELTA_T*q1x)/2, vyk + (DELTA_T*q1y)/2, mass);
		double q2y = dVy_dt(vxak + (DELTA_T*q1x)/2, vyk + (DELTA_T*q1y)/2, mass);
		double q3x = dVx_dt(vxak + (DELTA_T*q2x)/2, vyk + (DELTA_T*q2y)/2, mass);
		double q3y = dVy_dt(vxak + (DELTA_T*q2x)/2, vyk + (DELTA_T*q2y)/2, mass);
		double q4x = dVx_dt(vxak + (DELTA_T*q3x), vyk + (DELTA_T*q3y), mass);
		double q4y = dVy_dt(vxak + (DELTA_T*q3x), vyk + (DELTA_T*q3y), mass);
		
		v[0] = vxk + (DELTA_T/6)*(q1x + 2*q2x + 2*q3x + q4x);
		v[1] = vyk + (DELTA_T/6)*(q1y + 2*q2y + 2*q3y + q4y);
		
		return v;
		
	}// end findNextV
	
	//Calculates the instantaneous time derivative of x velocity. Must be supplied with
	//the x-windspeed relative to the air, as well as vy relative to ground and a mass.
	public static double dVx_dt(double vxa, double vy, double mass) {
		return (- findDragForce(vxa, vy, mass)*vxa/(mass*findVelocityMagnitude(vxa, vy)));
	}// end dVx_dt
	
	//Calculates the instantaneous time derivative of y velocity. Must be supplied with
	//the x-windspeed relative to the air, as well as vy relative to ground and a mass.
	public static double dVy_dt(double vxa, double vy, double mass) {
		return (-G- findDragForce(vxa, vy, mass)*vy/(mass*findVelocityMagnitude(vxa, vy)));
	}// end dVy_dt
	
	//Returns the magnitude of the drag force.
	public static double findDragForce(double vxa, double vy, double mass) {
		double v = findVelocityMagnitude(vxa, vy);
		double A = findCrossSection(mass);
		double FD = (DRAG_COEFF * Math.pow(v, 2) * A * DENSITY_AIR)/2;
		return FD;
	}// end findDragForce
		
	//Returns the magnitude of the velocity (relative to the wind speed).
	public static double findVelocityMagnitude(double vxa, double vy) {
		return Math.sqrt(Math.pow(vxa, 2) + Math.pow(vy, 2));
	}// end findVelocityMagnitude
		
	//Finds the relative x-velocity due to air.
	public static double findRelativeVx(double vx, double wind) {
		return (vx - wind);

	}// end findRelativeVx
	
	//Returns the cross sectional area of the fireworks based on the mass and density.
	//This method works under the assumption that the roman candle is a perfect sphere.
	public static double findCrossSection(double mass) {
		double volume = mass/DENSITY_STAR;
		double r = Math.cbrt(volume*3/4/Math.PI);
		double A = (Math.PI) * (Math.pow(r, 2));
		return A; //return cross sectional area
	}// end findCrossSection
	
	//Finds the mass of the fireworks for the next time-step.
	public static double findNextMass(double currentMass) {
		return (currentMass - (DELTA_T * BURN_RATE));
	}// end findNextMass
		
	// This method calculates and returns the path for a single star assuming just
	// Newtonian physics for the equations of motion - no drag is involved.
	// The method must be supplied with the two initial velocity components for the
	// star at time=0, in m/sec. 
	// The method also assumes that the starting position of the star is (0, 0).
	// The path is returned as a two-dimensional array, where the first column is time in
	// seconds, the second column is x in metres and the third column is y in metres.
	// THIS CODE IS SUPPLIED
	public static double[][] getNewtonianPath (double vxInitial, double vyInitial) {
		int numPoints = (int)Math.round(STARTING_MASS / BURN_RATE / DELTA_T) + 1;
		double[][] points = new double[numPoints][3];
		double time = 0;
		// Calculate the points
		for (int i = 0; i < numPoints; i++) {
			points[i][0] = time;
			points[i][1] = vxInitial * time;
			points[i][2] = vyInitial * time - 0.5 * G * time * time;
			time = time + DELTA_T;
		}
		return points;		
	} // end getNewtonianPath
	
	
	
	// Save data to a tab delimited text file. The data type String is used to generate
	// the filename. The method must be supplied with a 2D array.
	public static void writeData(String dataType, double[][] points) {
		Path file = Paths.get(dataType);
		try (BufferedWriter writer = Files.newBufferedWriter(file)) {
			for (double[] item : points) {
				writer.write(Math.round(item[0]*1000.0)/1000.0 + "\t" + Math.round(item[1]*1000.0)/1000.0 + "\t" + Math.round(item[2]*1000.0)/1000.0 + "\r\n");
			}
		} catch (IOException err) {
			System.err.println(err.getMessage());
		}
	} // end writeData
	

	// The main method obtains the wind velocity and firing angle from the user using
	// the getDouble() method from the IOHelper class.  This method will re-prompt the user
	// if the value supplied is not within the provided ranges.
	// Then main obtains the star path and displays the results for both Newtonian and
	// Non-Newtonian equations of motion.
	// THIS CODE IS SUPPLIED.  USE YOUR OWN IOHelper CLASS.
	public static void main(String[] args) {
		String prompt = "Enter the cross-wind velocity in km/hour, between -20 and 20: ";
		double wind = IOHelper.getDouble(-20, prompt, 20);
		prompt = "Enter the firing angle in degrees between -15 and 15: ";
		double angle = IOHelper.getDouble(-15, prompt, 15);
		wind = wind * 1000. / 3600.;		// convert to m/sec
		angle = angle * Math.PI / 180.;		// convert to radians
		double v0x = EXIT_VELOCITY * Math.sin(angle);
		double v0y = EXIT_VELOCITY * Math.cos(angle);
		writeData("Newtonian.txt", getNewtonianPath(v0x, v0y));
		writeData("NonNewtonian.txt", getPath(v0x, v0y, wind));
		System.out.println("Done");
	} // end main

}
