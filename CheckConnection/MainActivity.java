package com.example.chris.day5checkconnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectedOnline()){
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_LONG).show();
                    //new GetData().execute("http://www.bbc.com/news/world-europe-40178183");
                    //new GetImage().execute("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
                    RequestParams params = new RequestParams("GET", "http://dev.theappsdr.com/lectures/params.php");
                    params.addParam("key1", "value1");
                    params.addParam("key2", "value2");
                    params.addParam("key3", "value3");
                    params.addParam("key4", "value4");
                    new GetDataWithParams().execute(params);
                } else{
                    Toast.makeText(MainActivity.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //READING TEXT FROM A URL
    private class GetData extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result != null){
                Log.d("demo", result);
            } else{
                Log.d("demo", "Null data");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            try{
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET"); //con.setRequestMethod("POST");
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    //Getting an image from online.
    private class GetImage extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if(result != null){
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                iv.setImageBitmap(result);
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            InputStream in = null;
            try{
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET"); //con.setRequestMethod("POST");
                in = con.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(in);
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    //GetDataWithParams
    private class GetDataWithParams extends AsyncTask<RequestParams, Void, String>{

        @Override
        protected String doInBackground(RequestParams... params) {
            BufferedReader reader = null;
            try{
                HttpURLConnection con = params[0].setupConnection();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result != null){
                Log.d("demo", result);
            } else{
                Log.d("demo", "Null data");
            }
        }
    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
