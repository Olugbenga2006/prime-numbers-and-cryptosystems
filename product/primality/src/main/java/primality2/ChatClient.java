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

      // Receive public key from server
      String[] keyParts = input.readLine().split(",");
      BigInteger publicKeyE = new BigInteger(keyParts[0]);
      BigInteger modulusN = new BigInteger(keyParts[1]);
      System.out.println("Connected to server. Public key received.");

      // Create an RSA object with the received public key
      RSA rsa = new RSA(512, 5);
      rsa = new RSA(512, 5) {
        @Override
        public BigInteger getPublicKeyE() {
          return publicKeyE;
        }

        @Override
        public BigInteger getModulusN() {
          return modulusN;
        }
      };

      // Chat loop
      while (true) {
        System.out.print("You: ");
        String message = userInput.readLine();

        // a way to exit chat
        if (message.equalsIgnoreCase("exit")) break;

        // Encrypt message and send
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
