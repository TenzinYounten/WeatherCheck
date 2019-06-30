package com.example.weathercheck.WeatherCheck.Main;

import android.view.View;

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
}
