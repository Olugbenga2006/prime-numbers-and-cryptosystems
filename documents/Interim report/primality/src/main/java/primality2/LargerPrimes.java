package primality2;

import java.math.BigInteger;
import java.io.*;
import java.math.*;

public class LargerPrimes {
  // returns (x^y) % p
  static BigInteger modpower(BigInteger x, BigInteger y, BigInteger p) {

    BigInteger res = BigInteger.ONE;

    // x cannot be larger than p
    x = x.mod(p);

    while (y.compareTo(BigInteger.ZERO) > 0) {
      // If y is odd, multiply x with result
      if (y.and(BigInteger.ONE).equals(BigInteger.ONE)) // y%2 == 1
        res = (res.multiply(x)).mod(p);

      // y must be even now
      y = y.shiftRight(1); // y = y/2
      x = (x.multiply(x)).mod(p);
    }

    return res;
  }

  // This function is called for all k 
  // It returns false if n is composite and returns true if n is probably prime.
  // d is an odd number such that (2^r)*d = n-1 for some r >= 1
  static boolean millerTest(BigInteger d, BigInteger n) {

    // Pick a random number in [2..n-2]
    // Corner cases make sure that n > 4
    BigInteger a = BigInteger.valueOf(2)
        .add(new BigInteger(n.subtract(BigInteger.valueOf(4)).bitLength(), new java.util.Random()).mod(n.subtract(BigInteger.valueOf(4))));

    // Compute a^d % n
    BigInteger x = modpower(a, d, n);

    if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
      return true;

    // Keep squaring x while one of the following doesn't happen
    // d does not reach n-1
    // (x^2) % n is not 1
    // (x^2) % n is not n-1
    while (!d.equals(n.subtract(BigInteger.ONE))) {
      x = (x.multiply(x)).mod(n);
      d = d.shiftLeft(1); // d *= 2

      if (x.equals(BigInteger.ONE)) {
        return false;
      }
      if (x.equals(n.subtract(BigInteger.ONE))) {
        return true;
      }
    }

    // Return composite
    return false;
  }

  // It returns false if n is composite 
  // and returns true if n is probably prime. 
  // k is an input parameter that determines accuracy level.
  // Higher value of k indicates more accuracy.
  static boolean isPrime(BigInteger n, int k) {

    // Corner cases
    if (n.compareTo(BigInteger.ONE) <= 0 || n.equals(BigInteger.valueOf(4))) {
      return false;
    }
    // case for 2 and 3
    if (n.compareTo(BigInteger.valueOf(3)) <= 0) {
      return true;
    }
    // d=n-1
    BigInteger d = n.subtract(BigInteger.ONE);
    //make d odd
    while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
      d = d.divide(BigInteger.TWO);
    }


    // Iterate given number of 'k' times
    for (int i = 0; i < k; i++)
      if (!millerTest(d, n))
        return false;

    return true;
  }

}
