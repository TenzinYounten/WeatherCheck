package com.example.weathercheck.WeatherCheck.AddCity;

import com.example.weathercheck.DomainModel.Example;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RetrofitInterface {
    public static final String API_KEY = "1021c456b8264694953109b855a9cbbb";

    @GET("weather")
    Observable<Example> searchCity(@Query("q") String s, @Query("appid") String q);
}
