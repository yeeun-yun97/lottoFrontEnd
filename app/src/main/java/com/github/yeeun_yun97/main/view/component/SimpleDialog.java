package com.github.yeeun_yun97.main.view.component;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class SimpleDialog {
    public static AlertDialog getDialog(Activity activity, String title, String message, DialogInterface.OnClickListener confirmListener){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setTitle(title);
        builder.setPositiveButton("확인",confirmListener);
        builder.setNegativeButton("취소",null);
        AlertDialog dialog= builder.create();
        return dialog;
    }
}
