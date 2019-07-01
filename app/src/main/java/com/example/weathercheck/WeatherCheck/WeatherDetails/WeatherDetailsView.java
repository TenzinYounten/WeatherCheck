package com.example.weathercheck.WeatherCheck.WeatherDetails;

import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;

import com.example.weathercheck.WeatherCheck.Main.MainActivity;

public interface WeatherDetailsView {

    void setName(TextView name);

    void setTemp(TextView temp);

    void setTempMax(TextView tempMax);

    void setTempMin(TextView tempMin);

    void setLat(TextView latitude);

    void setLong(TextView longitude);

    void setWind(TextView speed);

}