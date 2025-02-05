package primality2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;

class RSATest {
    private RSA rsa;

    @BeforeEach
    public void setUp() {
        // Initialize RSA with a bit length of 1024 and 20 Miller-Rabin iterations
        rsa = new RSA(2048, 20);
    }

    @Test
    public void testPrimeGeneration() {
        // Test if the generated primes are valid using the isPrime method
        assertTrue(LargerPrimes.isPrime(rsa.getPrimeP(), 20), "Prime P should be a valid prime.");
        assertTrue(LargerPrimes.isPrime(rsa.getPrimeQ(), 20), "Prime Q should be a valid prime.");
    }

    @Test
    public void testMod_NCalculation() {
        // Test if modulus N is correctly calculated as p * q
        BigInteger expectedN = rsa.getPrimeP().multiply(rsa.getPrimeQ());
        assertEquals(expectedN, rsa.getModulusN(), "Modulus N should be the product of primes P and Q.");
    }

    @Test
    public void testPublicKeyECoprimeWithPhiN() {
        // Test if public key E is coprime with phi(N)
        BigInteger phiN = rsa.getPrimeP().subtract(BigInteger.ONE).multiply(rsa.getPrimeQ().subtract(BigInteger.ONE));
        BigInteger gcd = phiN.gcd(rsa.getPublicKeyE());
        assertEquals(BigInteger.ONE, gcd, "Public key E should be coprime with phi(N).");
    }

    @Test
    public void testPrivateKeyDCalculation() {
        // Test if private key D is the modular inverse of E modulo phi(N)
        BigInteger phi_N = rsa.getPrimeP().subtract(BigInteger.ONE).multiply(rsa.getPrimeQ().subtract(BigInteger.ONE));
        BigInteger expectedD = rsa.getPublicKeyE().modInverse(phi_N);
        assertEquals(expectedD, rsa.getPrivateKeyD(), "Private key D should be the modular inverse of E modulo phi(N).");
    }

    @Test
    public void testEncryptionAndDecryption() {
        // Test encryption and decryption with a sample message
        BigInteger message = new BigInteger("123456789");
        BigInteger encryptedMessage = rsa.encrypt(message);
        BigInteger decryptedMessage = rsa.decrypt(encryptedMessage);

        assertEquals(message, decryptedMessage, "Decrypted message should match the original message.");
    }

    @Test
    public void testEncryptionAndDecryptionWithLargeMessage() {
        // Test encryption and decryption with a large message
        BigInteger message = new BigInteger("9876543210987654321098765432109876543210");
        BigInteger encryptedMessage = rsa.encrypt(message);
        BigInteger decryptedMessage = rsa.decrypt(encryptedMessage);

        assertEquals(message, decryptedMessage, "Decrypted message should match the original large message.");
    }

    @Test
    public void testEncryptionAndDecryptionWithZero() {
        // Test encryption and decryption with zero
        BigInteger message = BigInteger.ZERO;
        BigInteger encryptedMessage = rsa.encrypt(message);
        BigInteger decryptedMessage = rsa.decrypt(encryptedMessage);

        assertEquals(message, decryptedMessage, "Decrypted message should match the original message (zero).");
    }

    @Test
    public void testEncryptionAndDecryptionWithOne() {
        // Test encryption and decryption with one
        BigInteger message = BigInteger.ONE;
        BigInteger encryptedMessage = rsa.encrypt(message);
        BigInteger decryptedMessage = rsa.decrypt(encryptedMessage);

        assertEquals(message, decryptedMessage, "Decrypted message should match the original message (one).");
    }
}