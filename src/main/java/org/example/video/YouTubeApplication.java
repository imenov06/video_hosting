package org.example.video;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class YouTubeApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/video/css/styles.css")).toExternalForm());
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/video/images/ico.png"))));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}