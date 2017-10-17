package com.example.chris.itunesmusicsearch;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Chris on 6/15/2017.
 */

public class MusicAdapter extends ArrayAdapter<Music>{
    List<Music> mData;
    Context mContext;
    int mResource;

    public MusicAdapter(Context context, int resource, List<Music> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }

        Music music = mData.get(position);
        NumberFormat formatter = new DecimalFormat("#0.00");

        TextView trackTitle = (TextView) convertView.findViewById(R.id.trackTitle);
        trackTitle.setText(music.getTrackName());
        TextView trackPrice = (TextView) convertView.findViewById(R.id.trackPrice);
        trackPrice.setText("$" + formatter.format(music.getTrackPrice()));
        TextView trackArtist = (TextView) convertView.findViewById(R.id.trackArtist);
        trackArtist.setText(music.getArtistName());
        TextView trackDate = (TextView) convertView.findViewById(R.id.trackDate);
        trackDate.setText(music.getReleaseDate());

        return convertView;
    }
}
