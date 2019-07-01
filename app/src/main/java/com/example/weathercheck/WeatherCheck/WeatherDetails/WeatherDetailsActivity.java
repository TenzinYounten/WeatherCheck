package com.example.weathercheck.WeatherCheck.WeatherDetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.weathercheck.DomainModel.FinalWeather;
import com.example.weathercheck.DomainModel.Weather;
import com.example.weathercheck.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class WeatherDetailsActivity extends AppCompatActivity implements WeatherDetailsView {
    String cityName;
    @BindView(R.id.nameTxt)
    TextView name;
    @BindView(R.id.temperatureTxt)
    TextView temp;
    @BindView(R.id.tempMaxTxt)
    TextView tempMax;
    @BindView(R.id.tempMinTxt)
    TextView tempMin;
    @BindView(R.id.latitudeTxt)
    TextView latitude;
    @BindView(R.id.longitudeTxt)
    TextView longitude;
    @BindView(R.id.speedTxt)
    TextView speed;

    FinalWeather weather;
    WeatherDetailsPresenter weatherDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        cityName = intent.getStringExtra("details");

        setupView();
        weather = getData(cityName);
        weatherDetailsPresenter = new WeatherDetailsPresenter(this, new WeatherDetailsService());
        weatherDetailsPresenter.setName(name);
        weatherDetailsPresenter.setTemp(temp);
        weatherDetailsPresenter.setMinTemp(tempMax);
        weatherDetailsPresenter.setMaxTemp(tempMin);
        weatherDetailsPresenter.setLat(latitude);
        weatherDetailsPresenter.setLong(longitude);
        weatherDetailsPresenter.setWind(speed);
    }

    private void setupView() {

        name = findViewById(R.id.nameTxt);
        temp = findViewById(R.id.temperatureTxt);
        tempMax = findViewById(R.id.tempMaxTxt);
        tempMin = findViewById(R.id.tempMinTxt);
        latitude = findViewById(R.id.latitudeTxt);
        longitude = findViewById(R.id.longitudeTxt);
        speed = findViewById(R.id.speedTxt);
    }

    private FinalWeather getData(String id) {
        Realm realm = null;
        boolean state = true;
        FinalWeather weather = null;
        Realm.init(this);
        try {
            realm = Realm.getDefaultInstance();
            weather = realm.where(FinalWeather.class).equalTo("name", id).findFirst();
            if (weather != null) {
                FinalWeather finalWeather = new FinalWeather();
                finalWeather.setName(weather.getName());
                finalWeather.setTemp_min(weather.getTemp_min());
                finalWeather.setTemp(weather.getTemp());
                finalWeather.setTemp_max(weather.getTemp_max());
                finalWeather.setLatitude(weather.getLatitude());
                finalWeather.setLongitude(weather.getLongitude());
                finalWeather.setSpeed(weather.getSpeed());
                return finalWeather;
            }
        } catch (Exception e) {
            state = false;
            Log.e("Exception", "" + e.toString());
        }
        return weather;
    }

    @Override
    public void setName(TextView name) {
        name.setText(weather.getName());
    }

    @Override
    public void setTemp(TextView temp) {
        temp.setText("" + weather.getTemp());
    }

    @Override
    public void setTempMax(TextView tempMax) {
        tempMax.setText("" + weather.getTemp_max());
    }

    @Override
    public void setTempMin(TextView tempMin) {
        tempMin.setText("" + weather.getTemp_min());
    }

    @Override
    public void setLat(TextView latitude) {
        latitude.setText(""+weather.getLatitude());
    }

    @Override
    public void setLong(TextView longitude) {
        longitude.setText(""+weather.getLongitude());
    }

    @Override
    public void setWind(TextView speed) {
        speed.setText(""+weather.getSpeed());
    }
}
