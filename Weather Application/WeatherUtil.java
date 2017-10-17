package com.example.chris.weatherapplication;

import com.example.chris.weatherapplication.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Chris on 6/19/2017.
 */

public class WeatherUtil {
    static public class WeatherJSONParser{
        static ArrayList<Weather> parseWeather(String in) throws JSONException {
            ArrayList<Weather> weatherList = new ArrayList<Weather>();

            JSONObject root = new JSONObject(in);
            JSONArray weatherJSONArray = root.getJSONArray("hourly_forecast");

            for(int i = 0; i < weatherJSONArray.length(); i++){
                JSONObject weatherJSONObject = weatherJSONArray.getJSONObject(i);
                Weather weather = Weather.createWeather(weatherJSONObject);
                weatherList.add(weather);

            }
            return weatherList;
        }
    }
}



