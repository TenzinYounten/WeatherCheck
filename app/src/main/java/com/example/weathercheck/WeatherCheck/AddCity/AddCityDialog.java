package com.example.weathercheck.WeatherCheck.AddCity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;


import com.example.weathercheck.R;

import java.util.List;

import com.example.weathercheck.WeatherCheck.Adapter.WeatherAdapter;
import com.example.weathercheck.DomainModel.Example;

import io.reactivex.ObservableSource;

/*
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableSource;
*/

public class AddCityDialog extends AppCompatActivity implements AddCityView {

    Context context;
    AddCityPresenter addCityPresenter;
 /*   @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;*/

    /* @BindView(R.id.queryList)
     RecyclerView recyclerView;
 */
    RecyclerView recyclerView;
    private SearchView searchView;
    RecyclerView.Adapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.add_city);
        addCityPresenter = new AddCityPresenter(new AddCityService(), this);
        super.onCreate(savedInstanceState);
        /*ButterKnife.bind(this);*/
        recyclerView = (RecyclerView) findViewById(R.id.queryList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Enter City ");

        addCityPresenter.getResultsBasedOnQuery(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAdd(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void displayResult(List<Example> weatherResponse) {

        adapter = new WeatherAdapter(weatherResponse, AddCityDialog.this);
        recyclerView.setAdapter(adapter);
        ;
    }

    @Override
    public void displayError(String error_fetching_movie_data) {

    }

}

