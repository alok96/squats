package com.zef.squats.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zef.squats.R;

import com.zef.squats.apiexecutor.RetrofitClient;
import com.zef.squats.constants.AppConfig;
import com.zef.squats.dialog.LoadingDialogFragment;
import com.zef.squats.model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zef on 15/10/18.
 */

public class MovieDetailsActivity extends AppCompatActivity {
    private int movieId;
    private ImageView cover;
    private TextView movieTitle;
    private TextView language;
    private TextView releaseDate;
    private TextView popularity;
    private ImageView poster;
    private TextView description;
    private ImageView backButton;

    private LoadingDialogFragment loadingDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieId  = getIntent().getIntExtra(AppConfig.MOVIE_INTENT,0);
        cover = findViewById(R.id.iv_cover);
        movieTitle = findViewById(R.id.tv_movie_title);
        language = findViewById(R.id.tv_language);
        releaseDate = findViewById(R.id.tv_release_date);
        popularity = findViewById(R.id.tv_rest_open_status);
        description = findViewById(R.id.tv_description);
        poster = findViewById(R.id.iv_poster);
        backButton = findViewById(R.id.iv_back_theme);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadingDialogFragment = new LoadingDialogFragment(this);
        getMovieDetails(movieId);
    }

    private void getMovieDetails(int movieId){
        loadingDialogFragment.show();
        Call<MovieResponse> call = RetrofitClient.getClient().getMovieDetails(movieId,AppConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
               setData(response.body());
               loadingDialogFragment.dismissDialog();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                loadingDialogFragment.dismissDialog();
            }
        });
    }

    private void setData(MovieResponse movieResponse){
        movieTitle.setText(movieResponse.getTitle());
        description.setText(movieResponse.getOverview());
        language.setText(movieResponse.getOriginalLanguage());
        releaseDate.setText(movieResponse.getReleaseDate());
        Picasso.with(this).load(AppConfig.IMAGE_DOWNLOAD_URL+movieResponse.getPosterPath()).into(poster);
        Picasso.with(this).load(AppConfig.IMAGE_DOWNLOAD_URL+movieResponse.getBackdropPath()).into(cover);
        popularity.setText("Popularity :"+movieResponse.getPopularity());
    }
}
