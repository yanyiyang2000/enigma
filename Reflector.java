package main;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representation of a reflector that comprises an Enigma machine.
 * 
 * @author Yiyang Yan
 */
public class Reflector {
	
	/**
	 * <p>An {@link ArrayList} of 26 distinct {@link Integer} ranging from 0 to 25. It dictates the substitution cipher 
	 * of each English letter in this {@code Reflector}.</p>
	 * 
	 * <p>The indices of the {@code ArrayList} represent 26 English letters alphabetically.  For example, index 0 
	 * represents letter A.</p>
	 * 
	 * <p>Each {@code Integer} in the {@code ArrayList} represents the substitution cipher of the letter represented by 
	 * the {@code Integer}'s index.  For example, element of index 0 is 2 indicates that C is the substitution cipher of 
	 * A.<p>
	 */
	private ArrayList<Integer> wiring;

	/**
	 * Constructs a {@code Reflector} instance with the specified wiring.
	 * 
	 * @param wiring the wiring of the {@code Reflector}
	 */
	public Reflector(ArrayList<Integer> wiring) {
		this.wiring = new ArrayList<>(wiring); // prevents illegal mutation
	}
	
	public Reflector() {
		wiring = generateWiring();
	}
	
	/**
	 * Constructs a {@code Reflector} instance that has the same instance variables as the {@code Reflector} passed in 
	 * the argument.
	 * 
	 * @param reflector reflector to be copied
	 */
	public Reflector(Reflector reflector) {
		wiring = new ArrayList<Integer>(reflector.getWiring());
	}
	
	/**
	 * Returns a deep copy of the wiring of this {@code Reflector}.
	 * 
	 * @return a deep copy of the wiring of this {@code Reflector}
	 */
	public ArrayList<Integer> getWiring() {
		return new ArrayList<>(wiring);
	}

	/**
	 * Yields an {@code int} representation of substitution cipher for the {@link Rotor} on the right based on the 
	 * wiring of this {@code Reflector}.
	 * 
	 * @param input {@code Integer} representation of the cipher encrypted by the {@code Rotor} on the right
	 * @return {@code int} representation of substitution cipher of the input cipher
	 */
	public int reflects(Integer input) {
		return wiring.get(input);
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
		
		// The list contains 13 pairs of values that satisfy:
		// 1. Values in each pair add up to 25.
		// 2. Each pair has a value indexed less than 13, and a value indexed greater than or equal to 13.
	    // 3. Each value is a distinct value ranging from 0 to 25 representing letter A to Z.
		for (int i = 0; i < 13; i++) {
			do {
				sub = random.nextInt(13) + 13;
			} while (wiring.contains(sub) || sub == wiring.size());
			
			wiring.add(sub);
		}
		
		for (int i = 13; i < 26; i++) {
			wiring.add(wiring.indexOf(i)); // auto-cast int to Integer
		}
		return wiring;
	}
	
	/**
	 * Compares the specific object with this {@code Reflector} for equality.  Returns true if and only if the argument 
	 * is not null and is a {@code Reflector} object and all instance variables in the two {@code Reflectors} are equal.
	 * 
	 * @return true the specific object is equal to this {@code Reflector} and false otherwise
	 */
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof Reflector) {
			Reflector anotherReflector = (Reflector)anObject;
			if (anotherReflector.getWiring().equals(wiring)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a string representation of this {@code Reflector}.  The string representation consists of the instance 
	 * variable of the {@code Reflector}.  The instance variables are converted to string as by 
	 * {@link String#valueOf(Object)}.
	 */
	public String toString() {
		return "Wiring: " + wiring.toString();
	}

}
