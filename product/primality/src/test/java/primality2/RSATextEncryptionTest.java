package primality2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;

class RSATextEncryptionTest {
    private RSA rsa;

    @BeforeEach
    public void setUp() {
        // Initialize RSA with a bit length of 1024 and 20 Miller-Rabin iterations
        rsa = new RSA(2048, 20);
    }

    @Test
    public void testMapToNumber() {
        // Test mapping of a word to a number
        String text = "Hello, World!";
        BigInteger number = rsa.mapToNumber(text);
        assertEquals(new BigInteger("08313838415853234144383055"), number, "Mapping of 'HELLO WORLD' to number is incorrect.");
    }

    @Test
    public void testMapToText() {
        // Test mapping of a number back to text
        BigInteger number = new BigInteger("08313838415853234144383055");
        String text = rsa.mapToText(number);
        assertEquals("Hello, World!", text, "Mapping of number to text is incorrect.");
    }

    @Test
    public void testEncryptAndDecryptText() {
        // Test encryption and decryption of a word
        String originalText = "HELLO RSA";
        String encryptedText = rsa.encryptText(originalText);
        String decryptedText = rsa.decryptText(encryptedText);

        assertEquals(originalText, decryptedText, "Decrypted text should match the original text.");
    }

    @Test
    public void testEncryptAndDecryptSentence() {
        // Test encryption and decryption of a sentence
        String originalText = "THIS IS A SECRET MESSAGE";
        String encryptedText = rsa.encryptText(originalText);
        String decryptedText = rsa.decryptText(encryptedText);

        assertEquals(originalText, decryptedText, "Decrypted sentence should match the original sentence.");
    }

    @Test
    public void testEncryptAndDecryptWithSpace() {
        // Test encryption and decryption of a text with spaces
        String originalText = "SPACES ARE IMPORTANT";
        String encryptedText = rsa.encryptText(originalText);
        String decryptedText = rsa.decryptText(encryptedText);

        assertEquals(originalText, decryptedText, "Decrypted text with spaces should match the original text.");
    }

    @Test
    public void testEncryptAndDecryptWithAllLetters() {
        // Test encryption and decryption of all letters
        String originalText = "ABCDEFGHIJKLMNOPRSTUVWXYZ";
        String encryptedText = rsa.encryptText(originalText);
        String decryptedText = rsa.decryptText(encryptedText);

        assertEquals(originalText, decryptedText, "Decrypted text with all letters should match the original text.");
    }
    
    @Test
    public void testEncryptAndDecryptWithASENTENCEAGAIN() {
        // Test encryption and decryption of all letters
        String originalText = "do CaPs Matter i also need to implement punctuation elements like commas and question marks lol";
        String encryptedText = rsa.encryptText(originalText);
        String decryptedText = rsa.decryptText(encryptedText);

        assertEquals(originalText, decryptedText, "Decrypted text with all letters should match the original text.");
    }
    
    @Test
    public void testEncryptAndDecryptWithASENTENCEAGAINBUTBETTER() {
        // Test encryption and decryption of all letters
        String originalText = "CaPs Do maTTER a LOT aCtually. Now everything is in place as ALL ThingS should be: not only; do i not-know how to use punctuation,marks but !!! i also remember new stuff like br(ackets) . sir supervisor or final marker forgive this mistake";
        String encryptedText = rsa.encryptText(originalText);
        String decryptedText = rsa.decryptText(encryptedText);

        assertEquals(originalText, decryptedText, "Decrypted text with all letters should match the original text.");
    }
}