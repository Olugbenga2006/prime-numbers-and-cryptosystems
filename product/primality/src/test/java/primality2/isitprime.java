package primality2;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class isitprime {
  private LargerPrimes primeChecker;

  @BeforeEach
  public void setUp() {
    primeChecker = new LargerPrimes();
  }

  @Test
  public void testIsPrime_WithNegativeNumber() {
    // Test negative numbers
    BigInteger largePrime = new BigInteger("-5");
    assertFalse(primeChecker.isPrime(largePrime,2), "Negative numbers should not be prime.");
  }

  @Test
  public void testIsPrime_WithZero() {
    // Test zero
    BigInteger largePrime = new BigInteger("0");
    assertFalse(primeChecker.isPrime(largePrime,2), "Zero should not be prime.");
  }

  @Test
  public void testIsPrime_WithOne() {
    // Test one
    BigInteger LargePrime = new BigInteger("1");
    assertFalse(primeChecker.isPrime(LargePrime,2), "One should not be prime.");
  }

  @Test
  public void testIsPrime_WithTwo() {
    // Test two (the smallest prime number)
    BigInteger LargePrime = new BigInteger("2");
    assertTrue(primeChecker.isPrime(LargePrime,2), "Two should be prime.");
  }

  @Test
  public void testIsPrime_WithThree() {
    // Test three
    BigInteger LargePrime = new BigInteger("3");
    assertTrue(primeChecker.isPrime(LargePrime,2), "Three should be prime.");
  }

  @Test
  public void testIsPrime_WithFour() {
    // Test four
    BigInteger LargePrime = new BigInteger("4");
    assertFalse(primeChecker.isPrime(LargePrime,2), "Four should not be prime.");
  }
  @Test
  public void testIsPrime_WithPrimeNumber() {
    // Test a known prime number
    BigInteger LargePrime = new BigInteger("17");
    assertTrue(primeChecker.isPrime(LargePrime,2), "Seventeen should be prime.");
  }

  @Test
  public void testIsPrime_WithCompositeNumber() {
    // Test a known composite number
    BigInteger LargePrime = new BigInteger("10");
    assertFalse(primeChecker.isPrime(LargePrime,2), "Ten should not be prime.");
  }

  @Test
  public void testIsPrime_WithLargePrimeNumber() {
    // Test a large prime number
    BigInteger LargePrime = new BigInteger("7919");
    assertTrue(primeChecker.isPrime(LargePrime,5), "Seven thousand nine hundred nineteen should be prime.");
  }
  @Test
  public void testIsPrime_WithLargeCompositeNumber() {
    // Test a large composite number
    BigInteger LargePrime = new BigInteger("10000");
    assertFalse(primeChecker.isPrime(LargePrime,2), "Ten thousand should not be prime.");
  }
  @Test
  public void testIsPrime_WithLargerPrimeNumber() {
    // Test a  larger prime number
    BigInteger LargePrime = new BigInteger("1053569021");
    assertTrue(primeChecker.isPrime(LargePrime,15), "should be prime");
  }
  

}
