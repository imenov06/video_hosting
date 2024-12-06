package org.example.video;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static int counterId = 0;
    private final int id;
    private final String username;
    private final String password;
    private final List<User> subscriptions = new ArrayList<>();
    private final List<User> subscribers = new ArrayList<>();
    private final List<Video> userVideos = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = counterId;
        counterId++;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<User> getSubscriptions() {
        return subscriptions;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void addSubscription(User user) {
        subscriptions.add(user);
    }

    public void addSubscriber(User user) {
        subscribers.add(user);
    }

    public void addUserVideo(Video video) {
        userVideos.add(video);
    }

    public List<Video> getUserVideos() {
        return userVideos;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }


}
