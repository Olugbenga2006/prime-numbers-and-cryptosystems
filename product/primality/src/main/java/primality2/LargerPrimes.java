package primality2;

import java.math.BigInteger;

public class LargerPrimes {
    public boolean isPrime(BigInteger n) {
        if (n.compareTo(BigInteger.ONE) <= 0) {
            return false;
        }
        if (n.equals(BigInteger.TWO)) {
            return true; // 2 is prime
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false; // Exclude even numbers greater than 2
        }

        // Check for factors from 3 up to the square root of n (incrementing by 2)
        BigInteger sqrtN = n.sqrt();
        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(sqrtN) <= 0; i = i.add(BigInteger.TWO)) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                return false; // n is divisible by i
            }
        }
        return true; // n is prime
    }
}
