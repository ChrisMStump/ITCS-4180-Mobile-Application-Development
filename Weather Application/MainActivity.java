/*
Group 5
Christopher Stump
Houa Vu
Homework 3
 */
package com.example.chris.weatherapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText cityText, stateText;
    RecyclerView recyclerView;
    FavoritesAdapter adapter;
    ArrayList<FavWeatherObj> favWeatherObjs;
    Gson gson;
    String cityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = (EditText) findViewById(R.id.cityField);
        stateText = (EditText) findViewById(R.id.stateField);

        favWeatherObjs = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new FavoritesAdapter(this, favWeatherObjs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gson = new Gson();

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cityText.getText().toString().equals("") || stateText.getText().toString().length() != 2){
                    Toast.makeText(MainActivity.this, "City or State fields are invalid", Toast.LENGTH_SHORT).show();
                } else if(isConnectedOnline()){
                    //What to do after you click submit?
                    Intent intent = new Intent(MainActivity.this, CityWeather.class);
                    intent.putExtra("City Name", cityText.getText().toString());
                    intent.putExtra("State Initial", stateText.getText().toString());
                    intent.putExtra("URL", getURL(cityText.getText().toString(), stateText.getText().toString()));
                    startActivity(intent);

                } else{
                    Toast.makeText(MainActivity.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    private String getURL(String city, String state){
        //http://api.wunderground.com/api/3794b5a5c9a02a51/hourly/q/CA/San_Francisco.json
        StringTokenizer st = new StringTokenizer(city, " ");
        StringBuilder sb = new StringBuilder();
        sb.append(st.nextToken());
        while(st.hasMoreTokens()){
            sb.append("_" + st.nextToken());
        }
        return "http://api.wunderground.com/api/3794b5a5c9a02a51/hourly/q/" + state + "/" + sb.toString() + ".json";
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.textView2).setVisibility(View.GONE);
        findViewById(R.id.textView).setVisibility(View.VISIBLE);
        findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);

        SharedPreferences mPrefs = getSharedPreferences("weatherApplication",MODE_PRIVATE);
        Gson gson = new Gson();

        Set favCities = new HashSet();
        favCities = mPrefs.getStringSet("MyFavoriteCities", new HashSet<String>());
        if(!favCities.isEmpty()) {
            favWeatherObjs.clear();

            for (Object cityJson : favCities) {
                FavWeatherObj cityObj = gson.fromJson(cityJson.toString(), FavWeatherObj.class);
                favWeatherObjs.add(new FavWeatherObj(cityObj.getCityState(), cityObj.getTemperature(), cityObj.getUpdatedOn()));
            }
            Log.d("demo", favCities.toString());

            adapter.notifyDataSetChanged();
        } else{
            findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            findViewById(R.id.textView).setVisibility(View.GONE);
            findViewById(R.id.recyclerView).setVisibility(View.GONE);
        }
    }


}
