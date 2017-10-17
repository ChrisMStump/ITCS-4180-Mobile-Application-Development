package com.example.chris.itunesmusicsearch;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Chris on 6/13/2017.
 */

public class Music implements Serializable{
    String trackName, primaryGenreName, artistName, collectionName, imageURL, releaseDate;
    double trackPrice, collectionPrice;

    public Music(){

    }

    static public Music createMusic(JSONObject js){
        Music music = new Music();

        try {
            music.setTrackName(js.getString("trackName"));
            music.setPrimaryGenreName(js.getString("primaryGenreName"));
            music.setArtistName(js.getString("artistName"));
            music.setCollectionName(js.getString("collectionName"));
            music.setTrackPrice(js.getDouble("trackPrice"));
            music.setCollectionPrice(js.getDouble("collectionPrice"));
            music.setImageURL(js.getString("artworkUrl100"));
            music.setReleaseDate(js.getString("releaseDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return music;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackame) {
        this.trackName = trackame;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public double getCollectionPrice() {
        return collectionPrice;
    }

    public void setCollectionPrice(double collectionPrice) {
        this.collectionPrice = collectionPrice;
    }

    @Override
    public String toString() {
        return "Music{" +
                "trackame='" + trackName + '\'' +
                ", primaryGenreName='" + primaryGenreName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", trackPrice=" + trackPrice +
                ", collectionPrice=" + collectionPrice +
                '}';
    }
}
