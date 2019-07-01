package com.example.weathercheck.WeatherCheck.Main;

import android.view.View;

import com.example.weathercheck.WeatherCheck.WeatherDetails.WeatherDetailsActivity;

import io.realm.Realm;

public class MainPresenter {
    private MainView mainView;
    private MainService mainService;
    Realm realm;

    public MainPresenter(MainView mainView, MainService mainService) {
        this.mainView = mainView;
        this.mainService = mainService;
    }

    public void onFabClick(View view) {
        mainView.onFabClick(view);
    }

    public void goToDetails(MainActivity mainActivity, Class<WeatherDetailsActivity> weatherDetailsActivityClass, Long id) {
        mainView.goToDetails(mainActivity, weatherDetailsActivityClass,id);
    }
}
