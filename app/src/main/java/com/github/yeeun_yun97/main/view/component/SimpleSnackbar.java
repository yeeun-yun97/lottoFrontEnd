package com.github.yeeun_yun97.main.view.component;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SimpleSnackbar {

    public static Snackbar getSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(
                "확인",
                v -> snackbar.dismiss()
        );
        return snackbar;
    }


}
