package org.example.video.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.video.Data;
import org.example.video.User;
import org.example.video.Video;

import java.io.IOException;
import java.util.Objects;

public class YouTubeController {
    @FXML
    private TextField searchField;

    @FXML
    private GridPane videoGrid;

    @FXML
    protected void onSubscriptions() {
        videoGrid.getChildren().clear();
        int row = 0;
        int col = 0;
        User currentUser = Data.getCurrentUser();
        for (Video video : Data.getVideos()) {
            if (currentUser.getSubscriptions().contains(video.getAuthor())) {
                VBox videoBox = new VBox(5);
                ImageView thumbnail = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(video.getThumbnailUrl()))));
                thumbnail.setFitWidth(320);
                thumbnail.setFitHeight(180);
                thumbnail.setOnMouseClicked(event -> openVideoPage(video));
                Text authorText = new Text(video.getAuthor().getUsername() + ": ");
                authorText.setStyle("-fx-font-weight: bold;");
                Label titleLabel = new Label(video.getTitle());
                HBox titleBox = new HBox(authorText, titleLabel);
                videoBox.getChildren().addAll(thumbnail, titleBox);
                videoGrid.add(videoBox, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    @FXML
    protected void onHome() {
        searchField.clear();
        videoGrid.getChildren().clear();
        int row = 0;
        int col = 0;
        for (Video video : Data.getVideos()) {
            VBox videoBox = new VBox(5);
            ImageView thumbnail = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(video.getThumbnailUrl()))));
            thumbnail.setFitWidth(320);
            thumbnail.setFitHeight(180);
            thumbnail.setOnMouseClicked(event -> openVideoPage(video));
            Text authorText = new Text(video.getAuthor().getUsername() + ": ");
            authorText.setStyle("-fx-font-weight: bold;");
            Label titleLabel = new Label(video.getTitle());
            HBox titleBox = new HBox(authorText, titleLabel);
            videoBox.getChildren().addAll(thumbnail, titleBox);
            videoGrid.add(videoBox, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }

    @FXML
    protected void onUpload() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/upload-video.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            Stage stage = (Stage) videoGrid.getScene().getWindow();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/video/css/styles.css")).toExternalForm());

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onSearch() {
        String query = searchField.getText().toLowerCase();
        videoGrid.getChildren().clear();
        int row = 0;
        int col = 0;
        for (Video video : Data.getVideos()) {
            if (video.getTitle().toLowerCase().contains(query)) {
                VBox videoBox = new VBox(5);
                ImageView thumbnail = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(video.getThumbnailUrl()))));
                thumbnail.setFitWidth(320);
                thumbnail.setFitHeight(180);
                thumbnail.setOnMouseClicked(event -> openVideoPage(video));
                Text authorText = new Text(video.getAuthor().getUsername() + ": ");
                authorText.setStyle("-fx-font-weight: bold;");
                Label titleLabel = new Label(video.getTitle());
                HBox titleBox = new HBox(authorText, titleLabel);
                videoBox.getChildren().addAll(thumbnail, titleBox);
                videoGrid.add(videoBox, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Initializing YouTubeController");
        int row = 0;
        int col = 0;
        for (Video video : Data.getVideos()) {
            VBox videoBox = new VBox(5);
            ImageView thumbnail = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(video.getThumbnailUrl()))));
            thumbnail.setFitWidth(320);
            thumbnail.setFitHeight(180);
            thumbnail.setOnMouseClicked(event -> openVideoPage(video));
            Text authorText = new Text(video.getAuthor().getUsername() + ": ");
            authorText.setStyle("-fx-font-weight: bold;");
            Label titleLabel = new Label(video.getTitle());
            HBox titleBox = new HBox(authorText, titleLabel);
            videoBox.getChildren().addAll(thumbnail, titleBox);
            videoGrid.add(videoBox, col, row);
            videoGrid.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/video/css/styles.css")).toExternalForm());

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
        System.out.println("YouTubeController initialized");
    }

    @FXML
    protected void onPersonalAccount() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/personal-account.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            scene.getStylesheets().add(getClass().getResource("/org/example/video/css/styles.css").toExternalForm());
            Stage stage = (Stage) videoGrid.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/org/example/video/css/styles.css").toExternalForm());

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openVideoPage(Video video) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/video-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            VideoPageController controller = fxmlLoader.getController();
            controller.setVideo(video);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/example/video/css/styles.css")).toExternalForm());
            Stage stage = (Stage) videoGrid.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}