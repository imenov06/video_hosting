package org.example.video;

public class Comment {
    private static int counterId = 0;
    private int id;
    private String text;
    private User author;
    private int videoId;


    public Comment(String text, User author, int videoId) {
        this.id = counterId;
        this.text = text;
        this.author = author;
        this.videoId = videoId;
        counterId++;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public int getVideoId() {
        return videoId;
    }



}
