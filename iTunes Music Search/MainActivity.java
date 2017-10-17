/*
Christopher Stump
In Class 07
 */
package com.example.chris.itunesmusicsearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import static android.R.id.keyboardView;
import static android.R.id.list;
import static android.R.id.progress;

public class MainActivity extends AppCompatActivity implements GetMusicAsyncTask.IData{

    SeekBar limitBar;
    TextView keywordsView, limitView;
    String searchURL, keywords;
    Integer sizeLimit;
    ProgressDialog progress;
    LinearLayout container;
    boolean switchCheck;
    ArrayList<Music> arrayList;
    MusicAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        limitBar = (SeekBar) findViewById(R.id.seekBar);
        keywordsView = (TextView) findViewById(R.id.searchBar);
        limitView = (TextView) findViewById(R.id.limitView);
        limitView.setText("Limit: 10");

        final Switch sw = (Switch) this.findViewById(R.id.switch1);
        sw.setChecked(true);
        switchCheck = true;

        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        arrayList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new MusicAdapter(MainActivity.this, R.layout.listitem, arrayList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music music = arrayList.get(position);
                Intent intent = new Intent(MainActivity.this, DisplayMusic.class);
                intent.putExtra("musicObject", music);
                view.getContext().startActivity(intent);
            }
        });


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switchCheck = true;
                } else{
                    switchCheck = false;
                }
                if(!arrayList.isEmpty()) {
                        if(isChecked){
                            Collections.sort(arrayList, new dateCompare());
                            adapter.notifyDataSetChanged();
                        } else{
                            Collections.sort(arrayList, new priceCompare());
                            adapter.notifyDataSetChanged();
                        }
                }
            }
        });

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What happens when the user selects search?
                if(isConnectedOnline()){
                    if(!keywordsView.getText().toString().equals("")) {
                        //listView.removeAllViews();
                        sizeLimit = limitBar.getProgress() + 10;
                        keywords = keywordsView.getText().toString();
                        searchURL = getUrl(keywords);

                        progress = new ProgressDialog(MainActivity.this);
                        progress.setCancelable(false);
                        progress.setTitle("Loading...");
                        progress.show();
                        new GetMusicAsyncTask(MainActivity.this).execute("https://itunes.apple.com/search?term=" + searchURL + "&limit=" + sizeLimit);
                    }else{
                        Toast.makeText(MainActivity.this, "Enter a search value.", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(MainActivity.this, "No Network Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What happens when the user selects reset?
                keywordsView.setText("");
                keywordsView.setHint("Search...");
                limitBar.setProgress(0);
                sw.setChecked(true);
                adapter.notifyDataSetChanged();
                arrayList.clear();
                switchCheck = true;
            }
        });

        limitBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                limitView.setText("Limit: " + (limitBar.getProgress()+10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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

    private String getUrl(String keywords){
        StringTokenizer st = new StringTokenizer(keywords, " ");
        StringBuilder sb = new StringBuilder();
        while(st.hasMoreTokens()){
            sb.append(st.nextToken() + "+");
        }
        sb.deleteCharAt(sb.length()-1);
        keywords = sb.toString();
        return keywords;
    }

    public void postMusic(ArrayList<Music> result){
        String date;

        for (Music music : result) {
            date = music.getReleaseDate();
            date = formatDate(date);
            music.setReleaseDate(date);
            if (music.getTrackPrice() < 0) {
                music.setTrackPrice(music.getTrackPrice() * -1);
            }
        }
        if(!switchCheck){
            Collections.sort(result, new priceCompare());
        } else{
            Collections.sort(result, new dateCompare());
        }
        arrayList.clear();
        arrayList.addAll(result);
        adapter.notifyDataSetChanged();

        progress.dismiss();
    }

    public String formatDate(String date){
        String month, day, year;
        if(date != null){
            StringTokenizer st = new StringTokenizer(date, "- T");
            year = st.nextToken();
            month = st.nextToken();
            day = st.nextToken();
            return month + "-" + day + "-" + year;
        }
        return "No Date Available";
    }
}
