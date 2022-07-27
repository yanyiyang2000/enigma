package main;

/**
 * <p>Representation of a classical Enigma machine that consists of a right rotor, a left rotor and a reflector.</p>
 * 
 * <p>The data flow is as follows: </p>
 * 
 * <p>plaintext &mdash;&gt; right rotor &mdash;&gt; left rotor &mdash;&gt; reflector &mdash;&gt; left rotor &mdash;&gt; 
 *    right rotor &mdash;&gt; ciphertext</p>
 * 
 * @author Yiyang Yan
 */
public class Enigma {
	
	private Rotor rightRotor;
	private Rotor leftRotor;
	private Reflector reflector;

	/**
	 * Constructs an {@code Enigma} instance with the specified {@link Rotor} and {@link Reflector} instances.
	 * 
	 * @param rightRotor the right {@code Rotor}
	 * @param leftRotor the left {@code Rotor}
	 * @param reflector the {@code Reflector}
	 */
	public Enigma(Rotor rightRotor, Rotor leftRotor, Reflector reflector) {
		this.rightRotor = rightRotor;
		this.leftRotor = leftRotor;
		this.reflector = reflector;
	}
	
	/**
	 * <p>Constructs an {@code Enigma} instance with the specified {@code Rotor} and {@code Reflector} instances, and 
	 * the specified initial position of the {@code Rotor}.</p>
	 * 
	 * <p>Parameter {@code initChar} is an array of 2 {@code char} with the first and second entries representing the 
	 * letters displayed in the window of the right and left {@code Rotor} respectively.  For example, array {B, E} 
	 * indicates that the letter in the window of right and left rotor is B and E respectively.  {@code initChar} 
	 * only accepts capital English letters.
	 * 
	 * <p>If you wish any of the letter displayed in the window of the {@code Rotor} unchanged, set the value of 
	 * respective index of the {@code initChar} to A.</p>
	 * 
	 * @param rightRotor the right {@code Rotor}
	 * @param leftRotor the left {@code Rotor}
	 * @param reflector the {@code Reflector}
	 * @param initChar an array of {@code char} representing letters displaying in the window of the right and left 
	 *        {@code Rotor}
	 */
	public Enigma(Rotor rightRotor, Rotor leftRotor, Reflector reflector, char[] initChar) {
		this.rightRotor = rightRotor;
		this.leftRotor = leftRotor;
		this.reflector = reflector;
		
		rightRotor.adjustInitChar(initChar[0]);
		leftRotor.adjustInitChar(initChar[1]);
	}
	
	/**
	 * Constructs an {@code Enigma} instance that has the same parameters as the {@code Enigma} passed in the argument.
	 * 
	 * @param enigma enigma to be copied
	 */
	public Enigma(Enigma enigma) {
		rightRotor = new Rotor(enigma.getRightRotor());
		leftRotor = new Rotor(enigma.getLeftRotor());
		reflector = new Reflector(enigma.getReflector());
	}
	
	/**
	 * Returns a deep copy of the right rotor of this {@code Enigma}. 
	 * 
	 * @return a deep copy of the right rotor of this {@code Enigma}
	 */
	public Rotor getRightRotor() {
		return new Rotor(rightRotor);
	}
	
	/**
	 * Returns a deep copy of the left rotor of this {@code Enigma}.
	 * 
	 * @return a deep copy of the left rotor of this {@code Enigma}
	 */
	public Rotor getLeftRotor() {
		return new Rotor(leftRotor);
	}
	
	/**
	 * Returns a deep copy of the reflector of this {@code Enigma}.
	 * 
	 * @return a deep copy of the reflector of this {@code Enigma}
	 */
	public Reflector getReflector() {
		return new Reflector(reflector);
	}
	
	/**
	 * <p>Simulates the process of the encryption of a letter (plaintext) to a different letter. (ciphertext)</p>
	 * 
	 * <p>The argument only accepts one capital letter upon each invocation.</p>
	 * 
	 * @param plaintext the plaintext to be encrypted
	 * @return ciphertext
	 */
	public char getCiphertext(char plaintext) {
		/*
		 * Converts the plaintext (capital letter) to a number
		 * A -> 0
		 * B -> 1
		 * C -> 2
		 * ...
		 * Z -> 25
		 */
		int input = plaintext - 'A';
		
		// Right rotor encodes the input to a cipher
		int cipher = rightRotor.yieldInputForLeftRotor(input);
		
		// Left rotor encodes the cipher from the rotor on its right to a substitution cipher 
		int subCipher1 = leftRotor.yieldInputForReflector(cipher);
		
		// Reflector encodes the substitution cipher input from the rotor to another substitution cipher
		int subCipher2 = reflector.reflects(subCipher1); 
		
		// Left rotor encodes substitution cipher from the reflector to another substitution cipher
		int subCipher3 = leftRotor.yieldInputForRightRotor(subCipher2);
		
		 // Right rotor encodes the substitution cipher from the rotor on its left and yields the ciphertext
		int output = rightRotor.yieldInputForRightRotor(subCipher3); 
		
		rightRotor.steps();
		if (rightRotor.getTotalSteps() == 26) { // right rotor has finished a full rotation
			leftRotor.steps();
		}
		
		// Converts the number back to char
		char ciphertext = (char) (output + 'A');
		return ciphertext;
	}
	
	/**
	 * Compares the specific object with this {@code Enigma} for equality.  Returns true if and only if the argument 
	 * is not null and is a {@code Enigma} object and all the instance variables in the two {@code Enigmas} are equal.
	 * 
	 * @return true if the specific object is equal to this {@code Enigma} and false otherwise
	 */
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof Enigma) {
			Enigma anotherEnigma = (Enigma)anObject;
			if (anotherEnigma.getRightRotor().equals(rightRotor) && 
				anotherEnigma.getLeftRotor().equals(leftRotor) && 
				anotherEnigma.getReflector().equals(reflector)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a string representation of this {@code Enigma}.  The string representation consists of all the instance 
	 * variables of the {@code Enigma}.  The instance variables are converted to string by 
	 * {@link String#valueOf(Object)}.
	 */
	public String toString() {
		return "Right rotor: " + rightRotor.toString() + "\n" +
			   "Left rotor: " + leftRotor.toString() + "\n" +
			   "Reflector: " + reflector.toString();
	}

}
