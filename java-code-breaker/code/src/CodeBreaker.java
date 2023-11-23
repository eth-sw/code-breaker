import com.ciphers.CaesarCipher;
import com.ciphers.Cipher;
import com.ciphers.KeyedCaesarCipher;
import com.ciphers.VigenereCipher;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * The CodeBreaker class which we run from.
 * Contains all menu options.
 *
 * @author Ethan Swain
 * @version 2.0, 9th April 2023
 */
public class CodeBreaker {
    private static final String caesarKeyFile = "caesar-key.txt";
    private static final String keyedCaesarKeyFile = "keyed-caesar-key.txt";
    private static final String vigenereKeyFile = "vigenere-key.txt";
    private static final String prepPlainTextFile = "prep.txt";
    private Scanner scan;
    private String key;
    private String currentCipher;
    private String prepPlainText;
    private String cipherText;

    /**
     * Initialise CodeBreaker with the current cipher
     * default set to Caesar Cipher
     */
    private CodeBreaker() {
        currentCipher = "Caesar";
    }

    /**
     * Runs when code starts
     * Loads key for the current cipher selected
     */
    private void initialise() {
        loadKeys();
    }

    /**
     * User-selection menu
     * Contains all functional requirements
     */
    private void menu() {
        String response;
        do {
            System.out.println("Choose an option: ");
            printMenu(); // outputs text menu for increased usability
            scan = new Scanner(System.in);
            response = scan.nextLine();
            switch (response) {
                case "1":
                    caesarCipher(); // sets current cipher to caesar cipher
                    break;
                case "2":
                    keyedCaesarCipher(); // sets current cipher to keyed caesar cipher
                    break;
                case "3":
                    vigenereCipher(); // sets current cipher to vigenere cipher
                    break;
                case "4":
                    editKey(); // allows user to edit the key
                    break;
                case "5":
                    displayKey(); // displays the current key, different depending on current cipher
                    break;
                case "6":
                    inputPlainFile(); // allows user to enter a plain text file of choice
                    break;
                case "7":
                    inputCipherFile(); // allows user to enter a cipher text file of choice
                    break;
                case "8":
                    encrypt(); // encrypts the prepared plain text in caesar cipher text
                    break;
                case "9":
                    displayCipher(); // displays the caesar cipher text
                    break;
                case "10":
                    try {
                        saveCipher(); // passes the user entered cipher text file
                    } catch (IOException e) {
                        throw new RuntimeException(e); // else throws runtime exception
                    }
                    break;
                case "11":
                    decrypt(); // decrypts the caesar cipher text into prepared plain text
                    break;
                case "12":
                    displayPlain(); // displays the prepared plain text
                    break;
                case "13":
                    try {
                        savePlain(); // passes the prepared plain text file to be saved to
                    } catch (IOException e) {
                        throw new RuntimeException(e); // else throws runtime exception
                    }
                    break;
                case "q":
                    saveKeys();
                    System.exit(0); // exits program
                    break;
                default:
                    System.err.println("Error occurred. Please enter an option from the list.");
            }
        } while (!response.equals("Q"));
    }

    /**
     * Outputs the menu options to the user
     * Includes the output of the current cipher
     */
    private void printMenu() {
        System.out.println("Current cipher: " + currentCipher);
        System.out.println("1 - Use Caesar Cipher");
        System.out.println("2 - Use Keyed Caesar Cipher");
        System.out.println("3 - Use Vigenere Cipher");
        System.out.println("4 - Edit Key");
        System.out.println("5 - Display Key");
        System.out.println("6 - Input Plain Text File");
        System.out.println("7 - Input Cipher Text File");
        System.out.println("8 - Encrypt");
        System.out.println("9 - Display Cipher Text");
        System.out.println("10 - Save Cipher Text");
        System.out.println("11 - Decrypt");
        System.out.println("12 - Display Plain Text");
        System.out.println("13 - Save Plain Text");
        System.out.println("Q - Quit Program");
    }

