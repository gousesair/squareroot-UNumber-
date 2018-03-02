package demoSquareRootWithUNumberWithMissingCode;

import java.util.Scanner;

import uNumberLibrary.UNumber;
import java.io.File;

/**
 * <p>
 * Title: DemoNewtonMethod.
 * </p>
 * 
 * <p>
 * Description: Mainline to demo the Newton-Raphson square root method
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter © 2017
 * </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00 Initial baseline
 * 
 */

public class DemoSquareRootWithUNumberWIthMissingCode {

	public static int numSignificantDigits;

	/*****
	 * This private method counts how many digits are the same between two estimates
	 */
	private static int howManyDigitsMatch(UNumber newGuess, UNumber oldGuess) {
		// If the characteristics is not the same, the digits in the mantissa do not
		// matter
		if (newGuess.getCharacteristic() != oldGuess.getCharacteristic())
			return 0;

		// The characteristic is the same, so fetch the mantissas so we can compare them
		byte[] newG = newGuess.getMantissa();
		byte[] oldG = oldGuess.getMantissa();

		// Computer the shorter of the two
		int size = newGuess.length();
		int otherOne = oldGuess.length();
		if (otherOne < size)
			size = otherOne;

		// Loop through the digits as long as they match
		for (int ndx = 0; ndx < size; ndx++)
			if (newG[ndx] != oldG[ndx])
				return ndx; // If the don't match, ndx is the result

		// If the loop completes, then the size of the shorter is the length of the
		// match
		return size;
	}

	public static int match2(UNumber x) {
		String str = x.toString(numSignificantDigits);
		File file = new File("F:\\msit\\software foundations\\week3\\Day 2\\02 SquareRootOf2.txt");
		String s="";
	    try {

	        Scanner sc = new Scanner(file);

	        while (sc.hasNextLine()) {
	            s+=sc.nextLine();
	        }
	        sc.close();
	    } 
	    catch(Exception e) {
	    	System.out.println("");
	    }
	     str = str.replaceAll("[^0-9]", "");
	     s = s.replaceAll("[^0-9]", "");
	     int count=0;
	     System.out.println(str);
	     System.out.println(s);
	     return count;
	 
	}
	/*****
	 * This is the mainline
	 * 
	 * @param args
	 *            The program parameters are ignored
	 */
	public static void main(String[] args) {
		// Set up keyboard as a Scanner object using the console keyboard for input
		Scanner keyboard = new Scanner(System.in);
		int flag=0;
		// Request a floating point value from the user in the form of three items
		System.out.println("Enter a double value or just press return (enter) to stop the loop: ");
		System.out.println("Enter the digists matching aqnd it has to range from 10 to 10000");
		numSignificantDigits = Integer.parseInt(keyboard.nextLine());
		if(numSignificantDigits < 10 || numSignificantDigits>9999) {
			System.out.println("Invalid input");
			System.exit(0);
		}
		// Fetch the input from the user, removing leading and trailing white space
		// characters
		String input = keyboard.nextLine().trim();

		// As long as the length of the input String is positive, continue processing
		// the input
		while (input.length() > 0) {
			Scanner value = new Scanner(input);
			// Does this input line consist of a value?

			// As long as there is another double value, compute the square root of it
			System.out.println("*****************************************************");
			double inputValue = value.nextDouble();
			if(inputValue==2.0) {
				flag=1;
			}
			System.out.print("The value to be used: ");
			System.out.println(inputValue);
			System.out.println("The result of the square root with and estimate of one half the value");

			UNumber theValue = new UNumber(inputValue);

			UNumber two = new UNumber(2.0);

			UNumber newGuess = new UNumber(theValue); // Compute the estimate
			newGuess.div(two);
			System.out.println(newGuess + "\n"); // Display the first estimate

			UNumber oldGuess; // Temporary value for determining when to terminate the loop

			int iteration = 0; // Count the number of iterations
			int digitsMatch = 0;
			do {
				long start = System.nanoTime();
				iteration++; // Next iteration
				//
				// This old double code needs to be replace with UNumber code
				//
				oldGuess = newGuess; // Save the old guess
				//
				// newGuess = (theValue/oldGuess+oldGuess)/two; // Compute the new guess
				//
				newGuess = new UNumber(theValue, numSignificantDigits); // Compute the new guess
				newGuess.div(oldGuess);
				newGuess.add(oldGuess);
				newGuess.div(two);
				long stop = System.nanoTime();

				digitsMatch = howManyDigitsMatch(newGuess, oldGuess);
				System.out.println("     " + iteration + " estimate " + newGuess.toString() + " with " + digitsMatch
						+ " digits matching taking " + (stop - start) / 1000000000.0 + " seconds"); // Display the
																									// intermediate
																									// result

			} while (digitsMatch < numSignificantDigits); // Determine if the old and the new guesses are "close enough"

			System.out.println("The square root");
			System.out.println(newGuess); // Display the final result
			UNumber resultSquared = new UNumber(newGuess);
			resultSquared.mpy(newGuess);
			System.out.println("\nThe square of the computed square root.  (It should be *very* close to the input value!):");
			System.out.println(resultSquared); // Display the result squared
			if(flag==1) {
				System.out.println("The number of digits matched with sqrt2 for digits matching"+" "+numSignificantDigits+" "+"is -"+match2(resultSquared));
			}

			// Ask for more input
			System.out.println("Enter the digists matching aqnd it has to range from 10 to 1000");
			input = keyboard.nextLine().trim();
			if(input.length()>1)
				numSignificantDigits = Integer.parseInt(input);
			else
				{
				System.out.print("Empty line detected... the program stops");
				System.exit(0);
				}
			System.out.print("\nEnter a double value or just press return (enter) to stop the loop: ");
			input = keyboard.nextLine().trim();
			value.close();
		}
		// An empty input line has been entered, so the tell the user we are stopping
		System.out.print("Empty line detected... the program stops");
		keyboard.close();
	}
}