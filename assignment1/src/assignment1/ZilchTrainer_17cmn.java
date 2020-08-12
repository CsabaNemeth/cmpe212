package assignment1;

//Import required libraries
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;
import java.lang.Math;
/* CMPE 212 Assignment 1
 * 
 * Completed By: Csaba Nemeth
 * NetID: 17cmn, 20090753
 * 
 * This is a training program for the popular dice game zilch.
 * Scoring implementation was written by myself while the rest of the program was completed
 * by prof. Alan McLeod.
 * 
 * SCORING METHODOLOGY
 * 
 * Scoring is controlled by the scoreThrow() method which creates an occurrence array that stores
 * all the occurrences of a value n at index n - 1. The occurrence array is passed to scoring methods under the
 * hierarchy specials, multiples, ones and fives. As the scores are added up, the occurrence array is modified before
 * passing on to the next level. Occurrence array is modified with the help of counting methods.
 * 
 * 
 * Note: I added two lines of code in runTrainer()
 * 		After the current turn is over:
 * 
 * 		numToRoll = 6;
		freeRoll = false;
		
		Added more-so as a safety measure to ensure full reset.
 */

public class ZilchTrainer_17cmn {
	
	// YOU CANNOT ADD ANY MORE ATTRIBUTES!
	// Seeding the generator ensures that a different sequence will be used
	// each time the game is played.  You can invoke .nextInt(n) from the
	// generator object to get a random integer between 0 and (n-1).
	public static Random generator = new Random(System.currentTimeMillis());
	public static final int GAME_LIMIT = 10000;
	private static int numToRoll = 6;
	private static boolean freeRoll = false;
	
	//Rolls a single die (returns number between 1 and 6).
	private static int rollOneDice() {
		return generator.nextInt(6) + 1;
	}
	
	//Rolls six dice and returns an array.
	private static int[] rollDice(int numToRoll) {
		int[] dice = new int[numToRoll];
		for (int i = 0; i < numToRoll; i++) 
			dice[i] = rollOneDice();
		int[] dice1 = {2, 3, 4, 4, 3, 2};
		return dice1;
	}
	
	//Returns number of times key is found within the integer array.
	private static int intInArray(int[] arr, int key) {
		int counter = 0;
		for (int item : arr) 
			if (item == key) counter++;
		return counter;
	}
	
	//Returns array that stores how many times element occurs in array. 
	private static int[] timesOccuring(int[] dice) {
		int[] occurrences = new int[6]; 
		for (int i = 0; i < 6; i++)
			occurrences[i] = intInArray(dice, i + 1);
		return occurrences;
	}
		
	//Prints special roll statement and returns the corresponding score.
	private static int scoreSpecials(int[] occurrences) {
		if (intInArray(occurrences, 0) == 0) {
			printSpecialScoreStatement(2);
			return 1500;
		}
		if (occurrences[0] == 0 && occurrences[4] == 0) { //No ones and no twos
			if (occurrences[1] <= 2 && occurrences[2] <= 2 && occurrences[3] <=2 && occurrences[5] <=2) {
				printSpecialScoreStatement(1);
				return 500;			
			}
		}
		if (intInArray(occurrences, 2) == 3) {
			printSpecialScoreStatement(3);
			return 1500;
		}
		return 0;
	}
	
	//Prints ones and fives roll statement and returns the score of ones and fives.
	private static int scoreOnesAndFives(int[] occurrences) {
		int score = 0;
		score += occurrences[0]*100;
		score += occurrences[4]*50;
		printOnesAndFivesScoreStatement(occurrences[0], occurrences[4]);
		return score;
	}
	
	//Return the score contributed to by multiples greater than 3. 
	private static int scoreMultiples(int[] occurrences) {
		int score = 0;
		for (int i = 0; i < occurrences.length; i++) {
			if(occurrences[i] >= 3) {
				if (i != 0) {
					score += (i+1)*100*Math.pow(2, occurrences[i]-3);
				}
				else {
					score += 1000*Math.pow(2, occurrences[i]-3);
				}
			}
		}
		return score;
	}
	
	//Prints multiples score statement and returns the dice values that score multiples.
	private static int[] findMultiples(int[] occurrences) {
		int[] multiples = {0, 0};
		for (int i = 0; i < occurrences.length; i++) {
			if (occurrences[i] >= 3) {
				printMultiplesScoreStatement(occurrences[i]);
				if (multiples[0] == 0) {
					multiples[0] = i+1;
				}
				else {
					multiples[1] = i+1;
				}
			}	
		}
		return multiples;
	}
	
	//Print score statement for special roll, variable "type" indicates the type of special roll scored.
	private static void printSpecialScoreStatement(int type) {
		if (type == 1) System.out.print(" *No score with 6 dice!*");
		else if (type == 2) System.out.print(" *Run from 1 to 6 with 6 dice!*");
		else System.out.print(" *Three pairs!*");
	}
	
