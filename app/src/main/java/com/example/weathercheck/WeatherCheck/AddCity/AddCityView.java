package com.example.weathercheck.WeatherCheck.AddCity;

import android.widget.Button;

import com.example.weathercheck.DomainModel.Example;

import java.util.List;

public interface AddCityView {

    void onAdd(Button button);



    void displayResult(List<Example> weatherResponse);

    void displayError(String error_fetching_movie_data);
}