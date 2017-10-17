package com.example.chris.newsfeed;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Chris on 6/6/2017.
 */

public class GetNewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>>{

    IData activity;

    public GetNewsAsyncTask(Context context){
        activity= (IData) context;

    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while(line != null){
                    sb.append(line);
                    line = reader.readLine();
                }
                return NewsUtil.newsJSONParser.parseNews(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> result) {
        super.onPostExecute(result);
        if(result != null){
            activity.postArticles(result);
        }
    }

    public interface IData{
        public void postArticles(ArrayList<News> result);

    }
}


