package com.example.azram.giphysearch.searchgiphy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.azram.giphysearch.R;
import com.example.azram.giphysearch.util.ActivityUtils;

public class SearchGiphyActivity extends AppCompatActivity {

    private static final String LOG_TAG = SearchGiphyActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_giphy_activity);

        //Dodaj fragment u fragmentLayout (kreiraj novi ako vec ne postoji)
        SearchGiphyFragment searchGiphyFragment = (SearchGiphyFragment) getSupportFragmentManager().findFragmentById(R.id.searchGiphyContent);

        if (searchGiphyFragment == null) {
            searchGiphyFragment = new SearchGiphyFragment();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    searchGiphyFragment,
                    R.id.searchGiphyContent);

            //Kreiraj presenter
            new SearchGiphyPresenter(searchGiphyFragment);
        }
    }
}
