package primality2;


import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class ChatClient {
  public static void main(String[] args) {
    try {
      // Connect to server
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

      // Receive public key from server
      String[] keyParts = input.readLine().split(",");
      BigInteger publicKeyE = new BigInteger(keyParts[0]);
      BigInteger modulusN = new BigInteger(keyParts[1]);
      System.out.println("Connected to server. Public key received.");

      // Create an RSA object with the received public key
      RSA rsa = new RSA(2048, 20);
      rsa.setModulusN(modulusN);
      rsa.setPublicKeyE(publicKeyE);

      // Chat loop
      while (true) {
        System.out.print("You: ");
        String message = userInput.readLine();
        if (message.equalsIgnoreCase("exit")) break;

        // Encrypt message with shared secret and send
        String encryptedMessage = rsa.encryptText(message);
        output.println(encryptedMessage);
        System.out.println("Sent (Encrypted): " + encryptedMessage);
      }

      // Cleanup
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}