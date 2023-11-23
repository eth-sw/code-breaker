package com.ciphers;

/**
 * This is the parent class for each Cipher
 * Provides common properties and methods for all ciphers
 *
 * @author Ethan Swain
 * @version 2.0, 10th April 2023
 */
public class Cipher {
    private String plaintext;
    private String ciphertext;
    private String key;

    /**
     * Constructor containing similar attributes for each cipher algorithm
     *
     * @param plaintext  Prepared plain text to be encrypted/been decrypted
     * @param ciphertext Cipher text to be decrypted/been encrypted
     * @param key        Key to encrypt/decrypt with
     */
    public Cipher(String plaintext, String ciphertext, String key) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
        this.key = key;
    }

    /**
     * Getter for plaintext
     *
     * @return plaintext
     */
    public String getPlaintext() {
        return plaintext;
    }

    /**
     * Setter for plaintext
     *
     * @param plaintext Plain text used in cipher algorithm
     */
    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    /**
     * Getter for ciphertext
     *
     * @return ciphertext
     */
    public String getCiphertext() {
        return ciphertext;
    }

    /**
     * Setter for ciphertext
     *
     * @param ciphertext Cipher text used in cipher algorithm
     */
    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    /**
     * Getter for key
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Setter for key
     *
     * @param key Key used in cipher algorithm
     */
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cipher{");
        sb.append("plaintext='").append(plaintext).append('\'');
        sb.append(", ciphertext='").append(ciphertext).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
