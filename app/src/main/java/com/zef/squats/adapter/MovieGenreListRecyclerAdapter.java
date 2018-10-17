package com.zef.squats.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zef.squats.R;

import java.util.ArrayList;

import com.zef.squats.apiexecutor.RetrofitClient;
import com.zef.squats.constants.AppConfig;
import com.zef.squats.model.Genre;
import com.zef.squats.model.MovieListResponse;
import com.zef.squats.model.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zef on 16/10/18.
 */

public class MovieGenreListRecyclerAdapter extends RecyclerView.Adapter<MovieGenreListRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Genre> genres;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView genre;
        public RecyclerView movieRecyclerView;
        public ArrayList<Result> movies = new ArrayList<>();
        public LinearLayoutManager linearLayoutManager;
        public MovieRecyclerAdapter movieRecyclerAdapter;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            genre = itemLayoutView.findViewById(R.id.tv_genre);
            movieRecyclerView = itemLayoutView.findViewById(R.id.rv_movies_list);
        }
    }

    public MovieGenreListRecyclerAdapter(ArrayList<Genre> genres, Context mContext) {
        this.genres = genres;
        this.mContext = mContext;
    }

    @Override
    public MovieGenreListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Genre genre = genres.get(position);
        viewHolder.genre.setText(genre.getName());

        viewHolder.linearLayoutManager = new LinearLayoutManager(mContext);
        viewHolder.linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.movieRecyclerAdapter = new MovieRecyclerAdapter(viewHolder.movies, mContext);
        viewHolder.movieRecyclerView.setAdapter(viewHolder.movieRecyclerAdapter);
        viewHolder.movieRecyclerView.setLayoutManager(viewHolder.linearLayoutManager);

        getMoviesByGenre(genre.getId(), viewHolder);
    }


    @Override
    public int getItemCount() {
        return genres.size();
    }

    private void getMoviesByGenre(int genreId, final ViewHolder viewHolder) {
        Call<MovieListResponse> call = RetrofitClient.getClient().getMoviesByGenre(genreId, AppConfig.API_KEY);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.body() != null && response.body().getResults() != null) {
                    viewHolder.movies.clear();
                    viewHolder.movies.addAll(response.body().getResults());
                    viewHolder.movieRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {

            }
        });

    }
}