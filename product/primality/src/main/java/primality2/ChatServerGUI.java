package primality2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServerGUI extends Application {
  private TextArea chatArea;
  private TextField inputField;
  private PrintWriter output;
  private RSA rsa;

  @Override
  public void start(Stage primaryStage) {
    // Setup GUI
    chatArea = new TextArea();
    chatArea.setEditable(false);
    chatArea.setWrapText(true);

    inputField = new TextField();
    inputField.setPromptText("Type your message...");
    inputField.setOnAction(e -> sendMessage());

    Button sendButton = new Button("Send");
    sendButton.setOnAction(e -> sendMessage());

    HBox inputBox = new HBox(10, inputField, sendButton);
    inputBox.setPadding(new Insets(10));

    BorderPane root = new BorderPane();
    root.setCenter(chatArea);
    root.setBottom(inputBox);

    Scene scene = new Scene(root, 600, 400);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Secure Chat Server");
    primaryStage.show();

    // Start server thread
    new Thread(this::startServer).start();
  }

  private void startServer() {
    try {
      // RSA Key Generation
      rsa = new RSA(2048, 20);
      appendToChat("Server RSA keys generated.");

      // Setup server socket
      ServerSocket serverSocket = new ServerSocket(3000);
      appendToChat("Waiting for client to connect...");
      Socket socket = serverSocket.accept();
      appendToChat("Client connected!");

      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      output = new PrintWriter(socket.getOutputStream(), true);

      // Diffie-Hellman Key Exchange
      DiffieHellmanKeyGenerator dh = new DiffieHellmanKeyGenerator(2048);
      BigInteger serverPublicKey = dh.getPublicKey();
      BigInteger clientPublicKey = new BigInteger(input.readLine());
      output.println(serverPublicKey);
      BigInteger sharedSecret = dh.computeSharedKey(clientPublicKey);
      appendToChat("Shared secret established.");

      // Send RSA public key to client
      output.println(rsa.getPublicKeyE() + "," + rsa.getModulusN());
      appendToChat("RSA public key sent to client.");

      // Start receiving messages
      String line;
      while ((line = input.readLine()) != null) {
        String decrypted = rsa.decryptText(line);
        appendToChat("Client: " + decrypted);
      }

      socket.close();
      serverSocket.close();
    } catch (Exception e) {
      e.printStackTrace();
      appendToChat("Server error: " + e.getMessage());
    }
  }

  private void sendMessage() {
    String message = inputField.getText().trim();
    if (!message.isEmpty() && rsa != null && output != null) {
      try {
        String encrypted = rsa.encryptText(message);
        output.println(encrypted);
        appendToChat("You: " + message);
        inputField.clear();
      } catch (Exception e) {
        appendToChat("Encryption error: " + e.getMessage());
      }
    }
  }

  private void appendToChat(String message) {
    chatArea.appendText(message + "\n");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
