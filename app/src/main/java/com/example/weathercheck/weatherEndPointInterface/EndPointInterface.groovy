package com.example.weathercheck.weatherEndPointInterface

import com.example.weathercheck.DomainModel.Example;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndPointInterface {

    public static final String API_KEY = "1021c456b8264694953109b855a9cbbb";

    @GET("search/movie")
    Observable<Example> searchCity(@Query("q") String api_key, @Query("appid") String q);

}
