package com.example.azram.giphysearch.searchgiphy.adapters;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azram.giphysearch.GlideApp;
import com.example.azram.giphysearch.R;
import com.example.azram.giphysearch.giphydetails.GiphyDetailsActivity;
import com.example.azram.giphysearch.model.Gif;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class SearchGiphyRecyclerViewAdapter extends RealmRecyclerViewAdapter<Gif, SearchGiphyRecyclerViewAdapter.GifViewHolder> {

    public SearchGiphyRecyclerViewAdapter(@Nullable OrderedRealmCollection<Gif> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View giphyItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.giphy_item, parent, false);
        return new GifViewHolder(giphyItemView);
    }

    @Override
    public void onBindViewHolder(GifViewHolder holder, int position) {
        if (getData() != null) {
            holder.bindTo(getData().get(position));
        }
    }

    /*
    @Override
    public void onViewRecycled(GifViewHolder holder) {
        super.onViewRecycled(holder);
        Log.i("RECYCLER", "ON VIEW RECYCLED");
        holder.imageView.setImageBitmap(null);
    }*/

    static class GifViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        private void bindTo(final Gif gif) {
            titleTextView.setText(gif.getTitle());
            GlideApp.with(imageView.getContext())
                    .load(gif.getGifImages().getGifImage().getUrl())
                    //.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openGiphyDetails = new Intent(imageView.getContext(), GiphyDetailsActivity.class);
                    openGiphyDetails.putExtra(GiphyDetailsActivity.EXTRA_GIF_ID, gif.getId());
                    imageView.getContext().startActivity(openGiphyDetails);
                }
            });
        }

        GifViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.giphyTitle);
            imageView = itemView.findViewById(R.id.giphyImage);
        }
    }
}
