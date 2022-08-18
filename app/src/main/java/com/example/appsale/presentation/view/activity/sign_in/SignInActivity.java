package com.example.appsale.presentation.view.activity.sign_in;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appsale.R;
import com.example.appsale.common.AppConstant;
import com.example.appsale.common.SpannedCommon;
import com.example.appsale.common.StringCommon;
import com.example.appsale.data.local.AppCache;
import com.example.appsale.data.model.User;
import com.example.appsale.data.remote.dto.AppResource;
import com.example.appsale.presentation.view.activity.home.HomeActivity;
import com.example.appsale.presentation.view.activity.sign_up.SignUpActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    SignInViewModel viewModel;
    LinearLayout layoutLoading, btnSignIn;
    TextInputEditText txtInputEditEmail, txtInputEditPassword;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        initial();
        observerData();
        event();
    }

    //quan sát, cập nhật dữ liệu của livedata
    private void observerData() {
        viewModel.getResourceUser().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userAppResource) {
                switch (userAppResource.status) {
                    case SUCCESS:
                        Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        AppCache.getInstance(SignInActivity.this)
                                .setValue(AppConstant.TOKEN_KEY, userAppResource.data.getToken())
                                .commit();
                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
                        break;
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        Toast.makeText(SignInActivity.this, userAppResource.message, Toast.LENGTH_SHORT).show();
                        layoutLoading.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }
    List<String> list = Arrays.asList("oo");
    //Dùng phương thức POST để lấy dữ liệu về từ server
    private void event() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtInputEditEmail.getText().toString();
                String password = txtInputEditPassword.getText().toString();
                if (StringCommon.isValidEmail(email) && !password.isEmpty()) {
                    viewModel.signIn(email, password);
                }
            }
        });

        // Set Click Register
        setTextRegister();
    }

    //Khởi tạo ViewModel (Retrofit, LiveData)
    private void initial() {
        layoutLoading = findViewById(R.id.layout_loading);
        txtInputEditEmail = findViewById(R.id.textEditEmail);
        txtInputEditPassword = findViewById(R.id.textEditPassword);
        btnSignIn = findViewById(R.id.sign_in);
        tvRegister = findViewById(R.id.text_view_register);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SignInViewModel(SignInActivity.this);
            }
        }).get(SignInViewModel.class);
    }
    private void setTextRegister() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Don't have an account?");
        builder.append(SpannedCommon.setClickColorLink("Register", this, () -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
        }));
        tvRegister.setText(builder);
        tvRegister.setHighlightColor(Color.TRANSPARENT);
        tvRegister.setMovementMethod(
                LinkMovementMethod.getInstance());
    }


}
