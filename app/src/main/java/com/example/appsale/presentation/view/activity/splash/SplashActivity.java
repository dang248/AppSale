package com.example.appsale.presentation.view.activity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.appsale.R;
import com.example.appsale.common.AppConstant;
import com.example.appsale.data.local.AppCache;
import com.example.appsale.presentation.view.activity.home.HomeActivity;
import com.example.appsale.presentation.view.activity.onboard.OnboardingActivity;
import com.example.appsale.presentation.view.activity.sign_in.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        navigateToDestination();
    }
    private void navigateToDestination(){
        new Handler().postDelayed(() -> {
            Boolean isFirstTimeDisplay = (Boolean) AppCache.getInstance(SplashActivity.this)
                    .getValue(AppConstant.ONBOARD_DING_FIRST_TIME_DISPLAY_KEY);
            Intent intent ;
            if(isFirstTimeDisplay == null || !isFirstTimeDisplay){
                intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            }
            else{
                String token = (String) AppCache.getInstance(SplashActivity.this).getValue(AppConstant.TOKEN_KEY);
                if (token != null && !token.isEmpty()) {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, SignInActivity.class);
                }
            }
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);
        }, AppConstant.TIME_MILLISECOND_DELAY);
    }
}