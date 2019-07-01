package com.example.weathercheck.WeatherCheck.Main;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.weathercheck.DomainModel.FinalWeather;
import com.example.weathercheck.WeatherCheck.AddCity.AddCityDialog;
import com.example.weathercheck.R;
import com.example.weathercheck.WeatherCheck.WeatherDetails.WeatherDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mainPresenter;
    List<FinalWeather> finalWeathers;
    RecyclerView recyclerView;
    CardView cardView;
    RealmResults<FinalWeather> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView = (CardView) findViewById(R.id.cardView);
        recyclerView = (RecyclerView) findViewById(R.id.weatherList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainPresenter = new MainPresenter(this, new MainService());
    }

    @Override
    protected void onResume() {
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        results = realm.where(FinalWeather.class).findAllAsync();
        results.load();
        finalWeathers = new ArrayList<FinalWeather>();
        for(FinalWeather finalWeather: results) {
            FinalWeather weather = new FinalWeather();
            weather.setId(finalWeather.getId());
            weather.setLatitude(finalWeather.getLatitude());
            weather.setLongitude(finalWeather.getLongitude());
            weather.setName(finalWeather.getName());
            weather.setSpeed(finalWeather.getSpeed());
            weather.setTemp(finalWeather.getTemp());
            weather.setTemp_max(finalWeather.getTemp_min());
            weather.setTemp_min(finalWeather.getTemp_min());
            finalWeathers.add(weather);
        }
        recyclerView.setAdapter(new HomeAdapter(finalWeathers, getApplicationContext(), new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Long id) {
                mainPresenter.goToDetails(MainActivity.this, WeatherDetailsActivity.class, id);
            }
        }));
        super.onResume();

    }

    public void onActionClicked(View view) {
        mainPresenter.onFabClick(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the com.example.weathercheck.WeatherCheck.Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFabClick(View view) {

        AddCityDialog addCityFragment = new AddCityDialog();
        Intent intent = new Intent(MainActivity.this, AddCityDialog.class);
        startActivity(intent);
    }

    @Override
    public void goToDetails(MainActivity mainActivity, Class<WeatherDetailsActivity> weatherDetailsActivityClass, Long id) {
        Intent intent = new Intent(MainActivity.this, WeatherDetailsActivity.class);
        intent.putExtra("details", id);
        startActivity(intent);
    }
}
