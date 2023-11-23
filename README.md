# CodeBreaker README

## Overview

The CodeBreaker program is a Java application designed to perform various operations related to classical ciphers, including the Caesar Cipher, Keyed Caesar Cipher, and Vigenere Cipher. Users can choose the cipher type, input plain text or cipher text from files, edit the cipher key, encrypt and decrypt data, and save results to files.

## Table of Contents

- [Getting Started](#getting-started)
- [Usage](#usage)
- [Cipher Types](#cipher-types)
- [File Input/Output](#file-inputoutput)
- [Key Editing](#key-editing)
- [Error Handling](#error-handling)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

To run the CodeBreaker program, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in an integrated development environment (IDE) that supports Java.
3. Locate the `CodeBreaker` class in the project and run the `main` method.

## Usage

Upon running the program, users are presented with a menu that allows them to choose various operations related to classical ciphers. The menu includes options to select the cipher type, input files, edit the key, perform encryption and decryption, and save results.

## Cipher Types

The CodeBreaker program supports three types of classical ciphers:

1. **Caesar Cipher:** A basic substitution cipher where each letter in the plaintext is shifted a certain number of places down or up the alphabet.

2. **Keyed Caesar Cipher:** Similar to the Caesar Cipher, but with an additional keyword that determines the shift for each letter.

3. **Vigenere Cipher:** A method of encrypting alphabetic text by using a simple form of polyalphabetic substitution.

Users can choose the cipher type through the menu options.

## File Input/Output

The program allows users to input plain text or cipher text from files and save the results to files. When inputting files, the program reads the content, removes punctuation, and converts the text to uppercase. Users can specify file names when saving results.

## Key Editing

The program provides functionality to edit the key associated with the selected cipher type. The key format varies depending on the cipher type: an integer for Caesar Cipher, an integer followed by a string for Keyed Caesar Cipher, and a string for Vigenere Cipher.

## Error Handling

The CodeBreaker program includes error handling mechanisms to ensure correct user input. If invalid input is detected, appropriate error messages are displayed, guiding the user to enter the required information correctly.

## Dependencies

The CodeBreaker program does not have external dependencies. It is a standalone Java application.

## Contributing

Contributions to the CodeBreaker program are welcome. If you have suggestions, improvements, or bug fixes, feel free to create a pull request.

## License

The CodeBreaker program is released under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code as per the terms of the license.
