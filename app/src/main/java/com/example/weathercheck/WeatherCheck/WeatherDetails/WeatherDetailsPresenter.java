package com.example.weathercheck.WeatherCheck.WeatherDetails;

import android.widget.TextView;

import com.example.weathercheck.WeatherCheck.Main.MainView;

import io.realm.Realm;

public class WeatherDetailsPresenter {
    private WeatherDetailsView weatherDetailsView;
    private WeatherDetailsService weatherDetailsService;
    Realm realm;

    public WeatherDetailsPresenter(WeatherDetailsView weatherDetailsActivity, WeatherDetailsService weatherDetailsService) {
        this.weatherDetailsView = weatherDetailsActivity;
        this.weatherDetailsService = weatherDetailsService;
    }


    public void setName(TextView name) {
        weatherDetailsView.setName(name);
    }

    public void setTemp(TextView temp) {

        weatherDetailsView.setTemp(temp);
    }

    public void setMinTemp(TextView tempMax) {
        weatherDetailsView.setTempMax(tempMax);
    }

    public void setMaxTemp(TextView tempMin) {
        weatherDetailsView.setTempMin(tempMin);
    }

    public void setLat(TextView latitude) {
        weatherDetailsView.setLat(latitude);
    }

    public void setLong(TextView longitude) {
        weatherDetailsView.setLong(longitude);
    }

    public void setWind(TextView speed) {
        weatherDetailsView.setWind(speed);
    }
}
