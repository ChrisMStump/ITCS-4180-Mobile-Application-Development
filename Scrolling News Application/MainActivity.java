/*
In Class Assignment 06
Christopher Stump
800870867
 */
package com.example.chris.scrollingnewsapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.chris.scrollingnewsapplication.R.id.scrollView;

public class MainActivity extends AppCompatActivity implements GetNewsAsyncTask.IData {

    ProgressDialog progress;
    ArrayList<News> newsList;
    ScrollView scrollView;
    ImageView imageView;
    TextView textView;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        progress = new ProgressDialog(MainActivity.this);
        progress.setCancelable(false);
        progress.setTitle("Loading News...");

        progress.show();
        new GetNewsAsyncTask(MainActivity.this).execute("http://rss.nytimes.com/services/xml/rss/nyt/World.xml");


    }

    public void postNews(ArrayList<News> result){

        for(News news: result){
            listItemUI item = new listItemUI(this);
            View itemView = (View)item;
            item.title.setText(news.getTitle());
            String url = news.getImageURL();
            Log.d("demo", url+"");
            if(url != null) {
                Picasso.with(getApplicationContext()).load(url).into(item.image);
            } else{
                Picasso.with(getApplicationContext()).load(url).placeholder(R.drawable.blank).into(item.image);
            }
            container.addView(itemView);

            itemView.setOnClickListener((View.OnClickListener) new myOnClickListener(getApplicationContext(), news));
        }
        scrollView.addView(container);
        progress.dismiss();


    }
}
