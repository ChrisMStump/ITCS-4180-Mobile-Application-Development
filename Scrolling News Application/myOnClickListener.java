package com.example.chris.scrollingnewsapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Chris on 6/8/2017.
 */

public class myOnClickListener implements View.OnClickListener {
    News news;
    Context context;
    Intent intent;

    public myOnClickListener(Context context, News news){
        this.context = context;
        this.news = news;

    }


    @Override
    public void onClick(View v) {
        intent = new Intent(v.getContext(), DisplayNews.class);
        intent.putExtra("newsObject", news);
        v.getContext().startActivity(intent);
    }
}
