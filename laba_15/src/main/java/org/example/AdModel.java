package org.example;

import java.util.Date;
import java.util.HashSet;

public class AdModel implements Comparable<AdModel> {

    private final String title;
    private final String text;
    private final String username;
    private final Date date;
    private HashSet<String> likes = new HashSet<>();

    public AdModel(String title, String text, String username) {

        this.title = title;
        this.text = text;
        this.username = username;
        date = new Date();

    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }

    public Date getDate() {
        return date;
    }

    public int getLikeCounter() {
        return likes.size();
    }

    public void like(String username) {
        likes.add(username);
    }

    @Override
    public int compareTo(AdModel o) {
        return Integer.compare(getLikeCounter(), o.getLikeCounter());
    }
}
