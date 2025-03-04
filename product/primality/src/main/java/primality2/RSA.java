package primality2;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RSA {
  private BigInteger primeP;
  private BigInteger primeQ;
  private BigInteger mod_N;
  private BigInteger phi_N;
  private BigInteger public_KeyE;
  private BigInteger private_KeyD;
  // Mapping of characters to numbers
  private static final Map<Character, Integer> charToNumberMap = new HashMap<>();
  private static final Map<Integer, Character> numberToCharMap = new HashMap<>();

  static {
    // Initialize character to number mapping for uppercase letters
    for (char c = 'A'; c <= 'Z'; c++) {
      charToNumberMap.put(c, c - 'A' + 1);
      numberToCharMap.put(c - 'A' + 1, c);
    }
    // Initialize character to number mapping for lowercase letters
    for (char c = 'a'; c <= 'z'; c++) {
      charToNumberMap.put(c, c - 'a' + 27); // Start from 27 to avoid overlap with uppercase
      numberToCharMap.put(c - 'a' + 27, c);
    }
    // Initialize character to number mapping for punctuation and special characters- forgot about these and lowercases aha. original comment will become javadoc and this becomes new comment
    charToNumberMap.put(' ', 53); // Space
    numberToCharMap.put(53, ' ');
    charToNumberMap.put('?', 54); // Question mark
    numberToCharMap.put(54, '?');
    charToNumberMap.put('!', 55); // Exclamation mark
    numberToCharMap.put(55, '!');
    charToNumberMap.put(':', 56); // Colon
    numberToCharMap.put(56, ':');
    charToNumberMap.put(';', 57); // Semi-colon
    numberToCharMap.put(57, ';');
    charToNumberMap.put(',', 58); // Comma
    numberToCharMap.put(58, ',');
    charToNumberMap.put('‘', 59); // Single opening quotation mark
    numberToCharMap.put(59, '‘');
    charToNumberMap.put('’', 60); // Single closing quotation mark
    numberToCharMap.put(60, '’');
    charToNumberMap.put('“', 61); // Double opening quotation mark
    numberToCharMap.put(61, '“');
    charToNumberMap.put('”', 62); // Double closing quotation mark
    numberToCharMap.put(62, '”');
    charToNumberMap.put('-', 63); // Dash
    numberToCharMap.put(63, '-');
    charToNumberMap.put('.', 64); // Full stop
    numberToCharMap.put(64, '.');
    charToNumberMap.put('(', 65); // Left bracket
    numberToCharMap.put(65, '(');
    charToNumberMap.put(')', 66); // Right bracket
    numberToCharMap.put(66, ')');
  }


  // Constructor to initialize RSA key generation
  public RSA(int bitLength, int millerRabinIterations) {
    // Generate two large probable primes using BigInteger.probablePrime
    primeP = generateAndTestPrime(bitLength, millerRabinIterations);
    primeQ = generateAndTestPrime(bitLength, millerRabinIterations);

    // Compute modulus N = p * q
    mod_N = primeP.multiply(primeQ);

    // Compute Euler's totient function phi(N) = (p-1) * (q-1)
    phi_N = primeP.subtract(BigInteger.ONE).multiply(primeQ.subtract(BigInteger.ONE));

    // Generate public key E (most commonly used E is 65537)
    public_KeyE = BigInteger.valueOf(65537);

    // Ensure E is coprime with phi(N)
    while (!phi_N.gcd(public_KeyE).equals(BigInteger.ONE)) {
      public_KeyE = public_KeyE.add(BigInteger.ONE);
    }

    // Generate private key D (modular inverse of E modulo phi(N))
    private_KeyD = public_KeyE.modInverse(phi_N);
  }

  // Method to generate and test a probable prime
  private BigInteger generateAndTestPrime(int bitLength, int millerRabinIterations) {
    BigInteger prime;
    Random random = new Random();
    prime = BigInteger.probablePrime(bitLength, random);

    return prime;
  }

  //Method to map a word or sentence to a number
  public BigInteger mapToNumber(String text) {
    StringBuilder numberString = new StringBuilder();

    for (char c : text.toCharArray()) {
      if (charToNumberMap.containsKey(c)) {
        int number = charToNumberMap.get(c);
        numberString.append(String.format("%02d", number)); // Ensure two digits per character
      } else {
        throw new IllegalArgumentException("Character not supported: " + c);
      }
    }

    return new BigInteger(numberString.toString());
  }

  // Method to map a number back to a word or sentence
  public String mapToText(BigInteger number) {
    String numberString = number.toString();
    StringBuilder text = new StringBuilder();

    // Ensure the number string has an even length
    if (numberString.length() % 2 != 0) {
      numberString = "0" + numberString; // Pad with a leading zero if necessary
    }

    for (int i = 0; i < numberString.length(); i += 2) {
      int num = Integer.parseInt(numberString.substring(i, i + 2));
      if (numberToCharMap.containsKey(num)) {
        text.append(numberToCharMap.get(num));
      } else {
        throw new IllegalArgumentException("Number not mapped to a character: " + num);
      }
    }

    return text.toString();
  }
  
  // Setter method for modulus N
  public void setModulusN(BigInteger mod_N) {
    this.mod_N = mod_N;
  }

  // Setter method for public key E
  public void setPublicKeyE(BigInteger public_KeyE) {
    this.public_KeyE = public_KeyE;
  }

  // Getter methods for the keys and primes
  public BigInteger getPrimeP() {
    return primeP;
  }

  public BigInteger getPrimeQ() {
    return primeQ;
  }

  public BigInteger getModulusN() {
    return mod_N;
  }

  public BigInteger getPublicKeyE() {
    return public_KeyE;
  }

  public BigInteger getPrivateKeyD() {
    return private_KeyD;
  }
  
  // Method to encrypt a message using the public key
  public BigInteger encrypt(BigInteger message) {
    return message.modPow(public_KeyE, mod_N);
  }

  // Method to decrypt a message using the private key
  public BigInteger decrypt(BigInteger encryptedMessage) {
    return encryptedMessage.modPow(private_KeyD, mod_N);
  }
  // Method to encrypt a word or sentence
  public String encryptText(String text) {
    BigInteger number = mapToNumber(text);
    BigInteger encryptedNumber = encrypt(number);
    return encryptedNumber.toString();
  }

  // Method to decrypt a word or sentence
  public String decryptText(String encryptedText) {
    BigInteger encryptedNumber = new BigInteger(encryptedText);
    BigInteger decryptedNumber = decrypt(encryptedNumber);
    return mapToText(decryptedNumber);
  }

}