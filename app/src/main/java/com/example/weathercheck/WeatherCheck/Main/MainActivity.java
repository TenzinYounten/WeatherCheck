package com.example.weathercheck.WeatherCheck.Main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.weathercheck.DomainModel.FinalWeather;
import com.example.weathercheck.Utility.PermissionChecker;
import com.example.weathercheck.WeatherCheck.Adapter.WeatherAdapter;
import com.example.weathercheck.WeatherCheck.AddCity.AddCityDialog;
import com.example.weathercheck.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mainPresenter;
    List<FinalWeather> finalWeathers;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    RealmResults<FinalWeather> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.weatherList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        results = realm.where(FinalWeather.class).findAllAsync();
        results.load();
        finalWeathers = new ArrayList<FinalWeather>();
        for(FinalWeather finalWeather: results) {
            finalWeathers.add(finalWeather);
        }
        adapter = new HomeAdapter(finalWeathers, getApplicationContext());
        recyclerView.setAdapter(adapter);
        mainPresenter = new MainPresenter(this, new MainService());

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void onActionClicked(View view) {
        mainPresenter.onFabClick(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the com.example.weathercheck.WeatherCheck.Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
}
