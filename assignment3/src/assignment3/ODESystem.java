//package assignment3;

/**
 * An interface used by the RungeKuttaODE class to 
 * numerically solve a system of differential equations.
 * 
 * 
 * @author Csaba Nemeth
 * @version 1.0
 * @see RungeKuttaODE
 *
 */
//This interface needs to be implemented by any class to successfully use the RungeKuttaODE solver.
public interface ODESystem {
	
	/**Should return the size of the system to be solved.*/
	int getSystemSize();
	
	/** Should return an array of doubles with size {@link getSystemSize()} that 
	 * contain the current values of the system. 
	 */
	double[] getCurrentValues();
	
	/**
	 * Should return an array of doubles which contain the function(s) evaluated 
	 * at values and time.
	 * 
	 * @param time The time to evaluate the functions at.
	 * @param values An array of doubles {x1, x2, ..., xn} to evaluate the system at.
	 */
	double[] getFunction(double time, double[] values);

} //End ODESystem Interface
