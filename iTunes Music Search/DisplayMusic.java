package com.example.chris.itunesmusicsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static android.view.View.GONE;

/**
 * Created by Chris on 6/13/2017.
 */

public class DisplayMusic extends AppCompatActivity{
    ImageView imageView;
    TextView title, genre, artist, album, price, albumPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_music);

        imageView = (ImageView) findViewById(R.id.imageView);
        title = (TextView) findViewById(R.id.trackName);
        genre = (TextView) findViewById(R.id.trackGenre);
        artist = (TextView) findViewById(R.id.trackArtist);
        album = (TextView) findViewById(R.id.trackAlbum);
        price = (TextView) findViewById(R.id.trackPrice);
        albumPrice = (TextView) findViewById(R.id.albumPrice);


        final Music music = (Music) getIntent().getExtras().getSerializable("musicObject");
        NumberFormat formatter = new DecimalFormat("#0.00");

        if(music.getImageURL() != null){
            Picasso.with(getBaseContext()).load(music.getImageURL()).into(imageView);
        } else if(music.getImageURL() == null){
            imageView.setVisibility(GONE);
        }
        if(music.getTrackName() != null){
            title.setText("Track: " + music.getTrackName());
        }
        if(music.getPrimaryGenreName() != null){
            genre.setText("Genre: " + music.getPrimaryGenreName());
        }
        if(music.getArtistName() != null){
            artist.setText("Artist: " + music.getArtistName());
        }
        if(music.getCollectionName() != null){
            album.setText("Album: " + music.getCollectionName());
        }
        if(String.valueOf(music.getTrackPrice()) != null){
            price.setText("Track Price: $" + formatter.format(music.getTrackPrice()));
        }
        if(String.valueOf(music.getCollectionPrice()) != null){
            albumPrice.setText("Album Price: $" + formatter.format(music.getCollectionPrice()));
        }

        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}
