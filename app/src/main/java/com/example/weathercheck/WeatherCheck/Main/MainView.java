package com.example.weathercheck.WeatherCheck.Main;

import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.weathercheck.WeatherCheck.WeatherDetails.WeatherDetailsActivity;

public interface MainView {
    void onFabClick(View view);

    void goToDetails(MainActivity mainActivity, Class<WeatherDetailsActivity> weatherDetailsActivityClass, Long id);

    void search(SearchView searchView);
}