    /**
     * Updates the current cipher to Caesar Cipher
     * Reloads the key
     */
    private void caesarCipher() {
        currentCipher = "Caesar";
        loadKeys(); // loads caesar-key.txt
    }

    /**
     * Updates the current cipher to Keyed Caesar Cipher
     * Reloads the key
     */
    private void keyedCaesarCipher() {
        currentCipher = "Keyed Caesar";
        loadKeys(); // loads keyed-caesar-key.txt
    }

    /**
     * Updates the current cipher to the Vigenere Cipher
     * Reloads the key
     */
    private void vigenereCipher() {
        currentCipher = "Vigenere";
        loadKeys(); // loads vigenere-key.txt
    }

    /**
     * Allows the user to update the key for the current selected cipher
     * Contains error checking so that only keys of the correct format can be input
     * Caesar can only take in an integer
     * Keyed Caesar can only take in an integer (key) followed by a string (keyword)
     * Vigenere can only take in a string
     */
    private void editKey() {
        System.out.println("\nEnter key: ");
        switch (currentCipher) {
            case "Caesar":
                int keyInt;
                boolean isValidInput = false;
                do {
                    System.out.println("Enter the key (an integer greater than or equal to 0): ");
                    while (!scan.hasNextInt()) { // checks to ensure user has entered an integer
                        System.err.println("Error occurred. The key must be an integer. Please try again.");
                        scan.next();
                    }
                    keyInt = scan.nextInt();
                    System.out.println();
                    if (keyInt < 0) {
                        System.err.println("Error occurred. The key must be greater than or equal to 0. Please try again.");
                    } else {
                        isValidInput = true;
                    }
                } while (!isValidInput);
                key = Integer.toString(keyInt);
                break;
            case "Keyed Caesar":
                while (true) {
                    String keyedCaesarInput = scan.nextLine();
                    if (keyedCaesarInput.matches("\\d+[a-zA-Z]+")) { // checks to ensure user has entered integer followed by string
                        key = keyedCaesarInput;
                        System.out.println();
                        break;
                    } else {
                        System.err.println("Error occurred. The key must be in the format of an integer followed by a string (e.g. 1TEST). Please try again.");
                    }
                }
                break;
            case "Vigenere":
                while (!scan.hasNext("[a-zA-Z]+")) { // checks to ensure user has entered a string
                    System.err.println("Error occurred. The key must be a string of letters. Please try again.");
                    scan.next();
                }
                key = scan.next();
                System.out.println();
                break;
            default:
                System.err.println("Error occurred. Invalid cipher type.");
                System.out.println();
        }
        saveKeys();
    }

    /**
     * Outputs the current key for the selected cipher
     */
    private void displayKey() {
        if (key == null || key.isEmpty()) { // checks to ensure there is data in key file
            System.err.println("\nError occurred. No key data to display.");
        } else {

            System.out.println("\nCurrent key: " + key); // displays the current key
            System.out.println();
        }
    }

    /**
     * Checks the current selected cipher and loads the key relevant to that cipher
     */
    private void loadKeys() {
        switch (currentCipher) {
            case "Caesar":
                key = loadKeyFromFile(caesarKeyFile); // passes caesar-key.txt
                break;
            case "Keyed Caesar":
                key = loadKeyFromFile(keyedCaesarKeyFile); // passes keyed-caesar-key.txt
                break;
            case "Vigenere":
                key = loadKeyFromFile(vigenereKeyFile); // passes vigenere-key.txt
                break;
            default:
                System.err.println("\nError occurred. Incorrect cipher type.");
                System.out.println();
        }
    }

