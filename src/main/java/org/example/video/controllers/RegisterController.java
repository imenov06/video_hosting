package org.example.video.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.video.Data;
import org.example.video.User;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean userExists = Data.getUsers().stream()
                .anyMatch(u -> u.getUsername().equals(username));
        if (!userExists) {
            Data.getUsers().add(new User(username, password));
            System.out.println("Registration successful");
            System.out.println(Data.getUsers());
            backToLogin();
        } else {
            System.out.println("Username already exists");
        }
    }

    @FXML
    protected void onBackToLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            scene.getStylesheets().add(getClass().getResource("/org/example/video/css/styles.css").toExternalForm());
            Stage stage = (Stage) usernameField.getScene().getWindow();

            stage.setScene(scene);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backToLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            scene.getStylesheets().add(getClass().getResource("/org/example/video/css/styles.css").toExternalForm());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}