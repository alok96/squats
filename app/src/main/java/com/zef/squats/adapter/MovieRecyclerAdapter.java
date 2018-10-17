package com.zef.squats.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zef.squats.R;
import com.zef.squats.activity.MovieDetailsActivity;

import java.util.ArrayList;

import com.zef.squats.constants.AppConfig;
import com.zef.squats.dialog.LoadingDialogFragment;
import com.zef.squats.model.Result;

/**
 * Created by zef on 16/10/18.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Result> movies;
    private LoadingDialogFragment loadingDialogFragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public TextView movieTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.iv_movie);
            movieTitle = itemView.findViewById(R.id.tv_movie_title);
        }
    }

    public MovieRecyclerAdapter(ArrayList<Result> movies, Context context) {
        this.movies = movies;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        MovieRecyclerAdapter.ViewHolder viewHolder = new MovieRecyclerAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Result movie = movies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        Picasso.with(mContext).load(AppConfig.IMAGE_DOWNLOAD_URL + movie.getPosterPath()).into(holder.movieImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movieIntent = new Intent(mContext, MovieDetailsActivity.class);
                movieIntent.putExtra(AppConfig.MOVIE_INTENT, movie.getId());
                mContext.startActivity(movieIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
