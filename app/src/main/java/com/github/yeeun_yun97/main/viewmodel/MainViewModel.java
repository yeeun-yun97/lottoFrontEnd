package com.github.yeeun_yun97.main.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.github.yeeun_yun97.main.model.constant.Constant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainViewModel extends ViewModel {

    private MutableLiveData<Authorization> auth;

    public MainViewModel() {
        Constant.EValue defaultVal= Constant.EValue.NULL;
        Authorization auth= Authorization.builder().token(defaultVal.name()).user_id(defaultVal.getId()).build();

        this.auth= new MutableLiveData<>();
        this.auth.setValue(auth);
    }

    public void observeStart(LifecycleOwner owner,Observer observer){
        this.auth.observe(owner,observer);
    }

    public void observeStop(Observer observer){
        this.auth.removeObserver(observer);
    }

    public void saveValue(Authorization auth){
        this.auth.setValue(auth);
    }

    public Authorization getAuthValue(){return this.auth.getValue();}

}
