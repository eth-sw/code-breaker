package com.ciphers;

/**
 * This is the class for a Vigenere Cipher
 * It allows for encryption of plain text
 * It allows for decryption of cipher text
 * Inherits from the Cipher parent class
 *
 * @author Ethan Swain
 * @version 4.0, 10th April 2023
 */
public class VigenereCipher extends Cipher {

    /**
     * Default constructor for the Vigenere Cipher
     */
    public VigenereCipher() {
        super("unknown", "unknown", "unknown");
    }

    /**
     * This constructor inherits the plaintext, ciphertext and key from the Cipher class
     *
     * @param plaintext  Prepared plain text to be encrypted/been decrypted
     * @param ciphertext Cipher text to be decrypted/been encrypted
     * @param key        Key to encrypt/decrypt with
     */
    public VigenereCipher(String plaintext, String ciphertext, String key) {
        super(plaintext, ciphertext, key);
        setPlaintext(plaintext);
        setCiphertext(ciphertext);
        setKey(key);
    }

    /**
     * Called when encrypting plain text, passes the plain text and the key to the vigenere cipher algorithm method
     *
     * @param cipher cipher Object passed into method - contains plain text, cipher text, and key
     * @return cipher text output after encryption
     */
    public String encryptVigenere(Cipher cipher) {
        return vigenereAlgorithm(cipher, true);
    }

    /**
     * Called when decrypting cipher text, passes the cipher text and the key to the vigenere cipher algorithm method
     *
     * @param cipher cipher Object passed into method - contains plain text, cipher text, and key
     * @return plain text output after decryption
     */
    public String decryptVigenere(Cipher cipher) {
        return vigenereAlgorithm(cipher, false);
    }

    /**
     * Algorithm for vigenere cipher encryption and decryption
     *
     * @param cipher  cipher Object passed into method - contains plain text, cipher text, and key
     * @param encrypt Boolean - if true then algorithm encrypts, else algorithm decrypts
     * @return result of algorithm after encryption/decryption has been applied to text
     */
    private String vigenereAlgorithm(Cipher cipher, boolean encrypt) {
        String text;
        if (encrypt) { // specific to encryption
            text = cipher.getPlaintext();
        } else { // specific to decryption
            text = cipher.getCiphertext();
        }
        String keyword = cipher.getKey();
        String result;

        // ERROR CHECKING
        if (keyword == null || keyword.isEmpty()) {
            throw new IllegalArgumentException("Error occurred. Keyword cannot be empty.");
        }
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Error occurred. Text cannot be empty.");
        }

        keyword = keyword.toUpperCase();
        text = text.toUpperCase();
        int keywordLength = keyword.length();
        int textLength = text.length();
        char[] output = new char[textLength];
        int j = 0;

        for (int i = 0; i < textLength; i++) {
            char inputChar = text.charAt(i);

            if (!Character.isLetter(inputChar)) {
                output[i] = inputChar;
                continue;
            }

            int keywordCharIndex = j % keywordLength;
            char keywordChar = keyword.charAt(keywordCharIndex);
            int shiftAmount = (int) keywordChar - 65;
            char outputChar;
            if (encrypt) { // specific to encryption
                outputChar = (char) ((inputChar + shiftAmount - 65) % 26 + 65);
            } else { // specific to decryption
                outputChar = (char) ((inputChar - shiftAmount - 65 + 26) % 26 + 65);
            }
            output[i] = outputChar;
            j++;
        }

        result = new String(output);

        if (encrypt) { // specific to encryption
            setCiphertext(result);
            return getCiphertext();
        } else { // specific to decryption
            setPlaintext(result);
            return getPlaintext();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VigenereCipher{");
        sb.append('}');
        return sb.toString();
    }
}
