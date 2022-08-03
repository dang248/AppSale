package com.example.appsale.presentation.view.activity.sign_in;


import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appsale.R;
import com.example.appsale.data.model.User;
import com.example.appsale.data.remote.dto.AppResource;


/**
 * Created by ldang on 02/08/2022.
 */
public class SignInActivity extends AppCompatActivity {
    SignInViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initial();
        observerData();
        event();
    }

    //quan sát dữ liệu của livedata
    private void observerData() {
        viewModel.getResourceUser().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userAppResource) {
                switch (userAppResource.status) {
                    case SUCCESS:
                        break;
                    case LOADING:
                        break;
                    case ERROR:
                        break;
                }
            }
        });
    }

    //Dùng phương thức POST để lấy dữ liệu về từ server
    private void event() {
        viewModel.signIn("demo@gmail.com", "123456789");
    }

    //Khởi tạo ViewModel, Retrofit, LiveData
    private void initial() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignInViewModel();
            }
        }).get(SignInViewModel.class);
    }


}
