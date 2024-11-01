package primality2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class isitprime {
  private prime primeChecker;

  @BeforeEach
  public void setUp() {
      primeChecker = new prime();
  }

  @Test
  public void testIsPrime_WithNegativeNumber() {
      // Test negative numbers
      assertFalse(primeChecker.isprime(-5), "Negative numbers should not be prime.");
  }

  @Test
  public void testIsPrime_WithZero() {
      // Test zero
      assertFalse(primeChecker.isprime(0), "Zero should not be prime.");
  }

  @Test
  public void testIsPrime_WithOne() {
      // Test one
      assertFalse(primeChecker.isprime(1), "One should not be prime.");
  }

  @Test
  public void testIsPrime_WithTwo() {
      // Test two (the smallest prime number)
      assertTrue(primeChecker.isprime(2), "Two should be prime.");
  }

  @Test
  public void testIsPrime_WithThree() {
      // Test three
      assertTrue(primeChecker.isprime(3), "Three should be prime.");
  }

  @Test
  public void testIsPrime_WithFour() {
      // Test four
      assertFalse(primeChecker.isprime(4), "Four should not be prime.");
  }
  @Test
  public void testIsPrime_WithPrimeNumber() {
      // Test a known prime number
      assertTrue(primeChecker.isprime(17), "Seventeen should be prime.");
  }

  @Test
  public void testIsPrime_WithCompositeNumber() {
      // Test a known composite number
      assertFalse(primeChecker.isprime(10), "Ten should not be prime.");
  }

  @Test
  public void testIsPrime_WithLargePrimeNumber() {
      // Test a large prime number
      assertTrue(primeChecker.isprime(7919), "Seven thousand nine hundred nineteen should be prime.");
  }
  @Test
  public void testIsPrime_WithLargeCompositeNumber() {
      // Test a large composite number
      assertFalse(primeChecker.isprime(10000), "Ten thousand should not be prime.");
  }
  @Test
  public void testIsPrime_WithLargerPrimeNumber() {
      // Test a  larger prime number
      assertTrue(primeChecker.isprime(1053569021), "should be prime");
  }
  @Test
  public void testIsPrime_WithevenLargerPrimeNumber() {
      // Test the very large prime number
      assertTrue(primeChecker.isprime(35663926699L), "The number 35663926699 should be prime.");
  }

}
