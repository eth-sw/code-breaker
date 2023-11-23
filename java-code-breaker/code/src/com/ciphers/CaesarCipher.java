package com.ciphers;

/**
 * This is the class for a Caesar Cipher
 * It allows for encryption of plain text
 * It allows for decryption of cipher text
 * Inherits from the Cipher parent class
 *
 * @author Ethan Swain
 * @version 3.0, 10th April 2023
 */
public class CaesarCipher extends Cipher {

    /**
     * Default constructor for the Caesar Cipher
     * Inherits default values for plaintext, ciphertext, key
     */
    public CaesarCipher() {
        super("unknown", "unknown", "unknown");
    }

    /**
     * This constructor inherits the plaintext, ciphertext and key from the Cipher class
     *
     * @param plaintext  Prepared plain text to be encrypted/been decrypted
     * @param ciphertext Cipher text to be decrypted/been encrypted
     * @param key        Key to encrypt/decrypt with
     */
    public CaesarCipher(String plaintext, String ciphertext, String key) {
        super(plaintext, ciphertext, key); // inherits the plain text
        setPlaintext(plaintext);
        setCiphertext(ciphertext);
        setKey(key);
    }

    /**
     * Called when encrypting plain text, passes the plain text and the key to the caesar cipher algorithm method
     *
     * @param cipher cipher Object passed into method - contains plain text, cipher text, and key
     * @return cipher text output after encryption
     */
    public String encryptCaesar(Cipher cipher) {
        String keyString = cipher.getKey();
        int key = Integer.parseInt(keyString);
        String cipherText = shiftText(cipher.getPlaintext(), key, true);
        setCiphertext(cipherText);
        return getCiphertext();
    }

    /**
     * Called when decrypting cipher text, passes the cipher text and the key to the caesar cipher algorithm method
     *
     * @param cipher cipher Object passed into method - contains plain text, cipher text, and key
     * @return plain text output after decryption
     */
    public String decryptCaesar(Cipher cipher) {
        String keyString = cipher.getKey();
        int key = Integer.parseInt(keyString);
        key = -key; // converts key to negative number
        String plainText = shiftText(cipher.getCiphertext(), key, true);
        setPlaintext(plainText);
        return getPlaintext();
    }

    /**
     * Iterates through each character in the text
     * Calls the shiftCharacter method for each character to shift it by specified key
     *
     * @param text    Either plain text or cipher text to be encrypted/decrypted
     * @param key     Numeric shift
     * @param encrypt Boolean - if true then algorithm encrypts, else algorithm decrypts
     * @return result of algorithm after encryption/decryption has been applied to text
     */
    private static String shiftText(String text, int key, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char outputChar = shiftCharacter(textChar, key, encrypt);
            result.append(outputChar);
        }
        return result.toString();
    }

    /**
     * Shifts the character by the integer stored in 'key' positions in the alphabet
     * Wraps around to start of alphabet if required
     *
     * @param textChar Individual character from plain text or cipher text being shifted
     * @param key      Numeric shift
     * @param encrypt  Boolean - if true then algorithm encrypts, else algorithm decrypts
     * @return result of algorithm after encryption/decryption has been applied to text
     */
    private static char shiftCharacter(char textChar, int key, boolean encrypt) {
        if (Character.isLetter(textChar)) {
            int alphabetSize = 26;
            int startChar = 'A';
            int shift = key % alphabetSize;
            if (!encrypt) {
                shift = -shift;
            }
            int shiftedChar = ((textChar - startChar + shift + alphabetSize) % alphabetSize) + startChar;
            return (char) shiftedChar;
        } else {
            return textChar;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CaesarCipher{");
        sb.append('}');
        return sb.toString();
    }
}
