package primality2;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * The {@code DiffieHellmanKeyGenerator} class implements the key generation
 * portion of the Diffie-Hellman key exchange protocol. It generates a large prime number (P),
 * a primitive root modulo P (generator G), a private key, and computes a corresponding public key.
 */
public class DiffieHellmanKeyGenerator {
  private BigInteger primeP;
  private BigInteger generatorG;
  private BigInteger privateKey;
  private BigInteger publicKey;

  /** Secure random number generator for cryptographic purposes */
  private static final SecureRandom random = new SecureRandom();

  /**
   * Constructs a {@code DiffieHellmanKeyGenerator} instance with keys based on the specified bit length.
   *
   * @param bitLength the bit length for the prime number P
   */
  public DiffieHellmanKeyGenerator(int bitLength) {
    // Generate a large prime number P for the finite cyclic group
    primeP = BigInteger.probablePrime(bitLength, random);

    // Choose a common small primitive root (generator) G of P
    generatorG = BigInteger.valueOf(2); // Typical small generator

    // Generate a random private key < P
    privateKey = new BigInteger(bitLength - 1, random);

    // Compute the public key as G^privateKey mod P
    publicKey = generatorG.modPow(privateKey, primeP);
  }

  /**
   * Computes the shared secret key using another party's public key.
   *
   * @param otherPublicKey the public key received from the other party
   * @return the shared secret key as a BigInteger
   */
  public BigInteger computeSharedKey(BigInteger otherPublicKey) {
    return otherPublicKey.modPow(privateKey, primeP);
  }

  /**
   * Returns the large prime number P used in the Diffie-Hellman key exchange.
   *
   * @return the prime number P
   */
  public BigInteger getPrimeP() {
    return primeP;
  }

  /**
   * Returns the generator G (primitive root modulo P).
   *
   * @return the generator G
   */
  public BigInteger getGeneratorG() {
    return generatorG;
  }

  /**
   * Returns the private key used in this instance.
   * <p><b>Warning:</b> This should never be shared in a real-world scenario.</p>
   *
   * @return the private key
   */
  public BigInteger getPrivateKey() {
    return privateKey;
  }

  /**
   * Returns the public key generated from the private key.
   *
   * @return the public key
   */
  public BigInteger getPublicKey() {
    return publicKey;
  }
}
