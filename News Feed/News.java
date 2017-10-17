package com.example.chris.newsfeed;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chris on 6/6/2017.
 */

public class News {
    String urlToImage, title, author, description, publishedAt;

    static public News createNews(JSONObject js){
        News news = new News();

        try {
            news.setUrlToImage(js.getString("urlToImage"));
            news.setDescription(js.getString("description"));
            news.setTitle(js.getString("title"));
            news.setAuthor(js.getString("author"));
            news.setPublishedAt(js.getString("publishedAt"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

    public News() {

    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "News{" +
                "urlToImage='" + urlToImage + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }

}
