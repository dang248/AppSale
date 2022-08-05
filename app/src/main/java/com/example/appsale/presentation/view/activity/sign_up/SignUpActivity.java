package com.example.appsale.presentation.view.activity.sign_up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appsale.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText inputName, inputEmail, inputPassword, inputPhone, inputAddress;
    LinearLayout btnSignUp, layoutLoading;
    SignUpViewModel signUpViewModel;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}