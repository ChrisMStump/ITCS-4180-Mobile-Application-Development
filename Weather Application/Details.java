package com.example.chris.weatherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    ImageView imageView;
    TextView time, temperature, dewpoint, clouds, wind, climateType, humidity, feelsLike, pressure, maxTemp, minTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView = (ImageView) findViewById(R.id.imageView);
        time = (TextView) findViewById(R.id.curLocAndTime);
        temperature =  (TextView) findViewById(R.id.temperatureField);
        dewpoint = (TextView) findViewById(R.id.dewpointField);
        clouds = (TextView) findViewById(R.id.cloudsField);
        wind = (TextView) findViewById(R.id.windsField);
        climateType = (TextView) findViewById(R.id.descField);
        humidity = (TextView) findViewById(R.id.humidityField);
        feelsLike = (TextView) findViewById(R.id.feelsLike);
        pressure = (TextView) findViewById(R.id.pressureField);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        minTemp = (TextView) findViewById(R.id.minTemp);

        String cityState = getIntent().getStringExtra("CityState");
        final Weather weather = (Weather) getIntent().getExtras().getSerializable("weatherObject");
        int minimumTemp = getIntent().getIntExtra("minTemp", 0);
        int maximumTemp = getIntent().getIntExtra("maxTemp", 0);

        time.setText(Html.fromHtml("Current Location: " + "<b>" + cityState + " " + "(" + weather.getTime() + ")" + "</b>"));
        Picasso.with(getBaseContext()).load(weather.getIconUrl()).resize(150, 150).into(imageView);
        temperature.setText(weather.getTemperature());
        climateType.setText(weather.getClimateType());
        maxTemp.setText("Max Temperature: " + maximumTemp + "\u00b0" + "F");
        minTemp.setText("Min Temperature: " + minimumTemp + "\u00b0" + "F");
        feelsLike.setText("Feels like: " + weather.getFeelsLike());
        humidity.setText("Humidity: " + weather.getHumidity());
        dewpoint.setText("Dewpoint: " + weather.getDewpoint());
        pressure.setText("Pressure: " + weather.getPressure());
        clouds.setText("Clouds: " + weather.getClouds());
        wind.setText("Winds: " + weather.getWindSpeed() + ", " + weather.getWindDirection());
    }
}
