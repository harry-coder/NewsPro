package com.example.harpreet.okhlee.pojo;

/**
 * Created by Harpreet on 02/04/2017.
 */

public class NewsItems {

    String url;
    String title_full;
    String main_image;

    String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }



    /*public NewsItems(String url, String title_full, String main_image) {
        this.url = url;
        this.title_full = title_full;
        this.main_image = main_image;
    }*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle_full() {
        return title_full;
    }

    public void setTitle_full(String title_full) {
        this.title_full = title_full;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }
}