	//Prints score statement for multiples that were rolled.
	private static void printMultiplesScoreStatement(int occurrences) {
		if (occurrences == 3) System.out.print(" *Three of a kind!*");
		else if (occurrences == 4) System.out.print(" *Four of a kind!*");
		else if (occurrences == 5) System.out.print(" *Five of a kind!*");
		else System.out.print(" *Six of a kind!*");
	}
	
	//Prints score statement for ones and fives that were rolled.
	private static void printOnesAndFivesScoreStatement(int ones, int fives) {
		if (ones == 1) System.out.print(" *One one!*");
		else if (ones == 2) System.out.print(" *Two ones!");
		if (fives == 1) System.out.print(" *One five!*");
		else if (fives == 2) System.out.print(" *Two fives!*");
	}
	
	//Scores dice consistent with rules found at http://zilch.playr.co.uk/rules.php.
	//Method updates the occurrences, returns the total score and updates attributes.
	private static int scoreThrow(int[] dice) {
		int score = 0;
		int[] occurrences = timesOccuring(dice);
		if (numToRoll == 6) { //Compute special roll score.
			int specialScore = scoreSpecials(occurrences);
			if (specialScore != 0) {
				numToRoll = 6;
				freeRoll = true;
				return specialScore;
			}
		}
		score  += scoreMultiples(occurrences); //Compute multiple elements roll score.
		int[] multiples = findMultiples(occurrences);
		if (multiples[0] != 0) {
			occurrences[multiples[0] - 1] = 0;
			if (multiples[1] != 0) {
				occurrences[multiples[1] - 1] = 0;
			}
		}
		score += scoreOnesAndFives(occurrences); //Compute ones and fives roll score.
		occurrences[0] = 0;
		occurrences[4] = 0;
		numToRoll = Arrays.stream(occurrences).sum(); //Compute final number to re-roll.
		return score;
						
	}
	
	
	//--------------GIVEN-----------------
	
	// Returns the name of the dice roll as a word.
	private static String getNumberName(int roll) {
		String[] names = {"one", "two", "three", "four", "five", "six"};
		return names[roll - 1];
	} // end getNumberName

	// Obtains and returns a single character as provided by the user. If the user enters
	// more than one character the extra characters are ignored. If he or she does not 
	// provide any characters then the null character is returned.
	private static char getChar() {
		byte[] buffer = new byte[100];
		int numRead = 0;
		try {
			numRead = System.in.read(buffer);
		} catch (IOException e) {
		}
		if (numRead > 0)
			return (char)buffer[0];
		return '\0';
	} // end getChar

	// Returns true if the supplied target is in the supplied array, false otherwise.
	private static boolean inArray(char[] array, char target) {
		for (char elem : array)
			if (elem == target)
				return true;
		return false;
	} // end inArray

	// Prompts for, obtains and returns a single character from the user. If the
	// character is not legal, the user is prompted again.
	private static char playerPrompt(String prompt) {
		char response = '?';
		char[] legalResponses = {'r', 'R', 'b', 'B', 'q', 'Q'};
		while (true) {
			System.out.print(prompt);
			response = getChar();
			if (inArray(legalResponses, response))
				return response;
			else
				System.out.print("Illegal entry, please try again. ");
		}
	} // end playerPrompt

	// Displays instructions to the user.
	public static void displayIntro() {
		// A Text block would be useful here!
		String out = "This training program will help you practice the game of Zilch - ";
		out += "\nlearning when to roll again and when it is too risky.";
		out += "\nYou will roll against an AI player who banks or rolls at random.";
		out += "\nYou should be able to win easily if you are making better choices!";
		out += "\n\nPossible responses at a prompt are \"r\" to roll again, \"b\" to";
		out += "\nbank your points, just <enter> and \"q\" to quit the trainer. Otherwise";
		out += "\nthe session will run until one player wins.\n";
		System.out.println(out);
	} // end displayIntro

	// Displays the players dice roll to the console.
	public static void displayRoll(int rollCount, int[] dice) {
		String out = "Roll " + rollCount + ": ";
		for (int die : dice)
			out += "*" + getNumberName(die) + "*";
		out += " Scoring:";
		System.out.print(out);
	} // end displayRoll

