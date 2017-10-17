/*
Christopher Stump
In Class 05
800870867
 */
package com.example.chris.newsfeed;

import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements GetNewsAsyncTask.IData {
    ArrayList<News> newsList;
    TextView tv;
    ImageView iv;
    int iterator;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.newsText);
        iv = (ImageView) findViewById(R.id.newsImage);

        newsList = new ArrayList<News>();
        iterator = 0;


        final Spinner spinner = (Spinner) findViewById(R.id.userChoice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.news_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.getNewsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectedOnline()){
                    progress = new ProgressDialog(MainActivity.this);
                    progress.setCancelable(false);
                    progress.setTitle("Getting News");
                    progress.show();
                    if(spinner.getSelectedItem().toString().equals("CNN")){
                        Toast.makeText(MainActivity.this, "Clicked CNN.", Toast.LENGTH_SHORT).show();
                        new GetNewsAsyncTask(MainActivity.this).execute("https://newsapi.org/v1/articles?apiKey=62c1191b19e746a3880db8bbdaa60b1b&source=cnn");

                    } else if(spinner.getSelectedItem().toString().equals("BBC")){
                        Toast.makeText(MainActivity.this, "Clicked BBC.", Toast.LENGTH_SHORT).show();
                        new GetNewsAsyncTask(MainActivity.this).execute("https://newsapi.org/v1/articles?apiKey=62c1191b19e746a3880db8bbdaa60b1b&source=bbc-news");

                    } else{
                        Toast.makeText(MainActivity.this, "Please select a valid choice.", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(MainActivity.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.firstButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList.isEmpty()){
                    Toast.makeText(MainActivity.this, "No news to display.", Toast.LENGTH_SHORT).show();
                } else if(iterator == 0){
                    Toast.makeText(MainActivity.this, "Already on first news.", Toast.LENGTH_SHORT).show();
                } else{
                    News news = newsList.get(0);
                    new GetImage().execute(news.getUrlToImage());
                    setupText(news);
                    iterator = 0;
                }
                Log.d("iterator", iterator +"");
            }
        });

        findViewById(R.id.previousButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList.isEmpty()){
                    Toast.makeText(MainActivity.this, "No news to display.", Toast.LENGTH_SHORT).show();
                } else if(iterator == 0){
                    Toast.makeText(MainActivity.this, "No previous news.", Toast.LENGTH_SHORT).show();
                } else if(iterator >= 1){
                    News news = newsList.get(iterator - 1);
                    new GetImage().execute(news.getUrlToImage());
                    setupText(news);
                    iterator = iterator - 1;
                }
                Log.d("iterator", iterator +"");
            }
        });

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList.isEmpty()){
                    Toast.makeText(MainActivity.this, "No news to display.", Toast.LENGTH_SHORT).show();
                } else if((newsList.size() - (iterator + 1)) >= 1){
                    News news = newsList.get(iterator + 1);
                    new GetImage().execute(news.getUrlToImage());
                    setupText(news);
                    iterator = iterator + 1;
                } else if(iterator == newsList.size()-1){
                    Toast.makeText(MainActivity.this, "No more news to display.", Toast.LENGTH_SHORT).show();
                }
                Log.d("iterator", iterator +"");
            }
        });

        findViewById(R.id.lastButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList.isEmpty()){
                    Toast.makeText(MainActivity.this, "No news to display.", Toast.LENGTH_SHORT).show();
                } else if(iterator == (newsList.size() - 1)){
                    Toast.makeText(MainActivity.this, "Already on last news.", Toast.LENGTH_SHORT).show();
                } else{
                    News news = newsList.get(newsList.size()-1);
                    new GetImage().execute(news.getUrlToImage());
                    setupText(news);
                    iterator = newsList.size()-1;
                }
                Log.d("iterator", iterator +"");
            }
        });

        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    @Override
    public void postArticles(ArrayList<News> result) {
        newsList = result;
        progress.dismiss();
        if(!newsList.isEmpty()) {
            iterator = 0;
            News news = newsList.get(0);
            new GetImage().execute(news.getUrlToImage());
            setupText(news);
        }
    }

    public void setupText(News news){
        String title = news.getTitle();
        String author = news.getAuthor();
        String date = news.getPublishedAt();
        String description = news.getDescription();
        String text = title + "\nAuthor: " + author + "\nPublished on: " + date + "\n\nDescription:\n\n" + description;
        tv.setText(text);
    }

    private class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if(result != null){
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
}
