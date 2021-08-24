package com.github.yeeun_yun97.main.view.domain.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.viewmodel.SimpleViewModelObserverFragment;

import org.jetbrains.annotations.NotNull;

/**
 * 아직 미구현.
 */
public class FindPasswordFragment extends SimpleViewModelObserverFragment {

    private EditText emailEditText;
    private Button submitButton;

    @Override
    protected void doOnChange() {return;}

    @Override
    protected int layoutId() {
        return R.layout.fragment_find_password;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.emailEditText=view.findViewById(R.id.findPasswordFragment_emailEditText);
        this.submitButton=view.findViewById(R.id.findPasswordFragment_submitButton);
        this.submitButton.setOnClickListener(v-> this.doFindPassword());
    }

    private void doFindPassword(){
        this.showSnackBarMessage("아직 미구현입니다.");
    }
}