package com.zef.squats.apiexecutor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.zef.squats.constants.AppConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zef on 15/10/18.
 */

public class RetrofitClient {


    public static final String BASE_URL = AppConfig.MOVIE_BASE_URL;
    private static MovieApiInterface service;



    public static MovieApiInterface getClient() {
        if (service == null) {
            Gson gson = new GsonBuilder()
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();

            service = retrofit.create(MovieApiInterface.class);
        }
        return service;
    }
}