    /**
     * This method saves the current contents of the key, to the corresponding key file
     * based on the current chosen cipher
     */
    private void saveKeys() {
        try {
            switch (currentCipher) {
                case "Caesar":
                    Files.write(Paths.get(caesarKeyFile), key.getBytes());
                    break;
                case "Keyed Caesar":
                    Files.write(Paths.get(keyedCaesarKeyFile), key.getBytes());
                    break;
                case "Vigenere":
                    Files.write(Paths.get(vigenereKeyFile), key.getBytes());
                    break;
                default:
                    System.err.println("\nError occurred. Incorrect cipher type.");
                    System.out.println();
            }
        } catch (IOException e) {
            System.err.println("\nError occurred. Could not save keys.");
            System.out.println();
        }
    }

    /**
     * Called from the loadKeys() method
     * Loads the key from the specific file containing key data for each cipher
     * Contains error checking to ensure file exists
     *
     * @param filename Name of file key is being loaded from
     * @return
     */
    private String loadKeyFromFile(String filename) {
        try (Scanner input = new Scanner(new File(filename))) { // reads the first line of the key file, returns it as string
            return input.nextLine();
        } catch (FileNotFoundException e) { // else throw FileNotFoundException
            System.err.println("\nError occurred. File not found.");
            System.out.println();
            return "";
        }
    }

    /**
     * Inputs the Plain Text file prep.txt
     * Converts all text to uppercase
     */
    private void inputPlainFile() {
        System.out.println("\nEnter file name for plain text: ");
        String plainTextFile = scan.nextLine(); // reads user input
        System.out.println();
        try {
            prepPlainText = new String(Files.readAllBytes(Paths.get(plainTextFile)));
            prepPlainText = prepPlainText.replaceAll("[^a-zA-Z\\s]", ""); // removes all punctuation from plain text
            prepPlainText = prepPlainText.toUpperCase(); // converts all plain text to uppercase
        } catch (IOException e) { // else throws IOException
            System.err.println("Error occurred. Cannot read plain text file.");
            prepPlainText = "";
            System.out.println();
        }
        try {
            FileWriter writer = new FileWriter(prepPlainTextFile);
            writer.write(prepPlainText);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error occurred. Cannot write to prepared plain text file.");
            System.out.println();
        }
    }

    /**
     * Allows user to input file name of stored cipher text
     * Reads all data inside file
     */
    private void inputCipherFile() {
        System.out.println("\nEnter file name for cipher text: ");
        String cipherTextFile = scan.nextLine();
        System.out.println();
        try {
            cipherText = new String(Files.readAllBytes(Paths.get(cipherTextFile)));
            cipherText = cipherText.replaceAll("[^a-zA-Z\\s]", ""); // removes all punctuation from plain text
            cipherText = cipherText.toUpperCase(); // converts all plain text to uppercase
        } catch (IOException e) { // else throws IOException
            System.err.println("Error occurred. Cannot read cipher text file.");
            System.out.println();
            cipherText = "";
        }
    }

    /**
     * Encrypts the data inside the plain text file using the current selected cipher
     * Calls the class of the relevant cipher
     */
    private void encrypt() {
        Cipher cipher = new Cipher(prepPlainText, cipherText, key);
        switch (currentCipher) {
            case "Caesar":
                CaesarCipher cCipher = new CaesarCipher("", "", "");
                cipherText = cCipher.encryptCaesar(cipher); // passes cipher object to the encryption method in the caesar cipher class
                break;
            case "Keyed Caesar":
                KeyedCaesarCipher kCipher = new KeyedCaesarCipher("", "", "");
                cipherText = kCipher.encryptKeyed(cipher); // passes cipher object to the encryption method in the keyed caesar cipher class
                break;
            case "Vigenere":
                VigenereCipher vCipher = new VigenereCipher("", "", "");
                cipherText = vCipher.encryptVigenere(cipher); // passes cipher object to the encryption method in the vigenere cipher class
                break;
            default:
                System.err.println("\nError occurred. Incorrect cipher type.");
                System.out.println();
        }
    }

