package com.zef.squats.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zef.squats.R;

import java.util.ArrayList;

import com.zef.squats.adapter.MovieGenreListRecyclerAdapter;
import com.zef.squats.apiexecutor.MovieApiInterface;
import com.zef.squats.apiexecutor.RetrofitClient;
import com.zef.squats.constants.AppConfig;
import com.zef.squats.dialog.LoadingDialogFragment;
import com.zef.squats.model.Genre;
import com.zef.squats.model.GenreListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zef on 15/10/18.
 */

public class GenreActivity extends AppCompatActivity {

    private MovieApiInterface apiService;
    private RecyclerView recyclerView;
    private MovieGenreListRecyclerAdapter recyclerAdapter;
    private ArrayList<Genre> genres = new ArrayList<>();
    private LoadingDialogFragment loadingDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        recyclerView = findViewById(R.id.rv_movies_genre_all);
        recyclerAdapter = new MovieGenreListRecyclerAdapter(genres, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        apiService = RetrofitClient.getClient();
        loadingDialogFragment = new LoadingDialogFragment(this);
        getMovieGenres();
    }

    private void getMovieGenres() {
        loadingDialogFragment.show();
        Call<GenreListResponse> call = apiService.getGenreList(AppConfig.API_KEY);
        call.enqueue(new Callback<GenreListResponse>() {
            @Override
            public void onResponse(Call<GenreListResponse> call, Response<GenreListResponse> response) {
                loadingDialogFragment.dismissDialog();
                genres.addAll(response.body().getGenres());
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GenreListResponse> call, Throwable t) {
                loadingDialogFragment.dismissDialog();
            }
        });
    }
}
