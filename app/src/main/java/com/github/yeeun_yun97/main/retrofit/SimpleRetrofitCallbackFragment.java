package com.github.yeeun_yun97.main.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.constant.Constant;
import com.github.yeeun_yun97.main.view.component.SimpleSnackbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class SimpleRetrofitCallbackFragment extends Fragment {

    protected View snackbarView;
    private ProgressBar progressBar;

    protected Snackbar snackbar;
    protected Vector<AlertDialog> alertDialogs;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.alertDialogs = new Vector<>();
        this.snackbarView = getActivity().findViewById(R.id.mainActivity_placeSnackBar);
        this.progressBar = getActivity().findViewById(R.id.mainActivity_progressBar);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.dismissAll();
    }

    @Override
    public void onDestroy() {
        this.dismissAll();
        super.onDestroy();
    }

    private void dismissAll() {
        if (snackbar != null) snackbar.dismiss();
        for (AlertDialog alertDialog : alertDialogs) alertDialog.dismiss();
    }

    protected void showLoadingView() {
        this.progressBar.setVisibility(View.VISIBLE);
        if (this.snackbar != null) this.snackbar.dismiss();
    }

    protected void showSnackBarMessage(String message) {
        snackbar = SimpleSnackbar.getSnackbar(snackbarView, message);
        snackbar.show();
    }


    protected void hideLoadingView() {
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    protected abstract class SimpleCallback<T> implements Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            hideLoadingView();
            if (response.isSuccessful()) this.onSuccessfulResponse(response);
            else this.onFailResponse(response.code());
            this.onBothResponse();
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            hideLoadingView();
            showSnackBarMessage(this.setFailureMessage());
            Log.d("연결 실패 :" + this.getClass().getSimpleName(), t.getMessage());
        }

        protected void onFailResponse(int code) {
            showSnackBarMessage("에러가 발생하였습니다. 코드: " + Constant.setStringWrapped(String.valueOf(code)));
            Log.d("연결 오류 :" + this.getClass().getSimpleName(), code + "코드 오류");
        }

        public String setFailureMessage() {
            return "서버와의 연결이 불안정합니다.";
        }

        protected abstract void onBothResponse();

        protected abstract void onSuccessfulResponse(Response<T> responseEntity);

    }
}
