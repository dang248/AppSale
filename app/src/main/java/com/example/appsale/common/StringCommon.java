package com.example.appsale.common;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.DecimalFormat;


public class StringCommon {

    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

     public static String formatCurrency(int number) {
        return new DecimalFormat("#,###").format(number);

    }
}
