package primality2;

import java.io.*;
import java.net.*;
import java.math.BigInteger;

/**
 * ChatServer generates RSA keys, performs a Diffie-Hellman key exchange with the client,
 * receives encrypted messages, decrypts them using RSA, and prints them to the console.
 */
public class ChatServer {

  /**
   * Entry point for the server application.
   * 
   * This method:
   * - Generates RSA keys
   * - Waits for a client to connect
   * - Performs a Diffie-Hellman key exchange to establish a shared secret
   * - Sends the RSA public key to the client
   * - Receives and decrypts encrypted messages from the client
   */
  public static void main(String[] args) {
    try {
      // Generate RSA keys
      RSA rsa = new RSA(2048, 20);
      System.out.println("Server RSA keys generated.");

      // Set up server socket
      ServerSocket serverSocket = new ServerSocket(3000);
      System.out.println("Server started. Now waiting for client...");
      Socket socket = serverSocket.accept();
      System.out.println("Client has been connected.");

      // Perform Diffie-Hellman key exchange
      DiffieHellmanKeyGenerator dh = new DiffieHellmanKeyGenerator(2048);
      BigInteger serverPublicKey = dh.getPublicKey();
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
      BigInteger clientPublicKey = new BigInteger(input.readLine());
      output.println(serverPublicKey);
      BigInteger sharedSecret = dh.computeSharedKey(clientPublicKey);
      System.out.println("Shared secret established.");

      // Send RSA public key (E, N) to client
      output.println(rsa.getPublicKeyE() + "," + rsa.getModulusN());
      System.out.println("Public key has been sent to client.");

      // Receive and decrypt messages from client
      while (true) {
        String encryptedMessage = input.readLine();
        if (encryptedMessage == null) break;

        String decryptedMessage = rsa.decryptText(encryptedMessage);
        System.out.println("Client: " + decryptedMessage);
      }

      // Close connections
      socket.close();
      serverSocket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
