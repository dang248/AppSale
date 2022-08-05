package com.example.appsale.common;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.DecimalFormat;

/**
 * Created by pphat on 7/14/2022.
 */
public class StringCommon {

    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    /**
     * Format Currency By 0,000
     *
     * @param number int
     * @return String
     */
    public static String formatCurrency(int number) {
        return new DecimalFormat("#,###").format(number);

    }
}
