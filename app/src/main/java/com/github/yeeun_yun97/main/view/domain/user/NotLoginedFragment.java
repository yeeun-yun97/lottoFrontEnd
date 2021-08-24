package com.github.yeeun_yun97.main.view.domain.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.viewmodel.SimpleViewModelObserverFragment;

/**
 * 로그인이 안 되어있다고 알려주는 프래그먼트.
 */
public class NotLoginedFragment extends SimpleViewModelObserverFragment {

    //associate view
    private Button loginButton;

    @Override
    protected void doOnChange() {
        return;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_not_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.loginButton = view.findViewById(R.id.notLoginedFragment_loginButton);
        this.loginButton.setOnClickListener(v -> this.moveToLogin());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!this.isTokenNull()) this.moveToHistory();
    }

    /*
    navigation
    */
    private void moveToHistory() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_notLoginedFragment_to_historyFragment);
    }

    private void moveToLogin() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_notLoginedFragment_to_loginFragment);
    }
}
