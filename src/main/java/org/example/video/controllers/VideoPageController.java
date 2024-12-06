package org.example.video.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.video.Comment;
import org.example.video.Data;
import org.example.video.User;
import org.example.video.Video;

import java.io.IOException;
import java.util.List;

public class VideoPageController {
    @FXML
    private MediaView mediaView;
    @FXML
    private Label likeCount;
    @FXML
    private TextArea commentField;
    @FXML
    private VBox commentList;
    @FXML
    private Button subscribeButton;
    private MediaPlayer mediaPlayer;
    private Video video;
    @FXML
    private Label description;
    @FXML
    private Label subscriberCount;
    @FXML
    private Label channelName;

    @FXML
    public void initialize() {
        mediaView.setOnMouseClicked(event -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        });
    }

    public void setVideo(Video video) {
        this.video = video;
        updateUI();
    }

    private void updateUI() {
        channelName.setText(video.getAuthor().getUsername());
        if (video != null) {
            String videoUrl = getClass().getResource(video.getUrl()).toExternalForm();
            if (videoUrl != null) {
                System.out.println("Video URL: " + videoUrl);

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }

                Media media = new Media(videoUrl);
                mediaPlayer = new MediaPlayer(media);

                mediaPlayer.setOnError(() -> {
                    System.err.println("Error occurred: " + mediaPlayer.getError().getMessage());
                    mediaPlayer.getError().printStackTrace();
                });

                mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> {
                    if (newStatus == MediaPlayer.Status.READY) {
                        mediaPlayer.play();
                    } else if (newStatus == MediaPlayer.Status.PLAYING) {
                        System.out.println("Video is playing");
                    } else if (newStatus == MediaPlayer.Status.PAUSED) {
                        System.out.println("Video is paused");
                    } else if (newStatus == MediaPlayer.Status.STOPPED) {
                        System.out.println("Video is stopped");
                    } else if (newStatus == MediaPlayer.Status.DISPOSED) {
                        System.err.println("Failed to play video: " + mediaPlayer.getError().getMessage());
                    }
                });

                mediaView.setMediaPlayer(mediaPlayer);
            } else {
                System.err.println("Video file not found: " + video.getUrl());
            }

            likeCount.setText(Integer.toString(video.getLikes()));
            subscriberCount.setText(Integer.toString(video.getAuthor().getSubscribers().size()));
            description.setText(video.getDescription());
            description.getStyleClass().add("description");

            commentList.getChildren().clear();
            for (Comment comment : video.getComments()) {
                HBox commentBox = new HBox();
                commentBox.getStyleClass().add("comment-box");

                Label authorLabel = new Label(comment.getAuthor().getUsername() + ":");
                authorLabel.getStyleClass().add("comment-author");

                Label commentLabel = new Label(comment.getText());
                commentLabel.getStyleClass().add("comment-text");

                commentBox.getChildren().addAll(authorLabel, commentLabel);
                commentList.getChildren().add(commentBox);
            }

            // Update subscribe button text
            User currentUser = Data.getCurrentUser();
            if (video.getAuthor().getSubscribers().contains(currentUser)) {
                subscribeButton.setText("Unsubscribe");
            } else {
                subscribeButton.setText("Subscribe");
            }
        } else {
            System.err.println("Video is null");
        }
    }

    @FXML
    protected void onSubscribe() {
        if (Data.getCurrentUser()== video.getAuthor()) {
            return;
        }
        if (video != null) {
            List<User> subscribers = video.getAuthor().getSubscribers();
            User currentUser = Data.getCurrentUser();
            if (subscribers.contains(currentUser)) {
                subscribers.remove(currentUser);
                subscribeButton.setText("Подписаться");
                currentUser.getSubscriptions().remove(video.getAuthor());
                System.out.println("User with id " + currentUser.getId() + " unsubscribed");
                subscriberCount.setText(String.valueOf(subscribers.size()));
            } else {
                subscribers.add(currentUser);
                subscribeButton.setText("Отписаться");
                currentUser.addSubscription(video.getAuthor());
                System.out.println("User with id " + currentUser.getId() + " subscribed");
                subscriberCount.setText(String.valueOf(subscribers.size()));
            }
        }
    }

    @FXML
    protected void onLike() {
        if (video != null) {
            List<Integer> usersLiked = video.getUsersLiked();
            Integer currentUserId = Data.getCurrentUser().getId();

            if (usersLiked.contains(currentUserId)) {
                video.setLikes(video.getLikes() - 1);
                likeCount.setText(Integer.toString(video.getLikes()));
                usersLiked.remove(currentUserId);
                System.out.println("Пользователь с id " + currentUserId + " убрал лайк");
                return;
            }

            video.setLikes(video.getLikes() + 1);
            video.addLikedUser(Data.getCurrentUser());
            likeCount.setText(video.getLikes()+ "");
            System.out.println("Пользователь с id " + currentUserId + " поставил лайк");
        }
    }

    @FXML
    protected void onSubmitComment() {
        if (video != null) {
            String comment = commentField.getText();
            if (!comment.isEmpty()) {
                User currentUser = Data.getCurrentUser();
                Comment newComment = new Comment(comment, currentUser, video.getId());
                video.getComments().add(newComment);

                HBox commentBox = new HBox();
                commentBox.getStyleClass().add("comment-box");

                Label authorLabel = new Label(newComment.getAuthor().getUsername() + ":");
                authorLabel.getStyleClass().add("comment-author");

                Label commentLabel = new Label(newComment.getText());
                commentLabel.getStyleClass().add("comment-text");

                commentBox.getChildren().addAll(authorLabel, commentLabel);
                commentList.getChildren().add(commentBox);

                commentField.clear();
            }
        }
    }

    @FXML
    protected void onBack() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/video/fxml/youtube-view.fxml"));
            if (fxmlLoader.getLocation() == null) {
                throw new IllegalStateException("FXML file not found: /org/example/video/youtube-view.fxml");
            }
            Scene scene = new Scene(fxmlLoader.load(), Data.xSize, Data.ySize);
            Stage stage = (Stage) mediaView.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}