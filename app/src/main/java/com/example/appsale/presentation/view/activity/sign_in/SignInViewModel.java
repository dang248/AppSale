package com.example.appsale.presentation.view.activity.sign_in;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale.data.model.User;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.data.remote.dto.UserDTO;
import com.example.appsale.data.repository.AuthenticationRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ldang on 03/08/2022.
 */
public class SignInViewModel extends ViewModel {
    private final AuthenticationRepository authenticationRepository;
    private MutableLiveData<AppResource<User>> resourceUser;

    public SignInViewModel(Context context) {
        authenticationRepository = new AuthenticationRepository();
        if (resourceUser == null) {
            resourceUser = new MutableLiveData<>();
        }
    }

    public LiveData<AppResource<User>> getResourceUser() {
        return resourceUser;
    }

    public void signIn(String email, String password) {
        resourceUser.setValue(new AppResource.Loading(null));
        authenticationRepository
                .signIn(email, password)
                .enqueue(new Callback<AppResource<UserDTO>>() {
                    @Override
                    public void onResponse(Call<AppResource<UserDTO>> call, Response<AppResource<UserDTO>> response) {
                        if (response.isSuccessful()) {
                            AppResource<UserDTO> resourceUserDTO = response.body();
                            if (resourceUserDTO != null) {
                                UserDTO userDTO = resourceUserDTO.data;
                                resourceUser.setValue(
                                        new AppResource.Success(
                                                new User(
                                                        userDTO.getEmail(),
                                                        userDTO.getName(),
                                                        userDTO.getPhone(),
                                                        userDTO.getUserGroup(),
                                                        userDTO.getRegisterDate(),
                                                        userDTO.getToken())));
                            }
                        }else {
                            if (response.errorBody() != null) {
                                try {
                                    JSONObject jsonObjectError = new JSONObject(response.errorBody().string());
                                    resourceUser.setValue(new AppResource.Error(jsonObjectError.getString("message")));
                                    //khi đó AppResource có data = null, message = "Lỗi", Status = ERROR
//                                    resourceUser.setValue(new AppResource.Error("Lỗi"));
//                                    Log.d("BBB",jsonObjectError.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                    }
                }

                    @Override
                    public void onFailure(Call<AppResource<UserDTO>> call, Throwable t) {
                        resourceUser.setValue(new AppResource.Error(t.getMessage()));

                    }

                });
    }
}
