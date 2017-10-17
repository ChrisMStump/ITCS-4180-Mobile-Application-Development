package com.example.chris.itunesmusicsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Chris on 6/8/2017.
 */

public class listItemUI extends LinearLayout {
    public TextView title, price, artist, date;

    public listItemUI(Context context){
        super(context);
        inflateXML(context);
    }

    private void inflateXML(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listitem, this);
        this.title = (TextView) findViewById(R.id.trackTitle);
        this.price = (TextView) findViewById(R.id.trackPrice);
        this.artist = (TextView) findViewById(R.id.trackArtist);
        this.date = (TextView) findViewById(R.id.trackDate);
    }
}
