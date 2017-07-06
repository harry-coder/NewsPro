package com.example.harpreet.okhlee.pojo;

/**
 * Created by Harpreet on 10/04/2017.
 */

public class youtubeItems {
    private String id;
    private String title;
    private String nextPageToken;
    private String image;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
