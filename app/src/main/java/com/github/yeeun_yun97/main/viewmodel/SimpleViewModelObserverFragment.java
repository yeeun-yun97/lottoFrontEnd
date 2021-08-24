package com.github.yeeun_yun97.main.viewmodel;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.yeeun_yun97.main.model.constant.Constant;
import com.github.yeeun_yun97.main.retrofit.SimpleRetrofitCallbackFragment;

public abstract class SimpleViewModelObserverFragment extends SimpleRetrofitCallbackFragment implements Observer {

    private MainViewModel viewModel;
    protected Authorization auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewModel = new ViewModelProvider(this.getActivity()).get(MainViewModel.class);
        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public void onResume() {//프래그먼트가 시작될 때, token의 값을 가져오고, 감시 시작.
        super.onResume();
        this.viewModel.observeStart(this.getActivity(), this);
        this.getValues();
    }

    @Override
    public void onPause() {//프래그먼트가 멈출 때, token의 값을 저장하고, 감시 중지.
        super.onPause();
        this.saveValues();
        this.viewModel.observeStop(this);
    }

    private void getValues() {//값 가져오기.
        this.auth=this.viewModel.getAuthValue();
    }

    protected void saveValues() {//값 저장하기.
        this.viewModel.saveValue(this.auth);
        Log.d("저장되는 값",auth.getUser_id()+"/"+auth.getToken());
    }

    protected boolean isTokenNull() {
        if (this.auth.getToken() == null)//널값을 "NULL"로 교체.
            this.auth.setToken(Constant.EValue.NULL.name());
        return this.auth.getToken().equals(Constant.EValue.NULL.name());
    }

    @Override
    public void onChanged(Object o) {//감시하다 변화를 느끼는 부분.
        this.getValues();
        this.doOnChange();
    }

    protected abstract void doOnChange();

    protected abstract int layoutId();
}
