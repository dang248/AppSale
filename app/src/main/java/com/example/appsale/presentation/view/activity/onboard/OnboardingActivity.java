package com.example.appsale.presentation.view.activity.onboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsale.R;

public class OnboardingActivity extends AppCompatActivity {
    LinearLayout btnGetStarted;
    TextView tvRequestLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        btnGetStarted = findViewById(R.id.button_get_started);
        tvRequestLogin = findViewById(R.id.textview_request_login);
        setTextRequestLogin();
    }

    private void setTextRequestLogin(){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Already Have An Account?");
        int start = builder.length();
        builder.append("Log In");
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(OnboardingActivity.this, "click me", Toast.LENGTH_SHORT).show();
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