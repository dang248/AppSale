package com.example.appsale.presentation.view.activity.sign_in;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appsale.R;
import com.example.appsale.common.StringCommon;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Created by ldang on 02/08/2022.
 */
public class SignInActivity extends AppCompatActivity {
    SignInViewModel viewModel;
    LinearLayout layoutLoading, btnSignIn;
    TextInputEditText txtInputEditEmail, txtInputEditPassword;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        
        initial();
     
        event();
    }

    private void event() {
        viewModel.signIn("demo1@gmail.com", "123456789" );

    }

    private void initial() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignInViewModel(SignInActivity.this);
            }
        }).get(SignInViewModel.class);
    }
}
