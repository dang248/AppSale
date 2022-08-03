package com.example.appsale.data.repository;

import com.example.appsale.data.remote.ApiService;
import com.example.appsale.data.remote.RetrofitClient;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.data.remote.dto.UserDTO;

import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by ldang on 03/08/2022.
 */
public class AuthenticationRepository {
    private ApiService apiService;

    public AuthenticationRepository() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    public Call<AppResource<UserDTO>> signIn(String email, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return apiService.signIn(map);
    }

}
