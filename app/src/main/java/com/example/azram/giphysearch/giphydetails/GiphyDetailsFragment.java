package com.example.azram.giphysearch.giphydetails;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.azram.giphysearch.GlideApp;
import com.example.azram.giphysearch.R;
import com.example.azram.giphysearch.model.Gif;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiphyDetailsFragment extends Fragment implements GiphyDetailsContract.GiphyDetailsView {

    private static final String GIF_ID = "GIF_ID";

    private GiphyDetailsContract.GiphyDetailsPresenter mGiphyDetailsPresenter;

    private ImageView giphyImageView;

    public GiphyDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Koristiti za kreiranje fragmenta
     * @param gifId id gifa koji ce se prikazivati u fragmentu
     * @return {@link GiphyDetailsFragment}
     */
    public static GiphyDetailsFragment newInstance(String gifId) {

        Bundle args = new Bundle();
        args.putString(GIF_ID, gifId);

        GiphyDetailsFragment giphyDetailsFragment = new GiphyDetailsFragment();
        giphyDetailsFragment.setArguments(args);

        return giphyDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new GiphyDetailsPresenter(this,
                getArguments().getString(GIF_ID));
    }

    @Override
    public void setPresenter(GiphyDetailsContract.GiphyDetailsPresenter presenter) {
        this.mGiphyDetailsPresenter = presenter;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.giphy_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        giphyImageView = view.findViewById(R.id.giphyImage);
        mGiphyDetailsPresenter.bind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void showGif(Gif gif) {
        GlideApp.with(giphyImageView.getContext())
                .load(gif.getGifImages().getGifImage().getUrl())
                //.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(giphyImageView);
    }

    @Override
    public void showGifNotFound() {
        //TODO: NAPRAVITI NEKI VIEW ZA GIF NOT FOUND
        Toast.makeText(getActivity(), "Gif not found!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGiphyDetailsPresenter.unbind();
    }
}
