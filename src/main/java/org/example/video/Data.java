package org.example.video;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static final List<Video> videos = new ArrayList<>();
    private static final List<User> users = new ArrayList<>();
    private static User currentUser;
    public static final int xSize = 1530 + 5;
    public static final int ySize = xSize * 9 / 16 - 25;

    static {
        users.add(new User("Иван Иванов", "1"));
        users.add(new User("Петр Петров", "2"));
        users.add(new User("Василий Васильев", "3"));
        users.add(new User("Артем Матвеев", "4"));


        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);
        User user4 = users.get(3);

        // Initialize sample videos
        Video video1 = new Video("Политика сегодня",
                "Анализ последних событий в мире и обсуждение их влияния на глобальную экономику.",
                "/org/example/video/videos/sample1.mp4",
                "/org/example/video/images/img11.jpg",
                user1);

        Video video2 = new Video("Топ-10 игр 2024 года",
                "Обзор лучших игр года. Узнайте, что обязательно стоит поиграть.",
                "/org/example/video/videos/sample2.mp4",
                "/org/example/video/images/img_9.png",
                user1);

        Video video3 = new Video("Новинки технологий",
                "Революционные гаджеты, которые изменят вашу жизнь. Полный обзор.",
                "/org/example/video/videos/sample3.mp4",
                "/org/example/video/images/img_1.png",
                user1);

        Video video4 = new Video("Как создать успешный стартап",
                "Пошаговая инструкция для предпринимателей: от идеи до реализации.",
                "/org/example/video/videos/sample4.mp4",
                "/org/example/video/images/img_2.png",
                user2);



        video1.getComments().add(new Comment("Очень полезный анализ, спасибо!", user2, video1.getId()));
        video2.getComments().add(new Comment("Отличный список! Уже скачал пару игр.", user3, video2.getId()));
        video3.getComments().add(new Comment("Круто! Хочу себе такой гаджет.", user1, video3.getId()));
        video4.getComments().add(new Comment("Мотивирует начать свой проект!", user3, video4.getId()));

        // Add more comments to videos
        video1.getComments().add(new Comment("Политика сложная тема, но вы объяснили доступно.", user3, video1.getId()));
        video1.getComments().add(new Comment("Как вы считаете, к чему приведут эти изменения?", user2, video1.getId()));

        video2.getComments().add(new Comment("Крутой список, но я бы добавил ещё пару игр.", user1, video2.getId()));
        video2.getComments().add(new Comment("Жду продолжения с более подробным обзором.", user3, video2.getId()));

        video3.getComments().add(new Comment("Очень информативно, хочу купить одну из этих новинок!", user1, video3.getId()));
        video3.getComments().add(new Comment("Где можно найти более подробную информацию о гаджетах?", user2, video3.getId()));

        video4.getComments().add(new Comment("Отличное видео, пригодится для моего проекта!", user1, video4.getId()));
        video4.getComments().add(new Comment("А что делать, если нет опыта в этой сфере?", user3, video4.getId()));




        videos.add(video1);
        videos.add(video2);
        videos.add(video3);
        videos.add(video4);


    }

    public static List<Video> getVideos() {
        return videos;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static void deleteVideo(Video video) {
        videos.remove(video);
    }
}