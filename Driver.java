package main;

public class Driver {

  // example of constructing a Enigma machine through which encode plaintext to ciphertext. 
	public static void main(String[] args) {

    // construct two rotors
		Rotor rightRotor = new Rotor();
		
		Rotor leftRotor = new Rotor();
		
    // construct one reflector 
		Reflector reflector = new Reflector();
		
    // construct two identical Enigma machines, one for encoding and the other for decoding 
		Enigma encoder = new Enigma(rightRotor, leftRotor, reflector);
		Enigma decoder = new Enigma(encoder);
		
    // plaintext to be encoded
		char plaintext1 = 'A';
		char plaintext2 = 'B';
		char plaintext3 = 'C';
		
    // encode plaintext to ciphertext
		char cipher1 = encoder.getCiphertext(plaintext1);
		char cipher2 = encoder.getCiphertext(plaintext2);
		char cipher3 = encoder.getCiphertext(plaintext3);
		
    // decode the ciphertext to plaintext
		char translation1 = decoder.getCiphertext(cipher1);
		char translation2 = decoder.getCiphertext(cipher2);
		char translation3 = decoder.getCiphertext(cipher3);
 		
    // print cipher
		System.out.println(cipher1);
		System.out.println(cipher2);
		System.out.println(cipher3);
		
    // print plaintext
		System.out.println(translation1);
		System.out.println(translation2);
		System.out.println(translation3);

	}

}
