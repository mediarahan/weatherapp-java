package com.gbsrnov.caditassessment2weatherapp.remote;

import androidx.annotation.NonNull;

import com.gbsrnov.caditassessment2weatherapp.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    private static ApiConfig instance; //holds the single instance of the class

    private ApiConfig() {
        //default empty constructor
    }

    //Return the existing ApiConfig instance and creates one if there isn't already
    //Thus ensuring there's only one instance of the class; a Singleton.
    //Similar to companion object in Kotlin
    public static ApiConfig getInstance() {
        if (instance == null) {
            instance = new ApiConfig();
        }
        return instance;
    }

    //Api Config logic
    public static ApiService getApiService() {
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final Interceptor authInterceptor = new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                Request requestWithAuth = request.newBuilder()
                        .url(request.url().newBuilder().addQueryParameter("apikey", BuildConfig.API_KEY).build())
                        .build();
                return chain.proceed(requestWithAuth);
            }
        };

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dataservice.accuweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ApiService.class);
    }
}
