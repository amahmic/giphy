package com.example.azram.giphysearch.giphydetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.azram.giphysearch.R;
import com.example.azram.giphysearch.util.ActivityUtils;

public class GiphyDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_GIF_ID = "GIF_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giphy_details_activity);

        GiphyDetailsFragment giphyDetailsFragment = (GiphyDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.giphyDetailsContent);

        if (giphyDetailsFragment == null) {

            String taskId = getIntent().getStringExtra(EXTRA_GIF_ID);
            giphyDetailsFragment = GiphyDetailsFragment.newInstance(taskId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    giphyDetailsFragment,
                    R.id.giphyDetailsContent);
        }
    }
}
