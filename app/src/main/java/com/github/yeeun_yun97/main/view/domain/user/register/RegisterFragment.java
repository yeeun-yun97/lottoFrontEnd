package com.github.yeeun_yun97.main.view.domain.user.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.domain.user.register.RegisterRequest;
import com.github.yeeun_yun97.main.model.dto.domain.user.register.RegisterResponse;
import com.github.yeeun_yun97.main.retrofit.SimpleRetrofit;
import com.github.yeeun_yun97.main.view.component.SimpleDialog;
import com.github.yeeun_yun97.main.viewmodel.SimpleViewModelObserverFragment;

import org.jetbrains.annotations.NotNull;

import retrofit2.Response;

import static com.github.yeeun_yun97.main.model.constant.Constant.REGISTER_SUCCESS_MESSAGE;
import static com.github.yeeun_yun97.main.model.constant.Constant.REGISTER_SUCCESS_TITLE;


public class RegisterFragment extends SimpleViewModelObserverFragment {

    //associate view
    private AlertDialog dialog;
    private EditText emailEditText, passwordEditText, nameEditText;
    private Button registerButton;

    @Override
    protected void doOnChange() {

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.emailEditText = view.findViewById(R.id.registerFragment_emailEditText);
        this.passwordEditText = view.findViewById(R.id.registerFragment_passwordEditText);
        this.nameEditText = view.findViewById(R.id.registerFragment_nameEditText);
        this.registerButton = view.findViewById(R.id.registerFragment_registerButton);
        this.registerButton.setOnClickListener(v -> this.doRegister());
    }

    /*
    server rest api
     */
    private class RegisterCallback extends SimpleCallback<RegisterResponse> {
        @Override
        protected void onBothResponse() {
            return;
        }

        @Override
        protected void onSuccessfulResponse(Response<RegisterResponse> responseEntity) {
            dialog = SimpleDialog.getDialog(
                    getActivity(),
                    REGISTER_SUCCESS_TITLE,
                    REGISTER_SUCCESS_MESSAGE,
                    (dialog1, which) -> moveToHomeFragment()
            );
            dialog.show();
        }
    }

    private void doRegister() {
        RegisterRequest request =
                RegisterRequest.builder().email(this.emailEditText.getText().toString())
                        .password(this.passwordEditText.getText().toString())
                        .name(this.nameEditText.getText().toString())
                        .build();
        this.showLoadingView();
        SimpleRetrofit.getPublicService().register(request).enqueue(new RegisterCallback());
    }

    /*
    navigation
     */
    private void moveToHomeFragment(){
        Navigation.findNavController(getView()).navigate(R.id.action_global_homeFragment);
    }
}