package com.github.yeeun_yun97.main.view.domain.user.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.domain.user.login.LoginRequest;
import com.github.yeeun_yun97.main.model.dto.domain.user.login.LoginResponse;
import com.github.yeeun_yun97.main.retrofit.SimpleRetrofit;
import com.github.yeeun_yun97.main.viewmodel.Authorization;
import com.github.yeeun_yun97.main.viewmodel.SimpleViewModelObserverFragment;

import retrofit2.Response;

/**
 * 로그인 프래그먼트.
 */
public class LoginFragment extends SimpleViewModelObserverFragment {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private TextView forgetPasswordTextView;


    @Override
    protected void doOnChange() {
        return;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.emailEditText = view.findViewById(R.id.loginFragment_emailEditText);
        this.passwordEditText = view.findViewById(R.id.loginFragment_passwordEditText);
        this.loginButton = view.findViewById(R.id.loginFragment_loginButton);
        this.registerButton = view.findViewById(R.id.loginFragment_registerButton);
        this.forgetPasswordTextView = view.findViewById(R.id.loginFragment_forgetPasswordTextView);

        this.loginButton.setOnClickListener(v -> this.doLogin());
        this.registerButton.setOnClickListener(v -> this.moveToRegister());
        this.forgetPasswordTextView.setOnClickListener(v -> this.moveToFindPassword());
    }

    /*
    server rest api
    */
    private class LoginCallback extends SimpleCallback<LoginResponse> {
        @Override
        protected void onBothResponse() {
            return;
        }

        @Override
        protected void onSuccessfulResponse(Response<LoginResponse> responseEntity) {
            LoginResponse loginResponse = responseEntity.body();
            auth= Authorization.builder().token(loginResponse.getToken()).user_id(loginResponse.getUser_id()).build();
            saveValues();
            moveToHome();
        }
    }

    private void doLogin() {
        this.showLoadingView();
        LoginRequest request = LoginRequest.builder().email(this.emailEditText.getText().toString()).password((this.passwordEditText.getText().toString())).build();
        SimpleRetrofit.getPublicService().login(request).enqueue(new LoginCallback());
    }

    /*
    navigation
     */
    private void moveToHome() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_global_homeFragment);
    }

    private void moveToFindPassword() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_loginFragment_to_findPasswordFragment);
    }

    private void moveToRegister() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_loginFragment_to_registerFragment);
    }


}
