package primality2;

import java.io.*;
import java.math.BigInteger;
import java.net.*;

/**
 * ChatClient connects to the ChatServer, establishes a shared key using Diffie-Hellman,
 * receives the server's RSA public key, and sends RSA-encrypted messages to the server.
 */
public class ChatClient {

  /**
   * Entry point for the client application.
   * 
   * This method:
   * - Connects to the server on localhost:3000
   * - Performs Diffie-Hellman key exchange to generate a shared secret
   * - Receives the server's RSA public key (E and N)
   * - Uses RSA to encrypt user messages and sends them to the server
   */
  public static void main(String[] args) {
    try {
      // Connect to the server
      Socket socket = new Socket("localhost", 3000);
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

      // Perform Diffie-Hellman key exchange
      DiffieHellmanKeyGenerator dh = new DiffieHellmanKeyGenerator(2048);
      BigInteger clientPublicKey = dh.getPublicKey();
      output.println(clientPublicKey);
      BigInteger serverPublicKey = new BigInteger(input.readLine());
      BigInteger sharedSecret = dh.computeSharedKey(serverPublicKey);
      System.out.println("Shared secret established.");

      // Receive RSA public key from server
      String[] keyParts = input.readLine().split(",");
      BigInteger publicKeyE = new BigInteger(keyParts[0]);
      BigInteger modulusN = new BigInteger(keyParts[1]);
      System.out.println("Connected to server. Public key received.");

      // Initialize RSA with the received public key
      RSA rsa = new RSA(2048, 20);
      rsa.setModulusN(modulusN);
      rsa.setPublicKeyE(publicKeyE);

      // User input and message sending loop
      while (true) {
        System.out.print("You: ");
        String message = userInput.readLine();
        if (message.equalsIgnoreCase("exit")) break;

        // Encrypt and send message
        String encryptedMessage = rsa.encryptText(message);
        output.println(encryptedMessage);
        System.out.println("Sent (Encrypted): " + encryptedMessage);
      }

      // Close the connection
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
