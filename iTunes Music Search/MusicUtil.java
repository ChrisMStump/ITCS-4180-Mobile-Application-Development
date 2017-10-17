package com.example.chris.itunesmusicsearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chris on 6/13/2017.
 */

public class MusicUtil {
    static public class MusicJSONParser{
        static ArrayList<Music> parseMusic(String in) throws JSONException {
            ArrayList<Music> musicList = new ArrayList<Music>();

            JSONObject root = new JSONObject(in);
            JSONArray musicJSONArray = root.getJSONArray("results");

            for(int i = 0; i < musicJSONArray.length(); i++){
                JSONObject musicJSONObject = musicJSONArray.getJSONObject(i);
                Music music = Music.createMusic(musicJSONObject);
                musicList.add(music);

            }
            return musicList;
        }
    }
}
