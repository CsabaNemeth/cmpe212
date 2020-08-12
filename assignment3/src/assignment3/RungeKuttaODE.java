//package assignment3;

/**
 * A utility class used to solve a system of differential equations using the Runge-Kutta numerical method.
 * The class has a single static method that used the {@link ODESystem} interface. The system can be of any size. 
 * For more information on the runge-kutta algorithm see
 *  <a href = "RungeKuttaMethod">https://en.wikipedia.org/wiki/Runge%E2%80%93Kutta_methods </a> .
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see ODESystem
 */
public class RungeKuttaODE {
	
	/**
	 * Solves the system of differential equations contained in a class implementing 
	 * ODESystem.
	 * 
	 * @param system A class implementing ODESystem.
	 * @param time The time of the current values.
	 * @param dt The time step used to solve.
	 * @return An array of doubles containing the solution values at (time + dt).
	 */
	//Works for any arbitrarily sized system. The "x" value in a regular Runge Kutta solver
	//is replaced with time on the horizontal axis. Therefore, this solver assumes a time 
	//dependant system.
	public static double[] solve(ODESystem system, double time, double dt) {
		int systemSize = system.getSystemSize();
		double[] solution = new double[systemSize];
		double[] currentValues = system.getCurrentValues();
		
		double[] q1 = system.getFunction(time, currentValues);
		double[] q2 = system.getFunction(time+ dt/2, findNextQInput(currentValues, q1, dt, systemSize, 2));
		double[] q3 = system.getFunction(time + dt/2, findNextQInput(currentValues, q2, dt, systemSize, 3));
		double[] q4 = system.getFunction(time + dt, findNextQInput(currentValues, q3, dt, systemSize, 4));
		
		for (int i = 0; i < systemSize; i++) {
			solution[i] = currentValues[i] + (dt/6)*(q1[i] + 2*q2[i] + 2*q3[i] + q4[i]);
		}		
		return solution;
	}
	
	//Helper function to create inputs for ODESystem.getFunction() for an arbitrary sized system.
	private static double[] findNextQInput(double[] currentValues, double[] qOutput, double dt, int systemSize, int qNum) {
		double[] newQVals = new double[systemSize];
		for (int i = 0; i < systemSize; i++) {
			if (qNum != 4) {
				newQVals[i] = currentValues[i] + (dt/2)*qOutput[i];
			}
			else {
				newQVals[i] = currentValues[i] + dt*qOutput[i];
			}
		}
		return newQVals;
	}
	

}
