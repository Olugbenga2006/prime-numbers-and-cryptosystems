package primality2;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellmanKeyGenerator {
  private BigInteger primeP;
  private BigInteger generatorG;
  private BigInteger privateKey;
  private BigInteger publicKey;

  // Secure random generator
  private static final SecureRandom random = new SecureRandom();

  public DiffieHellmanKeyGenerator(int bitLength) {
    // Generate a large prime number for P
    primeP = BigInteger.probablePrime(bitLength, random);

    // Choose a small primitive root (generator) G
    generatorG = BigInteger.valueOf(2); // Common choice

    // Generate private key (a random number < P)
    privateKey = new BigInteger(bitLength - 1, random);

    // Compute public key (G^privateKey mod P)
    publicKey = generatorG.modPow(privateKey, primeP);
  }

  // Compute shared secret key given another party's public key
  public BigInteger computeSharedKey(BigInteger otherPublicKey) {
    return otherPublicKey.modPow(privateKey, primeP);
  }

  public BigInteger getPrimeP() {
    return primeP;
  }

  public BigInteger getGeneratorG() {
    return generatorG;
  }

  public BigInteger getPrivateKey() {
    return privateKey;
  }

  public BigInteger getPublicKey() {
    return publicKey;
  }
}
