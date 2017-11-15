package com.example.azram.giphysearch.searchgiphy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.azram.giphysearch.R;
import com.example.azram.giphysearch.model.Gif;
import com.example.azram.giphysearch.searchgiphy.adapters.SearchGiphyRecyclerViewAdapter;

import io.realm.OrderedRealmCollection;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGiphyFragment extends Fragment implements
        SearchGiphyContract.SearchGiphyView,
        SearchView.OnQueryTextListener  {

    private final static String LOG_TAG = SearchGiphyFragment.class.getName();

    /**
     * Presenter za ovaj view
     */
    private SearchGiphyContract.SearchGiphyPresenter mSearchGiphyPresenter;

    SearchView searchGiphy;

    SearchGiphyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    ProgressBar progressBar;

    public SearchGiphyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //Kreiraj presenter
        new SearchGiphyPresenter(this);
    }

    /**
     * Pozvace se iz konstruktora presentera
     */
    @Override
    public void setPresenter(SearchGiphyContract.SearchGiphyPresenter presenter) {
        this.mSearchGiphyPresenter = presenter;
    }

    /**
     * Takodje se poziva iz konstruktova presentera!
     */
    @Override
    public void setAdapter(OrderedRealmCollection<Gif> listaGifova) {
        adapter = new SearchGiphyRecyclerViewAdapter(listaGifova, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search_giphy_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setuj view - ove
        searchGiphy = view.findViewById(R.id.searchGiphy);
        searchGiphy.setOnQueryTextListener(this);

        recyclerView = view.findViewById(R.id.rvGifs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onDestroyView() {
        //Adapter se kreira u onCreate a recyclerView se povlaci u onCreateView!
        recyclerView.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchGiphyPresenter.searchQueryChanged(newText);
        return false;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        progressBar.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNoGifs() {
        //TODO: Napraviti neki view za nema gifova
        adapter.updateData(null);
        showMessage(getString(R.string.noGifsMessage));
    }

    @Override
    public void showLoadingGifsError() {
        adapter.updateData(null);
        showMessage(getString(R.string.errorLoadingGifsMessage));
    }

    private void showMessage(String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        mSearchGiphyPresenter.unbind();
        super.onDestroy();
    }
}
