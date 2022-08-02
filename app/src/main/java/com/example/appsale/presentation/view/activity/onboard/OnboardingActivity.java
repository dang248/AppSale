package com.example.appsale.presentation.view.activity.onboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appsale.R;
import com.example.appsale.common.AppConstant;
import com.example.appsale.data.local.AppCache;
import com.example.appsale.presentation.view.activity.sign_in.SignInActivity;
import com.example.appsale.presentation.view.adapter.OnboarddingPagerAdapter;

public class OnboardingActivity extends AppCompatActivity {
    LinearLayout btnGetStarted;
    TextView tvRequestLogin;
    ViewPager2 onboarddingViewPager;
    OnboarddingPagerAdapter onboarddingPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        //Initial
        initial();

        event();

        //Request Login
        setTextRequestLogin();



    }

    private void event() {
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nevigateLoginScreen();

            }
        });
    }

    private void nevigateLoginScreen(){
        AppCache.getInstance(OnboardingActivity.this)
                .setValue(AppConstant.ONBOARD_DING_FIRST_TIME_DISPLAY_KEY, true)
                .commit();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.alpha_fade_in, R.anim.alpha_fade_out);

    }
    private void initial() {
        btnGetStarted = findViewById(R.id.button_get_started);
        tvRequestLogin = findViewById(R.id.textview_request_login);
        onboarddingViewPager = findViewById(R.id.view_pager_onboard_ding);

        onboarddingPagerAdapter = new OnboarddingPagerAdapter(this);
        onboarddingViewPager.setAdapter(onboarddingPagerAdapter);
    }



    private void setTextRequestLogin(){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Already Have An Account?");
        int start = builder.length();
        builder.append("Log In");
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                nevigateLoginScreen();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor((getResources().getColor(R.color.primary)));
            }
        }, start, builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvRequestLogin.setText(builder);
        tvRequestLogin.setMovementMethod(LinkMovementMethod.getInstance());

    }

}