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
import java.net.Socket;

public class ChatClientGUI extends Application {
  private TextArea chatArea;
  private TextField inputField;
  private PrintWriter output;
  private RSA rsa;

  @Override
  public void start(Stage primaryStage) {
    // GUI Setup
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
    primaryStage.setTitle("Secure Chat Client");
    primaryStage.show();

    // Networking and Crypto
    new Thread(this::initializeConnection).start();
  }

  private void initializeConnection() {
    try {
      Socket socket = new Socket("localhost", 3000);
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      output = new PrintWriter(socket.getOutputStream(), true);

      DiffieHellmanKeyGenerator dh = new DiffieHellmanKeyGenerator(2048);
      BigInteger clientPublicKey = dh.getPublicKey();
      output.println(clientPublicKey);
      BigInteger serverPublicKey = new BigInteger(input.readLine());
      BigInteger sharedSecret = dh.computeSharedKey(serverPublicKey);
      appendToChat("Shared secret established.");

      // RSA Setup
      String[] keyParts = input.readLine().split(",");
      BigInteger publicKeyE = new BigInteger(keyParts[0]);
      BigInteger modulusN = new BigInteger(keyParts[1]);

      rsa = new RSA(2048, 20);
      rsa.setModulusN(modulusN);
      rsa.setPublicKeyE(publicKeyE);

      // Receive messages
      String line;
      while ((line = input.readLine()) != null) {
        String decrypted = rsa.decryptText(line);
        appendToChat("Server: " + decrypted);
      }
    } catch (Exception e) {
      e.printStackTrace();
      appendToChat("Connection error: " + e.getMessage());
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
