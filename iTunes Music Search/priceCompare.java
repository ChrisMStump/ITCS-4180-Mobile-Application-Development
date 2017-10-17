package com.example.chris.itunesmusicsearch;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Chris on 6/13/2017.
 */

public class priceCompare implements Comparator<Music> {
    @Override
    public int compare(Music o1, Music o2) {
        if (o1.getTrackPrice() > o2.getTrackPrice())
        {
            return 1;
        }
        else if (o1.getTrackPrice() < o2.getTrackPrice())
        {
            return -1;
        }
        return 0;
    }
}
