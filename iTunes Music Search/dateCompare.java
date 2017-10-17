package com.example.chris.itunesmusicsearch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Chris on 6/14/2017.
 */

public class dateCompare implements Comparator<Music> {

    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:m");
//System.out.println(sdf.parse(startDate).before(sdf.parse(endDate)));
    @Override
    public int compare(Music o1, Music o2) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date1 = null, date2 = null;
        if(o1.getReleaseDate().equals("No Date Available")){
            return 1;
        } else if(o2.getReleaseDate().equals("No Date Available")){
            return -1;
        } else if(o1.getReleaseDate().equals("No Date Available") && o2.getReleaseDate().equals("No Date Available")){
            return 0;
        }
        try {
            date1 = sdf.parse(o1.getReleaseDate());
            date2 = sdf.parse(o2.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date1.before(date2)){
            return 1;
        } else if(date1.after(date2)){
            return -1;
        } else
        return 0;
    }
}
