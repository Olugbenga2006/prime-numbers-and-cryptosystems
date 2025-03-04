package primality2;

import java.io.*;
import java.net.*;

public class ChatServer {
  public static void main(String[] args) {
    try {
      // Generate RSA keys
      RSA rsa = new RSA(512, 5); // testing using simple 512-bit keys
      System.out.println("Server RSA keys generated.");

      // Start server socket
      ServerSocket serverSocket = new ServerSocket(3000);
      System.out.println("Server started. Now Waiting for client...");
      Socket socket = serverSocket.accept();
      System.out.println("Client has been connected.");

      //  input and output streams
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

      // Send public key (E and N) to the client
      output.println(rsa.getPublicKeyE() + "," + rsa.getModulusN());
      System.out.println("Public key has been sent to client.");

      // Receive encrypted messages and decrypt them
      while (true) {
        String encryptedMessage = input.readLine();
        if (encryptedMessage == null) break;

        String decryptedMessage = rsa.decryptText(encryptedMessage);
        System.out.println("Client: " + decryptedMessage);
      }

      // Close
      socket.close();
      serverSocket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
