package com.ciphers;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the class for a Keyed Caesar com.ciphers.Cipher
 * It allows for encryption of plain text
 * It allows for decryption of cipher text
 * Inherits from the com.ciphers.Cipher parent class
 *
 * @author Ethan Swain
 * @version 5.0, 9th April 2023
 */
public class KeyedCaesarCipher extends Cipher {

    /**
     * Default constructor for the Keyed Caesar com.ciphers.Cipher
     */
    public KeyedCaesarCipher() {
        super("unknown", "unknown", "unknown");
    }

    /**
     * This constructor inherits the plaintext, ciphertext and key from the com.ciphers.Cipher class
     *
     * @param plaintext  Prepared plain text to be encrypted/been decrypted
     * @param ciphertext com.ciphers.Cipher text to be decrypted/been encrypted
     * @param key        Key to encrypt/decrypt with
     */
    public KeyedCaesarCipher(String plaintext, String ciphertext, String key) {
        super(plaintext, ciphertext, key);
        setPlaintext(plaintext);
        setCiphertext(ciphertext);
        setKey(key);
    }


    /**
     * Called when encrypting plain text, passes the plain text and the key to the keyed caesar cipher algorithm method
     *
     * @param cipher cipher Object passed into method - contains plain text, cipher text, and key
     * @return cipher text output after encryption
     */
    public String encryptKeyed(Cipher cipher) {
        String plaintext = cipher.getPlaintext();
        String key = cipher.getKey();
        String ciphertext = keyedAlgorithm(plaintext, key, true);
        setCiphertext(ciphertext);
        return getCiphertext();
    }

    /**
     * Called when decrypting cipher text, passes the cipher text and the key to the keyed caesar cipher algorithm method
     *
     * @param cipher cipher Object passed into method - contains plain text, cipher text, and key
     * @return plain text output after decryption
     */
    public String decryptKeyed(Cipher cipher) {
        String ciphertext = cipher.getCiphertext();
        String key = cipher.getKey();
        String plaintext = keyedAlgorithm(ciphertext, key, false);
        setPlaintext(plaintext);
        return getPlaintext();
    }

    /**
     * Algorithm for keyed caesar cipher encryption and decryption
     * Contains error checking and exception handling
     *
     * @param text    Either plain text or cipher text to be encrypted/decrypted
     * @param key     Numeric/Textual shift
     * @param encrypt Boolean - if true then algorithm encrypts, else algorithm decrypts
     * @return result of algorithm after encryption/decryption has been applied to text
     */
    private String keyedAlgorithm(String text, String key, boolean encrypt) {
        // splits key into integer and string
        int shift = Integer.parseInt(key.replaceAll("[^0-9].*", ""));
        String keyword = key.substring(String.valueOf(shift).length());
        // ERROR CHECKING STATEMENTS
        if (keyword == null || keyword.isEmpty()) {
            throw new IllegalArgumentException("Error occurred, keyword is empty");
        }
        if (shift < 0) {
            throw new IllegalArgumentException("Error occurred, numeric shift is negative");
        }
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Error occurred, text is empty");
        }

        String uniqueLetters = removeDuplicates(keyword); // removes all duplicates from the textual part of the string
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // creates alphabet A-Z
        String keyedAlphabet = uniqueLetters + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".replaceAll("[" + uniqueLetters + "]", ""); // creates a keyed alphabet by combining the unique letters and the remaining letters in the alphabet

        shift = shift % keyedAlphabet.length(); // adjusts shift value to account for length of keyed alphabet

        String shiftedKeyedAlphabet = keyedAlphabet.substring(shift) + keyedAlphabet.substring(0, shift);  // creates a new keyed alphabet by shifting the original keyed alphabet by shift value

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i); // retrieves character at index to be encrypted or decrypted
            if (Character.isLetter(character)) { // error checking, ensures character is a letter before shifting
                int position;
                char substitution;
                if (encrypt) { // specific to encryption
                    position = alphabet.indexOf(Character.toUpperCase(character)); // finds position of character in alphabet
                    substitution = shiftedKeyedAlphabet.charAt(position); // finds substitution character from shifted keyed alphabet
                } else { // specific to decryption
                    position = shiftedKeyedAlphabet.indexOf(Character.toUpperCase(character)); // finds position of character in shifted keyed alphabet
                    substitution = alphabet.charAt(position); // finds substitution character from alphabet
                }
                char resultChar;
                if (Character.isUpperCase(character)) { // specific to encryption
                    resultChar = Character.toUpperCase(substitution);
                } else { // specific to decryption
                    resultChar = substitution;
                }
                result.append(resultChar); // using StringBuilder
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    /**
     * Removes any duplicate characters from textual key so that only the first occurrence is used
     *
     * @param str Keyword from key
     * @return result after duplicates have been removed
     */
    private static String removeDuplicates(String str) {
        StringBuilder result = new StringBuilder();
        Set<Character> seen = new HashSet<>(); // HashSet knows which letters have already been seen
        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);
            if (!seen.contains(Character.toUpperCase(character)) && Character.isLetter(character)) { // checks if character is a letter, and hasn't been seen before
                result.append(Character.toUpperCase(character)); // append the result stringbuilder and add to hashset of seen characters
                seen.add(Character.toUpperCase(character));
            }
        }
        return result.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("com.ciphers.KeyedCaesarCipher{");
        sb.append('}');
        return sb.toString();
    }
}
