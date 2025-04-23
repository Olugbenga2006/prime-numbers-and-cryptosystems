package primality2;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The {@code RSA} class implements a basic RSA encryption and decryption system,
 * including key generation, encryption/decryption of numerical messages,
 * and character-to-number mappings for text-based message handling.
 */
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

  // Initialize character-to-number and number-to-character mappings
  static {
    // Map uppercase letters A-Z to 1–26
    for (char c = 'A'; c <= 'Z'; c++) {
      charToNumberMap.put(c, c - 'A' + 1);
      numberToCharMap.put(c - 'A' + 1, c);
    }

    // Map lowercase letters a-z to 27–52
    for (char c = 'a'; c <= 'z'; c++) {
      charToNumberMap.put(c, c - 'a' + 27);
      numberToCharMap.put(c - 'a' + 27, c);
    }

    // Map punctuation and special characters
    charToNumberMap.put(' ', 53); numberToCharMap.put(53, ' ');
    charToNumberMap.put('?', 54); numberToCharMap.put(54, '?');
    charToNumberMap.put('!', 55); numberToCharMap.put(55, '!');
    charToNumberMap.put(':', 56); numberToCharMap.put(56, ':');
    charToNumberMap.put(';', 57); numberToCharMap.put(57, ';');
    charToNumberMap.put(',', 58); numberToCharMap.put(58, ',');
    charToNumberMap.put('‘', 59); numberToCharMap.put(59, '‘');
    charToNumberMap.put('’', 60); numberToCharMap.put(60, '’');
    charToNumberMap.put('“', 61); numberToCharMap.put(61, '“');
    charToNumberMap.put('”', 62); numberToCharMap.put(62, '”');
    charToNumberMap.put('-', 63); numberToCharMap.put(63, '-');
    charToNumberMap.put('.', 64); numberToCharMap.put(64, '.');
    charToNumberMap.put('(', 65); numberToCharMap.put(65, '(');
    charToNumberMap.put(')', 66); numberToCharMap.put(66, ')');
  }

  /**
   * Constructs an RSA object with keys generated from the specified bit length and
   * Miller-Rabin primality test iterations.
   *
   * @param bitLength the bit length of the prime numbers
   * @param millerRabinIterations the number of iterations for primality testing
   */
  public RSA(int bitLength, int millerRabinIterations) {
    primeP = generateAndTestPrime(bitLength, millerRabinIterations);
    primeQ = generateAndTestPrime(bitLength, millerRabinIterations);
    mod_N = primeP.multiply(primeQ);
    phi_N = primeP.subtract(BigInteger.ONE).multiply(primeQ.subtract(BigInteger.ONE));
    public_KeyE = BigInteger.valueOf(65537);

    while (!phi_N.gcd(public_KeyE).equals(BigInteger.ONE)) {
      public_KeyE = public_KeyE.add(BigInteger.ONE);
    }

    private_KeyD = public_KeyE.modInverse(phi_N);
  }

  /**
   * Generates a probable prime of the specified bit length.
   *
   * @param bitLength the bit length of the prime number
   * @param millerRabinIterations the number of iterations for primality checking
   * @return a probable prime number
   */
  private BigInteger generateAndTestPrime(int bitLength, int millerRabinIterations) {
    Random random = new Random();
    return BigInteger.probablePrime(bitLength, random);
  }

  /**
   * Converts a string into a numeric representation using predefined character mappings.
   *
   * @param text the input text
   * @return a BigInteger representing the numeric form of the text
   */
  public BigInteger mapToNumber(String text) {
    StringBuilder numberString = new StringBuilder();

    for (char c : text.toCharArray()) {
      if (charToNumberMap.containsKey(c)) {
        int number = charToNumberMap.get(c);
        numberString.append(String.format("%02d", number));
      } else {
        throw new IllegalArgumentException("Character not supported: " + c);
      }
    }

    return new BigInteger(numberString.toString());
  }

  /**
   * Converts a numeric representation back into its corresponding text form.
   *
   * @param number the numeric representation of the text
   * @return the original text
   */
  public String mapToText(BigInteger number) {
    String numberString = number.toString();
    StringBuilder text = new StringBuilder();

    if (numberString.length() % 2 != 0) {
      numberString = "0" + numberString;
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

  /**
   * Sets the modulus N for the RSA system.
   *
   * @param mod_N the modulus value
   */
  public void setModulusN(BigInteger mod_N) {
    this.mod_N = mod_N;
  }

  /**
   * Sets the public key exponent E.
   *
   * @param public_KeyE the public exponent
   */
  public void setPublicKeyE(BigInteger public_KeyE) {
    this.public_KeyE = public_KeyE;
  }

  /** @return the first prime number P used in RSA key generation */
  public BigInteger getPrimeP() {
    return primeP;
  }

  /** @return the second prime number Q used in RSA key generation */
  public BigInteger getPrimeQ() {
    return primeQ;
  }

  /** @return the modulus N used for encryption and decryption */
  public BigInteger getModulusN() {
    return mod_N;
  }

  /** @return the public key exponent E */
  public BigInteger getPublicKeyE() {
    return public_KeyE;
  }

  /** @return the private key exponent D */
  public BigInteger getPrivateKeyD() {
    return private_KeyD;
  }

  /**
   * Encrypts a numeric message using the RSA public key.
   *
   * @param message the plaintext message as a BigInteger
   * @return the encrypted message
   */
  public BigInteger encrypt(BigInteger message) {
    return message.modPow(public_KeyE, mod_N);
  }

  /**
   * Decrypts a numeric message using the RSA private key.
   *
   * @param encryptedMessage the encrypted message as a BigInteger
   * @return the decrypted (plaintext) message
   */
  public BigInteger decrypt(BigInteger encryptedMessage) {
    return encryptedMessage.modPow(private_KeyD, mod_N);
  }

  /**
   * Encrypts a text message by mapping it to a number and applying RSA encryption.
   *
   * @param text the plaintext message
   * @return the encrypted message as a string
   */
  public String encryptText(String text) {
    BigInteger number = mapToNumber(text);
    BigInteger encryptedNumber = encrypt(number);
    return encryptedNumber.toString();
  }

  /**
   * Decrypts an encrypted text message using RSA decryption and maps it back to readable text.
   *
   * @param encryptedText the encrypted message as a string
   * @return the decrypted plaintext message
   */
  public String decryptText(String encryptedText) {
    BigInteger encryptedNumber = new BigInteger(encryptedText);
    BigInteger decryptedNumber = decrypt(encryptedNumber);
    return mapToText(decryptedNumber);
  }
}