    /**
     * Displays the current cipher text
     * Contains error checking - if there is no cipher text to display, then an error will be output
     */
    private void displayCipher() {
        if (cipherText == null || cipherText.isEmpty()) { // ensures there is cipher text data to display
            System.err.println("\nError occurred. No cipher text to display.");
            System.out.println();
        } else {
            System.out.println("\nCipher text content: " + cipherText);
            System.out.println();
        }
    }

    /**
     * Allows the user to enter a file name for the cipher text to be saved to
     *
     * @throws IOException Error thrown
     */
    private void saveCipher() throws IOException {
        String cipherSaveFile;
        do {
            System.out.println("\nEnter file to save cipher text to: ");
            cipherSaveFile = scan.nextLine(); // takes in user input of cipher text file name
            System.out.println();
        } while (cipherSaveFile == null || cipherSaveFile.trim().isEmpty());
        try (FileWriter fw = new FileWriter(cipherSaveFile);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {
            outfile.println(cipherText); // outputs the cipher text to the first line of the file specified
        } catch (IOException e) { // else throws IOException error
            System.err.println("\nError occurred. Cannot save cipher text to file.");
            System.out.println();
        }
    }

    /**
     * Saves the prepared plain text to the designated file prep.txt
     *
     * @throws IOException Error thrown
     */
    public void savePlain() throws IOException {
        String plainSaveFile;
        do {
            System.out.println("\nEnter file to save the plain text to: ");
            plainSaveFile = scan.nextLine(); // takes in user input of cipher text file name
            System.out.println();
        } while (plainSaveFile == null || plainSaveFile.trim().isEmpty());
        try (FileWriter fw = new FileWriter(plainSaveFile);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {
            outfile.println(prepPlainText); // outputs the plain text to the first line of the file specified
        } catch (IOException e) { // else throws IOException error
            System.err.println("\nError occurred. Cannot save plain text to file.");
            System.out.println();
        }
    }

    /**
     * Decrypts the data stored in the cipher text file using the current selected cipher
     * Calls the class of the relevant cipher
     */
    private void decrypt() {
        Cipher cipher = new Cipher(prepPlainText, cipherText, key);
        switch (currentCipher) {
            case "Caesar":
                CaesarCipher cCipher = new CaesarCipher();
                prepPlainText = cCipher.decryptCaesar(cipher); // passes the cipher text and the key to the encryption method in the caesar cipher class
                break;
            case "Keyed Caesar":
                KeyedCaesarCipher kCipher = new KeyedCaesarCipher();
                prepPlainText = kCipher.decryptKeyed(cipher); // passes the prepared plain text, the keyword, and the shift to the encryption method in the keyed caesar cipher class
                break;
            case "Vigenere":
                VigenereCipher vCipher = new VigenereCipher();
                prepPlainText = vCipher.decryptVigenere(cipher); // passes the key and the prepared plain text to the vigenere cipher class
                break;
            default:
                System.err.println("\nError occurred. Incorrect cipher type.");
                System.out.println();
        }
        try {
            FileWriter writer = new FileWriter(prepPlainTextFile);
            writer.write(prepPlainText);
            writer.close();
        } catch (IOException e) {
            System.err.println("\nError occurred. Cannot write to file.");
            System.out.println();
        }
    }

    /**
     * Displays the current content stored in plain text
     */
    private void displayPlain() {
        if (prepPlainText == null || prepPlainText.isEmpty()) { // ensures there is plain text data to display
            System.err.println("\nError occurred. No plain text to display.");
            System.out.println();
        } else {
            try {
                String contents = new String(Files.readAllBytes(Paths.get(prepPlainTextFile)));
                System.out.println("\nPrepared plain text content: " + contents);
                System.out.println();
            } catch (IOException e) {
                System.err.println("\nError occurred. Cannot read prepared plain text file.");
                System.out.println();
            }
        }
    }

    /**
     * Main method, ran when program starts
     *
     * @param args
     */
    public static void main(String[] args) {
        CodeBreaker start = new CodeBreaker();
        start.initialise(); // calls initialise() method
        start.menu(); // calls menu() method
    }
}
