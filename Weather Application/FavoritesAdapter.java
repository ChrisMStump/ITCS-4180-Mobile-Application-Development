package com.example.chris.weatherapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.example.chris.weatherapplication.R.id.view;

/**
 * Created by Chris on 6/20/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder>{


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cityState, temperature, updatedOn;

        public ViewHolder(final View itemView){
            super(itemView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Gson gson = new Gson();
                    SharedPreferences  mPrefs = mContext.getSharedPreferences("weatherApplication",MODE_PRIVATE);
                    Set favCities = new HashSet();
                    favCities = mPrefs.getStringSet("MyFavoriteCities", new HashSet<String>());

                    for (Object cityJson : favCities) {
                        FavWeatherObj cityObj = gson.fromJson(cityJson.toString(),FavWeatherObj.class);
                        if(cityObj.getCityState().equals(itemView.findViewById(R.id.cityStateField))){
                            favCities.remove(cityJson);
                            break;
                        }
                    }

                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    prefsEditor.putStringSet("MyFavoriteCities", favCities);
                    prefsEditor.commit();
                    mWeathers.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(),mWeathers.size());
                    Toast.makeText(itemView.getContext(), "Deleted item", Toast.LENGTH_LONG).show();
                    return false;
                }
            });

            cityState = (TextView) itemView.findViewById(R.id.cityStateField);
            temperature = (TextView) itemView.findViewById(R.id.tempField);
            updatedOn = (TextView) itemView.findViewById(R.id.updatedTime);
        }
    }

    private List<FavWeatherObj> mWeathers;
    private Context mContext;

    public FavoritesAdapter(Context context, List<FavWeatherObj> weatherObjs){
        mWeathers = weatherObjs;
        mContext = context;
    }

    private Context getContext(){
        return mContext;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View favView = inflater.inflate(R.layout.favorites_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(favView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
        FavWeatherObj favWeatherObj = mWeathers.get(position);

        holder.cityState.setText(favWeatherObj.getCityState());
        holder.temperature.setText(favWeatherObj.getTemperature() + "\u00b0" + "F");
        holder.updatedOn.setText(favWeatherObj.getUpdatedOn());

    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }
}
