package com.github.yeeun_yun97.main.view.domain.lotto.pick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.SimpleOkResponse;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.pick.CreateNumberCombinationRequest;
import com.github.yeeun_yun97.main.retrofit.SimpleRetrofit;
import com.github.yeeun_yun97.main.view.component.PickNumber;
import com.github.yeeun_yun97.main.view.component.SimpleDialog;
import com.github.yeeun_yun97.main.viewmodel.SimpleViewModelObserverFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

import retrofit2.Response;

import static com.github.yeeun_yun97.main.model.constant.Constant.NOT_LOGIN_TO_SAVE_MESSAGE;
import static com.github.yeeun_yun97.main.model.constant.Constant.NOT_LOGIN_TO_SAVE_TITLE;

public class PickNumberFragment extends SimpleViewModelObserverFragment {

    private Button retryButton, saveButton;
    private PickNumber pickNumber;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        this.pickNumber = view.findViewById(R.id.pickNumberFragment_pickNumber);
        this.retryButton = view.findViewById(R.id.pickFragment_retryButton);
        this.saveButton = view.findViewById(R.id.pickFragment_saveButton);

        this.retryButton.setOnClickListener(v -> this.random());
//        this.saveButton.setOnClickListener(v -> this.doSave());
    }

    @Override
    protected void doOnChange() {
        if (!this.isTokenNull()) {
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_pick_number;
    }

    private class CreateNumberCombinationCallback extends SimpleCallback<SimpleOkResponse> {

        @Override
        protected void onBothResponse() {

        }

        @Override
        protected void onSuccessfulResponse(Response<SimpleOkResponse> responseEntity) {

        }
    }

    private void random() {
        Random random = new Random();
        int[] number = this.pickNumber.getNumbers();

        for (int i = 0; i < 6; ) {
            if (number[i] != -1) {
                i++;
                continue;
            }
            int newNumber = random.nextInt(45) + 1;
            if (!Arrays.asList(number).contains(newNumber))
                number[i++] = newNumber;
        }
        Arrays.sort(number);
        this.pickNumber.setValues(number);
    }


    private void save() {
        if (!this.isTokenNull()) {
            this.showNoLoginDialog();
        } else {
            CreateNumberCombinationRequest request = CreateNumberCombinationRequest.builder()
                    .user_id(this.auth.getUser_id())
                    .numbers(this.getNumbers())
                    .build();
            SimpleRetrofit.getUserService(this.auth.getToken()).createNumberCombination(request).enqueue(new CreateNumberCombinationCallback());
        }
    }

    private int[] getNumbers() {
        return this.pickNumber.getNumbers();
    }

    private void showNoLoginDialog() {
        AlertDialog ADialog = SimpleDialog.getDialog(
                this.getActivity(),
                NOT_LOGIN_TO_SAVE_TITLE,
                NOT_LOGIN_TO_SAVE_MESSAGE,
                (dialog, which) -> this.moveToLogin());
        this.alertDialogs.add(ADialog);
        ADialog.show();
    }

    private void moveToLogin() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_global_loginFragment);
    }

}
