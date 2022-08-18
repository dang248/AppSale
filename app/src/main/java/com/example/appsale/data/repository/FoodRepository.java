package com.example.appsale.data.repository;

import android.content.Context;

import com.example.appsale.data.remote.ApiService;
import com.example.appsale.data.remote.RetrofitClient;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.data.remote.dto.FoodDTO;


import java.util.List;

import retrofit2.Call;

public class FoodRepository {
    private ApiService apiService;

    public FoodRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<List<FoodDTO>>> fetchFoods() {
        return apiService.fetchFoods();
    }
}
