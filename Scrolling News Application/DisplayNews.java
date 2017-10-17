package com.example.chris.scrollingnewsapplication;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class DisplayNews extends AppCompatActivity {
    TextView articleTitle, pubDate, descText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);

        final News news = (News) getIntent().getExtras().getSerializable("newsObject");

        articleTitle = (TextView) findViewById(R.id.articleTitle);
        pubDate = (TextView) findViewById(R.id.pubDate);
        descText = (TextView) findViewById(R.id.descText);
        imageView = (ImageView) findViewById(R.id.articleImage);

        if(news.getTitle() != null) {
            articleTitle.setText(news.getTitle());
        }
        if(news.getPubDate() != null) {
            news.setPubDate(getDate(news.getPubDate()));
            pubDate.setText(news.getPubDate());
        }
        if(news.getImageURL() != null) {
            Picasso.with(getBaseContext()).load(news.getImageURL()).into(imageView);
        } else{
            imageView.setImageBitmap(null);
        }
        if(news.getDescription() != null) {
            descText.setText(news.getDescription());
        }

    }

    public String getDate(String pubDate){
        StringTokenizer st = new StringTokenizer(pubDate);
        st.nextToken();
        String day = st.nextToken();
        String month = st.nextToken();
        String year = st.nextToken();
        String time = st.nextToken();

        try{
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM");
            Calendar cal = Calendar.getInstance();
            cal.setTime(inputFormat.parse(month));
            SimpleDateFormat outputFormat = new SimpleDateFormat("MM");
            month = outputFormat.format(cal.getTime());
            //Log.d("demo", month+"");

            SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = inFormat.parse(time);
            time = new SimpleDateFormat("hh:mm a").format(date);
            //Log.d("demo", time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return month + "/" + day + "/" + year + " " + time;
    }
}
