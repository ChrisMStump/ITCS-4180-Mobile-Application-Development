package com.example.chris.weatherapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CityWeather extends AppCompatActivity implements GetWeatherAsyncTask.IData{
    String city, state, URL;
    ProgressDialog progress;
    TextView location;
    ListView listView;
    WeatherAdapter adapter;
    ArrayList<Weather> arrayList;
    int minTemp, maxTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        location = (TextView) findViewById(R.id.currentLocation);
        listView = (ListView) findViewById(R.id.hourlyListView);

        arrayList = new ArrayList<>();
        adapter = new WeatherAdapter(CityWeather.this, R.layout.hourly_list_item, arrayList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        city = getIntent().getStringExtra("City Name");
        state = getIntent().getStringExtra("State Initial");
        URL = getIntent().getStringExtra("URL");

        progress = new ProgressDialog(CityWeather.this);
        progress.setCancelable(false);
        progress.setTitle("Loading Hourly Data");
        progress.show();
        new GetWeatherAsyncTask(CityWeather.this).execute(URL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Weather weather = arrayList.get(position);
                Intent intent = new Intent(CityWeather.this, Details.class);
                intent.putExtra("CityState", city + ", " + state);
                intent.putExtra("weatherObject", weather);
                intent.putExtra("minTemp", getMinValue(arrayList));
                intent.putExtra("maxTemp", getMaxValue(arrayList));
                view.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public void postWeather(ArrayList<Weather> result) {
        if(result != null) {
            arrayList.clear();
            arrayList.addAll(result);
            adapter.notifyDataSetChanged();
            location.setText(Html.fromHtml("Current Location: " + "<b>" + city + ", " + state + "</b>"));
            progress.dismiss();
            for (Weather weather : result) {
                Log.d("demo", weather.toString() + "\n\n");
            }
        } else {
            Toast.makeText(getBaseContext(), "No such city or state", Toast.LENGTH_LONG).show();
            progress.dismiss();
            finish();
        }
    }

    public static int getMaxValue(ArrayList<Weather> weathers){
        int maxValue = Integer.parseInt(weathers.get(0).getTemperature().replace("\u00b0" + "F", ""));
        for(int i=1;i < weathers.size() - 1; i++){
            if(Integer.parseInt(weathers.get(i).getTemperature().replace("\u00b0" + "F", "")) > maxValue){
                maxValue = Integer.parseInt(weathers.get(i).getTemperature().replace("\u00b0" + "F", ""));
            }
        }
        return maxValue;
    }

    public static int getMinValue(ArrayList<Weather> weathers){
        int minValue = Integer.parseInt(weathers.get(0).getTemperature().replace("\u00b0" + "F", ""));
        for(int i=1;i < weathers.size() - 1; i++){
            if(Integer.parseInt(weathers.get(i).getTemperature().replace("\u00b0" + "F", "")) < minValue){
                minValue = Integer.parseInt(weathers.get(i).getTemperature().replace("\u00b0" + "F", ""));
            }
        }
        return minValue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        Toast.makeText(this, "Sent to favorites", Toast.LENGTH_SHORT).show();
        String pattern = "MM-dd-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        FavWeatherObj obj = new FavWeatherObj(city + ", " + state, arrayList.get(0).getTemperature().replace("\u00b0" + "F", ""), dateInString);
        Gson gson = new Gson();
        String json = gson.toJson(obj);

        //Add the json object to a hashset.
        SharedPreferences  mPrefs = getSharedPreferences("weatherApplication",MODE_PRIVATE);

        Set favCities = new HashSet();
        favCities = mPrefs.getStringSet("MyFavoriteCities", new HashSet<String>());


        for (Object cityJson : favCities) {
            FavWeatherObj cityObj = gson.fromJson(cityJson.toString(),FavWeatherObj.class);
            if(cityObj.getCityState().equals(city + ", " + state)){
                favCities.remove(cityJson);
                Toast.makeText(getBaseContext(), "Updated Item", Toast.LENGTH_LONG).show();
                break;
            }
        }

        favCities.add(json);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putStringSet("MyFavoriteCities", favCities);
        prefsEditor.commit();
        return false;
    }
}
