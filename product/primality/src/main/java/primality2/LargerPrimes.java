package primality2;

import java.math.BigInteger;
import java.io.*;
import java.math.*;

/**
 * The {@code LargerPrimes} class provides methods for modular exponentiation and 
 * probabilistic primality testing using the Miller-Rabin algorithm.
 * 
 * This class is useful for working with large prime numbers, particularly 
 * in cryptographic contexts such as RSA key generation.
 */
public class LargerPrimes {

  /**
   * Computes {@code (x^y) % p} using modular exponentiation.
   * This method is efficient for large exponents using the binary exponentiation technique.
   *
   * @param x the base
   * @param y the exponent
   * @param p the modulus
   * @return {@code (x^y) mod p}
   */
  static BigInteger modpower(BigInteger x, BigInteger y, BigInteger p) {
    BigInteger res = BigInteger.ONE;
    x = x.mod(p);

    while (y.compareTo(BigInteger.ZERO) > 0) {
      if (y.and(BigInteger.ONE).equals(BigInteger.ONE))
        res = (res.multiply(x)).mod(p);

      y = y.shiftRight(1); // Equivalent to y = y / 2
      x = (x.multiply(x)).mod(p);
    }

    return res;
  }

  /**
   * Performs one iteration of the Miller-Rabin primality test.
   * 
   * @param d an odd number such that {@code n - 1 = 2^r * d} for some {@code r ≥ 1}
   * @param n the number to test for primality
   * @return {@code true} if {@code n} is probably prime, {@code false} if definitely composite
   */
  static boolean millerTest(BigInteger d, BigInteger n) {
    BigInteger a = BigInteger.valueOf(2)
        .add(new BigInteger(n.subtract(BigInteger.valueOf(4)).bitLength(), new java.util.Random())
            .mod(n.subtract(BigInteger.valueOf(4))));

    BigInteger x = modpower(a, d, n);

    if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
      return true;

    while (!d.equals(n.subtract(BigInteger.ONE))) {
      x = (x.multiply(x)).mod(n);
      d = d.shiftLeft(1); // Equivalent to d = d * 2

      if (x.equals(BigInteger.ONE))
        return false;

      if (x.equals(n.subtract(BigInteger.ONE)))
        return true;
    }

    return false;
  }

  /**
   * Checks if a number is prime using the Miller-Rabin primality test.
   * 
   * @param n the number to be tested
   * @param k the number of iterations to perform; higher {@code k} increases accuracy
   * @return {@code true} if {@code n} is probably prime, {@code false} if definitely composite
   */
  static boolean isPrime(BigInteger n, int k) {
    if (n.compareTo(BigInteger.ONE) <= 0 || n.equals(BigInteger.valueOf(4)))
      return false;

    if (n.compareTo(BigInteger.valueOf(3)) <= 0)
      return true;

    BigInteger d = n.subtract(BigInteger.ONE);
    while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
      d = d.divide(BigInteger.TWO);
    }

    for (int i = 0; i < k; i++) {
      if (!millerTest(d, n))
        return false;
    }

    return true;
  }
}
