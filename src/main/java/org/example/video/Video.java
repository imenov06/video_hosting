package org.example.video;

import java.util.ArrayList;

public class Video {
    private static int counterId = 0;
    private final int id;
    private final String title;
    private final String description;
    private final String url;
    private final String thumbnailUrl;
    private int likes = 0;
    private final User author;
    private final ArrayList<Integer> usersLiked = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();

    public Video(String title, String description, String url, String thumbnailUrl, User author) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.id = counterId;
        this.author = author;
        counterId++;
        author.addUserVideo(this);
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ArrayList<Integer> getUsersLiked() {
        return usersLiked;
    }


    public void addLikedUser(User user) {
        usersLiked.add(user.getId());
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public User getAuthor() {
        return author;
    }


    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", likes=" + likes +
                ", usersLiked=" + usersLiked +
                ", comments=" + comments + '\''+
                ", author=" + author +
                '}';
    }

}
