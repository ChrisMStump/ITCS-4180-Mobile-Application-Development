package com.example.chris.weatherapplication;

import java.util.ArrayList;

/**
 * Created by Chris on 6/20/2017.
 */

public class FavWeatherObj {
    String cityState, temperature, updatedOn;

    public FavWeatherObj(String cityState, String temperature, String updatedOn) {
        this.cityState = cityState;
        this.temperature = temperature;
        this.updatedOn = updatedOn;
    }

    public String getCityState() {
        return cityState;
    }

    public void setCityState(String cityState) {
        this.cityState = cityState;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    private static int lastWeatherId = 0;
}
