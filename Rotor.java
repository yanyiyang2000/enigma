package main;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representation of a rotor that comprises an Enigma machine.
 * 
 * @author Yiyang Yan
 */
public class Rotor {
	
	/**
	 * <p>An {@link ArrayList} of 26 distinct {@link Integer} ranging from 0 to 25. It dictates the substitution cipher 
	 * of each English letter in this {@code Rotor}.</p>
	 * 
	 * <p>The indices of the {@code ArrayList} represent 26 English letters alphabetically.  For example, index 0 
	 * represents letter A.</p>
	 * 
	 * <p>Each {@code Integer} of the {@code ArrayList} represents the substitution cipher of the letter represented by 
	 * the {@code Integer}'s index.  For example, element of index 0 is 2 indicates that C is the substitution cipher of 
	 * A.<p>
	 */
	private ArrayList<Integer> wiring;
	
	/**
	 * The number of 1/26 of a full rotation this {@code Rotor} has finished.
	 */
	private int totalSteps;
	
	/**
	 * Constructs a {@code Rotor} instance with the specified wiring.
	 * 
	 * @param wiring the wiring of the {@code Rotor}
	 */
	public Rotor(ArrayList<Integer> wiring) {
		this.wiring = new ArrayList<>(wiring); // prevents illegal mutation
		totalSteps = 0;
	}
	
	/**
	 * Constructs a {@code Rotor} instance with a randomly-generated wiring.
	 */
	public Rotor() {
		wiring = generateWiring();
		totalSteps = 0;
	}
	
	/**
	 * Constructs a {@code Rotor} instance that has the same instance variables as the {@code Rotor} passed in the 
	 * argument.
	 * 
	 * @param rotor rotor to be copied
	 */
	public Rotor(Rotor rotor) {
		wiring = new ArrayList<>(rotor.getWiring());
		totalSteps = rotor.getTotalSteps();
	}
	
	/**
	 * Returns a deep copy of the wiring of this {@code Rotor}.
	 * 
	 * @return a deep copy of the wiring of this {@code Rotor}
	 */
	public ArrayList<Integer> getWiring() {
		return new ArrayList<>(wiring); // prevents illegal mutation
	}
	
	/**
	 * Returns the total steps of this {@code Rotor}.
	 * 
	 * @return the total steps of this {@code Rotor}
	 */
	public int getTotalSteps() {
		return totalSteps;
	}
	
	/**
	 * Sets the wiring of this {@code Rotor}.
	 * 
	 * @param newWiring wiring to be set
	 */
	public void setWiring(ArrayList<Integer> newWiring) {
		this.wiring = new ArrayList<>(newWiring); // prevents illegal mutation
	}
	
	/**
	 * Sets the total steps of this {@code Rotor}.
	 * 
	 * @param newTotalSteps total steps to be set
	 */
	public void setTotalSteps(int newTotalSteps) {
		this.totalSteps = newTotalSteps;
	}

	/**
	 * Yields an {@code int} representation of substitution cipher for the {@code Rotor} on the left based on the 
	 * wiring of this {@code Rotor}.
	 * 
	 * @param input {@code Integer} representation of the cipher encrypted by the {@code Rotor} on the left
	 * @return {@code int} representation of substitution cipher of the input cipher
	 */
	public int yieldInputForLeftRotor(Integer input) {
		return wiring.get(input);
	}
	
	/**
	 * Yields an {@code int} representation of substitution cipher for the {@link Reflector} based on the wiring of 
	 * this {@code Rotor}.
	 * 
	 * @param input {@code Integer} representation of the cipher encrypted by the {@code Rotor} on the right
	 * @return {@code int} representation of substitution cipher of the input cipher
	 */
	public int yieldInputForReflector(Integer input) {
		return wiring.get(input);
	}

	/**
	 * Yields an {@code int} representation of substitution cipher for the {@code Rotor} on the right based on the 
	 * wiring of this {@code Rotor}.
	 * 
	 * @param input {@code Integer} representation of the cipher encrypted by the {@code Rotor} or the {@code Reflector} 
	 *        on the left
	 * @return {@code int} representation of the substitution cipher
	 */
	public int yieldInputForRightRotor(Integer input) {
		return wiring.indexOf(input);
	}

	/**
	 * Rotates this {@code Rotor} by 1/26 of a full rotation.
	 */
	public void steps() {
		// 1. Stores the first element and removes it from the list
		// 2. Adds the previously removed element to the end of the list
		// 3. For each value i in the list, i = (i + 25) % 26
		wiring.add(wiring.get(0));
		wiring.remove(0);
		for (Integer i : wiring) {
			i = (i + 25) % 26;
		}

		totalSteps++;
	}
	
	/**
	 * Adjusts the letter displayed in the window of this {@code Rotor} to the desired letter.
	 * 
	 * @param initChar letter to be displayed in the window of this {@code Rotor}
	 */
	public void adjustInitChar(char initChar) {
		if (initChar == 'A') { // A is the default letter displaying in the window so nothing will happen
			return;
		} else {
			// Converts the letter to a number ranging from 0 - 25 to represent A - Z.
			int numRep = initChar - 'A'; 
			
			// Calculates how many times the rotor needs to step to have the desired letter displayed in the window.
			int numSteps = wiring.indexOf(numRep);
			
			for (int i = 0; i <= numSteps; i++) {
				steps();
			}
			
			totalSteps = 0;
		}
	}
	
	/**
	 * Generates a random wiring.
	 * 
	 * @return a random wiring
	 */
	private static ArrayList<Integer> generateWiring() {
		ArrayList<Integer> wiring = new ArrayList<>();
		Random random = new Random();
		Integer sub;
		
		// The list contains 26 values that satisfy following:
		// 1. The value is not numerically equal to its index.
		// 2. Each value is a distinct integer ranging from 0 to 25 representing letter A to Z.
		for (int i = 0; i < 26; i++) {
			do {
				sub = random.nextInt(26);
			} while (wiring.contains(sub) || sub == wiring.size());
			
			wiring.add(sub);
		}
		return wiring;
	}
	
	/**
	 * Compares the specific object with this {@code Rotor} for equality.  Returns true if and only if the argument 
	 * is not null and is a {@code Rotor} object and all instance variables in the two {@code Rotors} are equal.
	 * 
	 * @return true the specific object is equal to this {@code Rotor} and false otherwise
	 */
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof Rotor) {
			Rotor anotherRotor = (Rotor)anObject;
			if (anotherRotor.getWiring().equals(wiring) &&
				anotherRotor.getTotalSteps() == totalSteps) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a string representation of this {@code Rotor}.  The string representation consists of all the instance 
	 * variables of the {@code Rotor}. The instance variables are converted to string as by 
	 * {@link String#valueOf(Object)}.
	 */
	public String toString() {
		return "Wiring: " + wiring.toString() + "\n" +
			   "Total steps: " + totalSteps;
	}

}
