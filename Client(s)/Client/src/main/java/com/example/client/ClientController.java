package com.example.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class ClientController implements Runnable {
    public TextField inputToSend;
    public TextArea textRecieved;
    public TextField NAME;

    final int port = 8082;
    DataOutputStream out;
    BufferedReader in;
    String path;

    public void ConnectToServer(ActionEvent actionEvent) {
        try {
            path = "localhost";
            Socket sock = new Socket(path, port);
            out = new DataOutputStream(sock.getOutputStream());
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (Exception e) {
            System.out.println("client lite input output connection error");
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    public void SendText(ActionEvent actionEvent) {
        String plaintext = inputToSend.getText();
        String name = NAME.getText();
        String stringToSend = name + " : " + plaintext;

        try {
            out.writeBytes(stringToSend + "\n");
            out.flush();
            System.out.println(NAME.getText() + " sent " + plaintext);
        } catch (Exception e) {
            System.out.println("client send error");
            e.printStackTrace();
        }
    }

    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                if (!message.equals(NAME.getText())) {
                    String finalMessage = message;
                    Platform.runLater(
                            () -> textRecieved.setText(textRecieved.getText() + "\n" + finalMessage)
                    );
                    System.out.println(NAME.getText() + " received: " + message);
                }
            }
        } catch (Exception e) {
            System.out.println("client receiving error");
        }
    }
}
