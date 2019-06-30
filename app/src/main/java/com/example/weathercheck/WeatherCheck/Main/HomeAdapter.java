package com.example.weathercheck.WeatherCheck.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathercheck.DomainModel.Example;
import com.example.weathercheck.DomainModel.FinalWeather;
import com.example.weathercheck.R;
import com.example.weathercheck.WeatherCheck.Adapter.WeatherAdapter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.WeatherHolder> {
    Context context;
    List<FinalWeather> finalWeathers;

    public HomeAdapter(List<FinalWeather> finalWeathers, Context mainActivity) {
        this.context = mainActivity;
        this.finalWeathers = finalWeathers;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.weather_rows, viewGroup, false);
        WeatherHolder mh = new WeatherHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder viewHolder, int i) {
        viewHolder.tvOverview.setText("" + finalWeathers.get(i).getName());
        viewHolder.tvOverview.setText("" + finalWeathers.get(i).getTemp());
        viewHolder.tvReleaseDate.setText("Latitude : " + finalWeathers.get(i).getLatitude() + " Longitude : "
                + finalWeathers.get(i).getLongitude());
        final int j = i;
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWeather(finalWeathers.get(j));
            }
        });
    }

    private void deleteWeather(FinalWeather finalWeathers) {
        Realm realm = null;
        boolean state = true;
        Realm.init(context);
        try {
            realm = Realm.getDefaultInstance();

            int nextId = 0;

            final RealmResults<FinalWeather> finalWeatherRealmResults = realm.where(FinalWeather.class).findAll();

            FinalWeather finalWeather = finalWeatherRealmResults .where().equalTo("id",finalWeathers.getId()).findFirst();

            if(finalWeather!=null){

                if (!realm.isInTransaction())
                {
                    realm.beginTransaction();
                }

                finalWeather.deleteFromRealm();

                realm.commitTransaction();
            }

        } catch (Exception e) {
            state = false;
        }

        if(state == true) {
            Toast.makeText(context,"Data Removed ....", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,"Data Not Removed ....", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
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
