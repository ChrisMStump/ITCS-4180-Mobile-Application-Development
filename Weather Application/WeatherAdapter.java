package com.example.chris.weatherapplication;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.weatherapplication.Weather;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Chris on 6/19/2017.
 */

public class WeatherAdapter extends ArrayAdapter<Weather>{
    List<Weather> mData;
    Context mContext;
    int mResource;

    public WeatherAdapter(Context context, int resource, List<Weather> objects) {
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

        Weather weather = mData.get(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
        Picasso.with(getContext()).load(weather.getIconUrl()).into(imageView);
        TextView timeField = (TextView) convertView.findViewById(R.id.timeField);
        timeField.setText(weather.getTime());
        TextView descField = (TextView) convertView.findViewById(R.id.descField);
        descField.setText(weather.getClouds());
        TextView tempField = (TextView) convertView.findViewById(R.id.temperatureField);
        tempField.setText(weather.getTemperature());

        return convertView;
    }
}



