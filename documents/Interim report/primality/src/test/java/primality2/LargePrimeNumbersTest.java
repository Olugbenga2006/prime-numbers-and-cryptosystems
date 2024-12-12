package primality2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LargePrimeNumbersTest {
    private prime primeChecker;

    @BeforeEach
    public void setUp() {
        primeChecker = new prime();
    }

    @Test
    public void testIsPrime_WithLargePrime1() {
        // Test a known large prime number with 12 digits
        long largePrime1 = 732881291171L;
        assertTrue(primeChecker.isprime(largePrime1), "732881291171 should be prime.");
    }

    @Test
    public void testIsPrime_WithLargePrime2() {
        // Test another known large prime number with 13 digits
        long largePrime2 = 3595107864083L;
        assertTrue(primeChecker.isprime(largePrime2), "3595107864083 should be prime.");
    }
  
    @Test
    public void testIsPrime_WithLargePrime3() {
        // Test a known large prime number with 14 digits
        long largePrime3 = 86518833269927L;
        assertTrue(primeChecker.isprime(largePrime3), "86518833269927 should be prime.");
    }

    @Test
    public void testIsPrime_WithLargePrime4() {
        // Test a known large prime number with 15 digits
        long largePrime4 = 871542403985867L;
        assertTrue(primeChecker.isprime(largePrime4), "871542403985867 should be prime.");
    }

    @Test
    public void testIsPrime_WithLargePrime5() {
        // Test a known large prime number with 16 digits
        long largePrime5 = 6409139063716621L;
        assertTrue(primeChecker.isprime(largePrime5), "6409139063716621 should be prime.");
    }

    @Test
    public void testIsPrime_WithLargePrime6() {
        // Test a known large prime number with 17 digits
        long largePrime6 = 24588943630796093L;
        assertTrue(primeChecker.isprime(largePrime6), "24588943630796093 should be prime.");
    }

    @Test
    public void testIsPrime_WithLargePrime7() {
        // Test a known large prime number with 18 digits
        long largePrime7 = 110266102705504331L;
        assertTrue(primeChecker.isprime(largePrime7), "110266102705504331 should be prime.");
    }

    @Test
    public void testIsPrime_WithLargeComposite1() {
        // Test a known large composite number with 12 digits
        long largeComposite1 = 1000000000040L; // Even number
        assertFalse(primeChecker.isprime(largeComposite1), "1000000000040 should not be prime.");
    }

    @Test
    public void testIsPrime_WithLargeComposite2() {
        // Test a known large composite number with 13 digits
        long largeComposite2 = 100000000020L; // Even number
        assertFalse(primeChecker.isprime(largeComposite2), "100000000020 should not be prime.");
    }

    @Test
    public void testIsPrime_WithLargeComposite3() {
        // Test a known large composite number with 15 digits
        long largeComposite3 = 100000000000030L; // Even number
        assertFalse(primeChecker.isprime(largeComposite3), "100000000000030 should not be prime.");
    }

    @Test
    public void testIsPrime_WithLargeComposite4() {
        // Test a known large composite number with 16 digits
        long largeComposite4 = 1000000000000004L; // Even number
        assertFalse(primeChecker.isprime(largeComposite4), "1000000000000004 should not be prime.");
    }

    @Test
    public void testIsPrime_WithLargeComposite5() {
        // Test a known large composite number with 17 digits
        long largeComposite5 = 100000000000000020L; // Even number
        assertFalse(primeChecker.isprime(largeComposite5), "100000000000000020 should not be prime.");
    }

    @Test
    public void testIsPrime_WithLargeComposite6() {
        // Test a known large composite number with 18 digits
        long largeComposite6 = 1000000000000000004L; // Even number
        assertFalse(primeChecker.isprime(largeComposite6), "1000000000000000004 should not be prime.");
    }
}
