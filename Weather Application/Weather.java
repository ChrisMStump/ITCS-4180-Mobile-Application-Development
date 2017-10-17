package com.example.chris.weatherapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Chris on 6/19/2017.
 */

public class Weather implements Serializable{
    String time, temperature, dewpoint, clouds, iconUrl, windSpeed, windDirection, climateType, humidity, feelsLike, pressure;

    final static String DEGREE = "\u00b0";

    static public Weather createWeather(JSONObject js) {
        Weather weather = new Weather();

        try {
            weather.setTime(js.getJSONObject("FCTTIME").getString("civil"));
            weather.setTemperature(js.getJSONObject("temp").getString("english") + DEGREE + "F");
            weather.setDewpoint(js.getJSONObject("dewpoint").getString("english") + DEGREE + " Fahrenheit");
            weather.setClouds(js.getString("condition"));
            weather.setIconUrl(js.getString("icon_url"));
            weather.setWindSpeed(js.getJSONObject("wspd").getString("english") + " mph");
            weather.setWindDirection(js.getJSONObject("wdir").getString("degrees") + DEGREE + " " + js.getJSONObject("wdir").getString("dir"));
            weather.setClimateType(js.getString("wx"));
            weather.setHumidity(js.getString("humidity") + "%");
            weather.setFeelsLike(js.getJSONObject("feelslike").getString("english") + DEGREE + "F");
            weather.setPressure(js.getJSONObject("mslp").getString("metric") + " hPa");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDewpoint() {
        return dewpoint;
    }

    public void setDewpoint(String dewpoint) {
        this.dewpoint = dewpoint;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getClimateType() {
        return climateType;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "time='" + time + '\'' +
                ", temperature='" + temperature + '\'' +
                ", dewpoint='" + dewpoint + '\'' +
                ", clouds='" + clouds + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", climateType='" + climateType + '\'' +
                ", humidity='" + humidity + '\'' +
                ", feelsLike='" + feelsLike + '\'' +
                ", pressure='" + pressure + '\'' +
                '}';
    }
}
