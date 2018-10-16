package com.zef.squats.apiexecutor;

import com.zef.squats.model.GenreListResponse;
import com.zef.squats.model.MovieListResponse;
import com.zef.squats.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by zef on 15/10/18.
 */

public interface MovieApiInterface {

    @GET("genre/movie/list")
    Call<GenreListResponse> getGenreList(@Query("api_key") String api_key);

    @GET("genre/{genre_id}/movies")
    Call<MovieListResponse> getMoviesByGenre( @Path("genre_id") int genre_id,@Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Call<MovieResponse> getMovieDetails(@Path("movie_id") int movie_id,@Query("api_key") String api_key);
}
