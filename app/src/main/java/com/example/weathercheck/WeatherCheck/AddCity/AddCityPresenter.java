package com.example.weathercheck.WeatherCheck.AddCity;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Button;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;
import com.example.weathercheck.DomainModel.Example;
import com.example.weathercheck.weatherEndPointInterface.*;
import static android.content.ContentValues.TAG;

public class AddCityPresenter implements SearchPresenterInterface{
    private AddCityView addCityView;
    private AddCityService addCityService;
    Realm realm;

    public AddCityPresenter(AddCityService addCityService, AddCityView addCityView) {

        this.addCityService = addCityService;
        this.addCityView = addCityView;
    }

    public void onAddClick(Button button) {
        addCityView.onAdd(button);
    }



    private Observable<String> getObservableQuery(SearchView searchView){

        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
    }

    public DisposableObserver<Example> getObserver(){
        return new DisposableObserver<Example>() {

            @Override
            public void onNext(@NonNull Example weatherResponse) {
                List<Example> list = new ArrayList<Example>();
                list.add(weatherResponse);
                addCityView.displayResult(list);
            }


            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                List<Example> list = new ArrayList<Example>();
                //addCityView.displayError("Error fetching Weather Data");
                addCityView.displayResult(list);
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }

    @SuppressLint("CheckResult")
    @Override
    public void getResultsBasedOnQuery(SearchView searchView) {


        getObservableQuery(searchView)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        if(s.equals("")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                })
                .debounce(5, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<Example>>() {
                    @Override
                    public Observable<Example> apply(@NonNull String s) throws Exception {
                        return NetworkClient.getRetrofit().create(RetrofitInterface.class)
                                .searchCity(s,RetrofitInterface.API_KEY);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());

    }
}
