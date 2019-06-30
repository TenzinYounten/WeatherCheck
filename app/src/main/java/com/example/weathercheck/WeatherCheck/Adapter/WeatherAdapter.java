package com.example.weathercheck.WeatherCheck.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.weathercheck.DomainModel.Coord;
import com.example.weathercheck.DomainModel.FinalWeather;
import com.example.weathercheck.DomainModel.Weather;
import com.example.weathercheck.R;

import java.util.List;

import com.example.weathercheck.DomainModel.Example;

import io.realm.Realm;
import io.realm.RealmList;


/**
 * Created by anujgupta on 26/12/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    List<Example> weatherList;
    Context context;

    public WeatherAdapter(List<Example> movieList, Context context) {
        this.weatherList = movieList;
        this.context = context;
    }

    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.searchrows, parent, false);
        WeatherHolder mh = new WeatherHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(WeatherHolder holder, int position) {

        holder.tvTitle.setText("" + weatherList.get(position).getName());
        holder.tvOverview.setText("" + weatherList.get(position).getMain().getTemp());
        holder.tvReleaseDate.setText("Latitude : " + weatherList.get(position).getCoord().getLat() + " Longitude : "
                + weatherList.get(position).getCoord().getLon());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeWeathers(weatherList);
            }
        });

    }

    private void storeWeathers(final List<Example> _weatherList) {
        Realm realm = null;
        boolean state = true;
        Realm.init(context);
        try {
            realm = Realm.getDefaultInstance();


            RealmList<Example> weatherList = new RealmList<>();
            int nextId = 0;

            for (Example json : _weatherList) {
                realm.beginTransaction();
                Number maxId = realm.where(FinalWeather.class).max("id");
                nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                FinalWeather example = realm.createObject(FinalWeather.class);
                example.name = json.getName();
                example.temp = json.getMain().getTemp();
                example.temp_min = json.getMain().getTempMin();
                example.temp_max = json.getMain().getTempMax();
                example.latitude = json.getCoord().getLat();
                example.longitude = json.getCoord().getLon();
                example.speed = json.getWind().getSpeed();
                realm.commitTransaction();

            }
        } catch (Exception e) {
            state = false;
        }

        if(state == true) {
            Toast.makeText(context,"Data Saved ....", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,"Data Not Saved ....", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class WeatherHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview, tvReleaseDate;
        Button button;

        public WeatherHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvOverview = (TextView) v.findViewById(R.id.tvOverView);
            tvReleaseDate = (TextView) v.findViewById(R.id.tvReleaseDate);
            button = (Button) v.findViewById(R.id.add);

        }
    }
}
