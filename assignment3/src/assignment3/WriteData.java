//package assignment3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The WriteData class implements a single static method
 * to write a 2D array of doubles into a tab-delimited text file.
 * Requires java.io and java.nio packages.
 * 
 * @author Csaba Nemeth
 * @version 1.0
 *
 */
//A general utility class containing a single method to write an array of doubles to a text file.
public class WriteData {
	
	/**
	 * Writes a 2D array of type double into a tab-delimited text file.
	 * 
	 * This method uses the java.io.BufferedWriter class. The text file is 
	 * written into the working directory. 
	 * 
	 * @param dataFileName String specifying the desired name of the text file.
	 * @param data 2D array of type double to be written to the text file.
	 * @exception java.io.IOException caught within the method.
	 * 
	 */
	//Uses a buffered write from the java.io library to write an array of doubles 
	//efficiently to a tab-delimited text file. Catches errors thrown by BufferedWriter.
	public static void write(String dataFileName, double[][] data) {
		Path file = Paths.get(dataFileName);
		try (BufferedWriter writer = Files.newBufferedWriter(file)) {
			for (double[] item : data) {
				writer.write(Math.round(item[0]*1000.0)/1000.0 + "\t" + Math.round(item[1]*1000.0)/1000.0 + "\t" + Math.round(item[2]*1000.0)/1000.0 + "\r\n");
			}
		} catch (IOException err) {
			System.err.println(err.getMessage());
		} //End try-catch.
	} //End method.
	
	

} //End WriteData class.
