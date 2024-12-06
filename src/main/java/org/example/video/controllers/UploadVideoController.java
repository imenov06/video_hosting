package org.example.video.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.video.Data;
import org.example.video.Video;

import java.io.File;
import java.io.IOException;

public class UploadVideoController {
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button thumbnailButton;
    @FXML
    private Button uploadButton;

    private File selectedFile;
    private File selectedThumbnail;

    @FXML
    protected void onSelectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4"));
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            uploadButton.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    protected void onSelectThumbnail() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedThumbnail = fileChooser.showOpenDialog(thumbnailButton.getScene().getWindow());
        if (selectedThumbnail != null) {
            thumbnailButton.setText(selectedThumbnail.getName());
        }
    }

    @FXML
    protected void onUpload() throws IOException {
        String title = titleField.getText();
        String description = descriptionField.getText();

        if (selectedFile != null && selectedThumbnail != null && !title.isEmpty()) {
            Video newVideo = new Video(title, description, "/org/example/video/videos/" + selectedFile.getName(),
                    "/org/example/video/images/" + selectedThumbnail.getName(), Data.getCurrentUser());
            Data.getVideos().add(newVideo);
            backToMain();
        } else {
            System.out.println("Title, video file, and thumbnail are required");
        }
    }

    @FXML
    protected void onBack() {
        backToMain();
    }

    private void backToMain() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/youtube-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            scene.getStylesheets().add(getClass().getResource("/org/example/video/css/styles.css").toExternalForm());
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}