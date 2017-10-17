package com.example.chris.scrollingnewsapplication;

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
    public TextView title;
    public ImageView image;

    public listItemUI(Context context){
        super(context);
        inflateXML(context);
    }

    private void inflateXML(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listitem, this);
        this.title = (TextView) findViewById(R.id.textView);
        this.image = (ImageView) findViewById(R.id.imageView);
    }
}
