package com.example.chris.scrollingnewsapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Chris on 6/8/2017.
 */

public class GetNewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {

    IData activity;

    public GetNewsAsyncTask(Context context){
        activity= (IData) context;

    }
    @Override
    protected ArrayList<News> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                return NewsUtil.NewsSAXParser.parseNews(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> result) {
        super.onPostExecute(result);
        if(result!= null) {
            //Log.d("demo", result.toString());
        }
        activity.postNews(result);
    }

    public interface IData{
        public void postNews(ArrayList<News> list);
    }
}
