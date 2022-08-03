package com.example.appsale.data.remote;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.appsale.common.AppConstant;
import com.example.appsale.data.local.AppCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pphat on 7/14/2022.
 */
public class RetrofitClient {

    private static RetrofitClient instance = null;
    private Retrofit retrofit;
    private ApiService apiService;

    private RetrofitClient() {
        retrofit = createRetrofit();
        apiService = retrofit.create(ApiService.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    /**
     * Create Instance Retrofit
     * @return Retrofit
     */
    private Retrofit createRetrofit() {
        HttpLoggingInterceptor logRequest = new HttpLoggingInterceptor();
        logRequest.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logRequest)
                .build();

        Gson gson = new GsonBuilder().create();

        return new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Get Interface End Point
     * @return ApiService
     */
    public ApiService getApiService(){
        if (apiService != null) {
            return apiService;
        } else {
            return retrofit.create(ApiService.class);
        }
    }
}