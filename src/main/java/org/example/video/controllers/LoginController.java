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
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = Data.getUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        if (user != null) {
            Data.setCurrentUser(user);
            openMainPage();
        } else {
            System.out.println("Invalid credentials");
        }
    }

    @FXML
    protected void onRegister() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/video/css/styles.css")).toExternalForm());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/video/css/styles.css")).toExternalForm());

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/youtube-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/video/css/styles.css")).toExternalForm());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}