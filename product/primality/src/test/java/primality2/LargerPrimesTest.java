package primality2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;

class LargerPrimesTest {
    private LargerPrimes largerPrimesChecker;

    @BeforeEach
    public void setUp() {
        largerPrimesChecker = new LargerPrimes();
    }

    @Test
    public void testIsPrime_WithLargePrime1() {
        // Test a known large prime number with 30 digits
        BigInteger largePrime1 = new BigInteger("636093585352712707633611402517");
        assertTrue(largerPrimesChecker.isPrime(largePrime1), "636093585352712707633611402517 should be prime.");
    }

    @Test
    public void testIsPrime_WithLargePrime2() {
        // Test a known large prime number with 40 digits
        BigInteger largePrime2 = new BigInteger("3670373232436798233752740931578068777707");
        assertTrue(largerPrimesChecker.isPrime(largePrime2), "3670373232436798233752740931578068777707 should be prime.");
    }
    @Test
    public void testIsPrime_WithLargePrime3() {
        // Test a known large prime number with 70 digits
        BigInteger largePrime3 = new BigInteger("3306255143249535762725215783282813901982286552958066097386549634901033");
        assertTrue(largerPrimesChecker.isPrime(largePrime3), "3306255143249535762725215783282813901982286552958066097386549634901033 should be prime.");
    }
    @Test
    public void testIsPrime_WithLargePrime4() {
        // Test a known large prime number with 200 digits
        BigInteger largePrime4 = new BigInteger("84480248448081452257668856369225076520225591347950741457698755694187073835274168220758444289342689715106769742554568945911355262656562652414251609357801774840095947028519555119850032559423832765791073");
        assertTrue(largerPrimesChecker.isPrime(largePrime4), "84480248448081452257668856369225076520225591347950741457698755694187073835274168220758444289342689715106769742554568945911355262656562652414251609357801774840095947028519555119850032559423832765791073 should be prime.");
    }

    @Test
    public void testIsPrime_WithLargeComposite1() {
        BigInteger largeComposite1 = new BigInteger("1000000000040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"); // Even number
        assertFalse(largerPrimesChecker.isPrime(largeComposite1), "1000000000040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000should not be prime.");
    }
}