	// Plays the game.
	public static void runTrainer() {
		
		boolean playRandom = false;
		int throwSum = 0;
		int turnSum = 0;
		int randomTurnSum = 0;
		int humanTurnSum = 0;
		int bankedSum = 0;
		int randomBankedSum = 0;
		int zilchCount = 0;
		int randomZilchCount = 0;
		int rollCount = 0;
		int randomRollCount = 0;
		int[] dice;
		char input;
		String prompt;
	
		//START GAME
		
		//Ask user to start game.
		System.out.print("Press <enter> to start trainer: ");
		input = getChar();
		
		// Loops until the human has had enough or one player has won.
		while (!(input == 'q' || input == 'Q') && bankedSum < GAME_LIMIT && randomBankedSum < GAME_LIMIT) {
			
			if (playRandom) {
				System.out.print("\n***RANDOM PLAYER Playing*** ");
				randomTurnSum = turnSum;
				humanTurnSum = 0;
				
			} else {
				System.out.print("\n***YOU Are Playing!*** ");
				humanTurnSum = turnSum;
				randomTurnSum = 0;
			}
			
			
			// Display current values for both players.
			System.out.println(numToRoll + " dice left.");
			System.out.print("RANDOM PLAYER Stats: ");
			System.out.print("\tTurn Sum: " + randomTurnSum + " \tBank: " + randomBankedSum +
					" \tZilch count: " + randomZilchCount + "\n");			
			System.out.print("YOUR Stats: ");
			System.out.println("\t\tTurn Sum: " + humanTurnSum + " \tBank: " + bankedSum +
					" \tZilch count: " + zilchCount + "\n");
			
			
			// Essentially pauses output so a human can read it..
			if (!playRandom) {
				
				System.out.print("Press <enter> to roll: ");
				input = getChar();
				
				//Quit the trainer.
				if (input == 'q' || input == 'Q')
					break;
			}
			
			
			// Roll the dice and display the resulting values.
			dice = rollDice(numToRoll);
			if (playRandom) {
				randomRollCount++;
				System.out.print("Random ");
				displayRoll(randomRollCount, dice);
			} else {
				rollCount++;
				System.out.print("Your ");
				displayRoll(rollCount, dice);
			}
			
			
			// Score the roll and show the score and the free roll notice if obtained.
			throwSum = scoreThrow(dice);
			System.out.print(" " + throwSum + " points.");
			if (freeRoll || numToRoll == 0) {
				System.out.println(" You get a free roll!");
				freeRoll = false;
				numToRoll = 6;
			}
			
			
			// Check for a zilch.
			if (throwSum == 0) {
				System.out.print(" A zilch!!\n");
				if (playRandom)
					randomZilchCount++;
				else {
					zilchCount++;
					System.out.print("Press <enter> to continue: ");
					input = getChar();
					if (input == 'q' || input == 'Q')
						break;					
				}
				turnSum = 0;
				
				// Check zilch count.  If at three, penalize banked points and reset
				// zilch count.
				if (zilchCount == 3) {
					bankedSum -= 500;
					zilchCount = 0;
				}
				if (randomZilchCount == 3) {
					randomBankedSum -= 500;
					randomZilchCount = 0;
				}
				
			
				//Reset numToRoll and freeRoll;
				playRandom = !playRandom;
				numToRoll = 6;
				freeRoll = false;
			}
			
			else { //If roll is not a zilch...
				
				
				// Add throw score to turn sum and report.
				turnSum += throwSum;
				
				System.out.println("\nTurn Sum: " + turnSum + " and " + numToRoll + " dice left to roll.");
				
				
				// If turn sum is greater than 300 you can choose to bank the turn
				// sum or roll again.
				if (turnSum >= 300 && numToRoll > 0) {
					if (playRandom) {
						
						// The AI player chooses.
						if (numToRoll == 6)
							System.out.println("Rolling again.");
						else if (rollOneDice() > numToRoll) {
							
							// Bank turn sum.
							System.out.println("Random choice to bank.");
							randomZilchCount = 0;
							freeRoll = false;
							randomBankedSum += turnSum;
							turnSum = 0;
							numToRoll = 6;
							
							// Turn is over after banking.
							playRandom = !playRandom;
						}
						else
							System.out.println("Random choice to roll.");
					} else {
						
						// The human chooses.
						prompt = "Do you want to (r)oll or (b)ank your turn sum? ";
						input = playerPrompt(prompt);
						if (input == 'q' || input == 'Q')
							break;
						if (input == 'b' || input == 'B') {
							
							// Bank turn sum.
							zilchCount = 0;
							freeRoll = false;
							bankedSum += turnSum;
							turnSum = 0;
							numToRoll = 6;
							
							// Turn is over after banking.
							playRandom = !playRandom;
						}
					}
				}
				else if (turnSum < 300 && numToRoll > 0)
					System.out.println("Less than 300 still, you must roll again.");
			}
		}
		
		// Game is over.  Report results.
		System.out.println("\nYour score: " + bankedSum + " in " + rollCount + " rolls.");
		System.out.println("Random choices score: " + randomBankedSum + " in " + randomRollCount + " rolls.");
		if (bankedSum > randomBankedSum)
			System.out.println("You won! You are making good choices.");
		else
			System.out.println("You lost! What? You are not making good choices!");
	} // end runTrainer

	// Displays the instructions and starts the game.
	public static void main(String[] args) {
		displayIntro();
		runTrainer();
	} // end main

} // end ZilchTrainer

