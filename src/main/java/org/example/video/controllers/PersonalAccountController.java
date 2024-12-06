package org.example.video.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.example.video.Data;
import org.example.video.User;
import org.example.video.Video;

import java.io.IOException;

public class PersonalAccountController {
    @FXML
    private Label subscriberCountLabel;
    @FXML
    private ListView<GridPane> subscriptionsListView;
    @FXML
    private ListView<GridPane> videosListView; // Изменено на ListView<GridPane>
    @FXML
    private Label totalLikesLabel;
    @FXML
    private Label channelNameLabel;

    private void unsubscribeUser(User user) {
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            currentUser.getSubscriptions().remove(user);
            user.getSubscribers().remove(currentUser);
            subscriptionsListView.getItems().removeIf(hbox -> {
                Label label = (Label) hbox.getChildren().get(0);
                return label.getText().equals(user.getUsername());
            });
        }
    }

    @FXML
    public void initialize() {
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            subscriberCountLabel.setText("Подписчики: " + currentUser.getSubscribers().size());
            subscriberCountLabel.setStyle("-fx-font-size: 16px; -fx-alignment: center;");
            channelNameLabel.setText("Ваш канал: " + currentUser.getUsername());
            channelNameLabel.setStyle("-fx-font-size: 16px; -fx-alignment: center;");
            subscriptionsListView.getItems().addAll(currentUser.getSubscriptions().stream().map(user -> {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10); // Set horizontal gap between columns

                Label userLabel = new Label(user.getUsername());
                userLabel.setStyle("-fx-font-size: 16px; -fx-alignment: center-left;");
                GridPane.setHgrow(userLabel, Priority.ALWAYS); // Allow the label to grow and take up remaining space
                GridPane.setConstraints(userLabel, 0, 0); // Set the label in the first column

                Button unsubscribeButton = new Button("Отписаться");
                unsubscribeButton.setPrefHeight(20); // Set the preferred height
                unsubscribeButton.setStyle("-fx-alignment: center-right; -fx-background-color: #ff5722;");
                unsubscribeButton.setOnAction(event -> unsubscribeUser(user));
                GridPane.setConstraints(unsubscribeButton, 1, 0); // Set the button in the second column

                gridPane.getChildren().addAll(userLabel, unsubscribeButton);
                return gridPane;
            }).toList());
            videosListView.getItems().addAll(currentUser.getUserVideos().stream().map(video -> {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10); // Set horizontal gap between columns

                Label videoLabel = new Label(video.getTitle() + " (" + video.getLikes() + " лайк)");
                videoLabel.setStyle("-fx-font-size: 16px; -fx-alignment: center-left;");
                GridPane.setHgrow(videoLabel, Priority.ALWAYS); // Allow the label to grow and take up remaining space
                GridPane.setConstraints(videoLabel, 0, 0); // Set the label in the first column

                Button deleteButton = new Button("Удалить");
                deleteButton.setPrefHeight(20); // Set the preferred height
                deleteButton.setStyle("-fx-alignment: center-right;  -fx-background-color: #ff5722;");
                deleteButton.setOnAction(event -> deleteVideo(video));
                GridPane.setConstraints(deleteButton, 1, 0); // Set the button in the second column

                gridPane.getChildren().addAll(videoLabel, deleteButton);
                return gridPane;
            }).toList());
            int totalLikes = currentUser.getUserVideos().stream().mapToInt(Video::getLikes).sum();
            totalLikesLabel.setText("Общее количество лайков: " + totalLikes);
            totalLikesLabel.setStyle("-fx-font-size: 16px; -fx-alignment: center;");
        }
    }

    private void deleteVideo(Video video) {
        User currentUser = Data.getCurrentUser();
        if (currentUser != null) {
            currentUser.getUserVideos().remove(video);
            Data.deleteVideo(video);
            videosListView.getItems().removeIf(gridPane -> {
                Label label = (Label) gridPane.getChildren().get(0);
                return label.getText().startsWith(video.getTitle());
            });
        }
    }

    @FXML
    protected void onBack() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/youtube-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            scene.getStylesheets().add(getClass().getResource("/org/example/video/css/styles.css").toExternalForm());
            Stage stage = (Stage) subscriberCountLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            scene.getStylesheets().add(getClass().getResource("/org/example/video/css/styles.css").toExternalForm());
            Stage stage = (Stage) subscriberCountLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}