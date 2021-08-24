package com.github.yeeun_yun97.main.view.domain.lotto.pick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.SimpleOkResponse;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.pick.CreateNumberCombinationRequest;
import com.github.yeeun_yun97.main.retrofit.SimpleRetrofit;
import com.github.yeeun_yun97.main.view.component.NumberBall;
import com.github.yeeun_yun97.main.view.component.SimpleDialog;
import com.github.yeeun_yun97.main.viewmodel.SimpleViewModelObserverFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import retrofit2.Response;

import static com.github.yeeun_yun97.main.model.constant.Constant.NOT_LOGIN_TO_SAVE_MESSAGE;
import static com.github.yeeun_yun97.main.model.constant.Constant.NOT_LOGIN_TO_SAVE_TITLE;
import static com.github.yeeun_yun97.main.model.constant.Constant.SAVED_ALREADY_MESSAGE;
import static com.github.yeeun_yun97.main.model.constant.Constant.SAVED_ALREADY_TITLE;
import static com.github.yeeun_yun97.main.model.constant.Constant.SAVE_HISTORY_SUCCESS;

/**
 * 예측 프래그먼트
 */
public class PickFragment extends SimpleViewModelObserverFragment {
    //view
    private Button retryButton, saveButton;
    private Vector<NumberBall> numberBalls;
//    private Chip modeChip;
    private ProgressBar pickProgressBar;

    //working variable
    private int[] number;
    private Long predict_id;
    private boolean isPredictSaved;
//    private Constant.EPredictMode predictMode;

    public PickFragment() {
        this.numberBalls = new Vector<>();
        this.number = new int[6];
        this.isPredictSaved = false;
        this.predict_id = null;
//        this.predictMode= Constant.EPredictMode.RANDOM;
    }

    @Override
    protected void doOnChange() {
//        this.initMode();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_pick;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.numberBalls.add(view.findViewById(R.id.pickFragment_numberBall1));
        this.numberBalls.add(view.findViewById(R.id.pickFragment_numberBall2));
        this.numberBalls.add(view.findViewById(R.id.pickFragment_numberBall3));
        this.numberBalls.add(view.findViewById(R.id.pickFragment_numberBall4));
        this.numberBalls.add(view.findViewById(R.id.pickFragment_numberBall5));
        this.numberBalls.add(view.findViewById(R.id.pickFragment_numberBall6));
        this.pickProgressBar = view.findViewById(R.id.pickFragment_progressBar);

        this.retryButton = view.findViewById(R.id.pickFragment_retryButton);
        this.saveButton = view.findViewById(R.id.pickFragment_saveButton);
//        this.modeChip = view.findViewById(R.id.pickFragment_modeChip);

        this.retryButton.setOnClickListener(v -> this.doPredict());
        this.saveButton.setOnClickListener(v -> this.doSave());
//        this.modeChip.setOnClickListener(v -> this.setPredictMode(this.predictMode.toggle()));
        this.initNumberBalls();
        this.doPredict();
    }


    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = this.getActivity().findViewById(R.id.mainActivity_bottomNavigationView);
        if (bottomNavigationView.getSelectedItemId() != R.id.bottomMenu_pick)
            bottomNavigationView.findViewById(R.id.bottomMenu_pick).performClick();
    }

    /*
    server rest api
    */
    private class SavePredictCallback extends SimpleCallback<SimpleOkResponse> {
        @Override
        protected void onBothResponse() {
        }

        @Override
        protected void onSuccessfulResponse(Response<SimpleOkResponse> responseEntity) {
            isPredictSaved = true;
            Toast.makeText(getActivity(), SAVE_HISTORY_SUCCESS, Toast.LENGTH_SHORT).show();
        }
    }

    private void doSave() {
        if (this.isPredictSaved) {
            this.showAlreadySavedDialog();
        } else {
            this.save();
        }
    }

    private void save() {
        if (this.isTokenNull()) {
            this.showNoLoginDialog();
        } else {
            CreateNumberCombinationRequest request = CreateNumberCombinationRequest.builder()
                    .user_id(this.auth.getUser_id())
                    .numbers(this.number)
                    .build();
            SimpleRetrofit.getUserService(this.auth.getToken()).createNumberCombination(request).enqueue(new SavePredictCallback());
        }
    }


//    private class PredictCallback extends SimpleCallback<LottoPredictResponse> {
//        @Override
//        protected void onBothResponse() {
//        }
//
//        @Override
//        protected void onSuccessfulResponse(Response<LottoPredictResponse> responseEntity) {
//            LottoPredictResponse response = responseEntity.body();
//            isPredictSaved = false;
//            predict_id = response.getPredict_id();
//            List<Integer> list = response.getNumberList();
//            for (int i = 0; i < number.length; i++)
//                number[i] = list.get(i);
//            showBalls();
//        }
//    }

    private void doPredict() {
        initNumberBalls();
//        if (this.predictMode == Constant.EPredictMode.RANDOM) {
            this.predict_id = null;
            this.random();
            this.showBalls();
//        }

//        else {
//            if (!this.isTokenNull()) {
//                SimpleRetrofit.getPublicService().predict(this.auth.getUser_id()).enqueue(new PredictCallback());
//            } else
//                this.doPredictAnonymous();
//        }
    }


    /*
    method
     */
    private void initNumberBalls() {
        for (NumberBall numberBall : this.numberBalls) {
            numberBall.setVisibility(View.INVISIBLE);
        }
        this.pickProgressBar.setVisibility(View.VISIBLE);
    }

//    private void initMode() {
//        this.setPredictMode(Constant.EPredictMode.PREDICT);
//    }

    private void showBalls() {
        this.pickProgressBar.setVisibility(View.INVISIBLE);
        for (int i = 0; i < 6; i++) {
            NumberBall numberBall = this.numberBalls.get(i);
            numberBall.setNumber(this.number[i]);
            numberBall.setVisibility(View.VISIBLE);
            numberBall.showAnimation();
        }
    }

//    private void doPredictAnonymous() {
//        Log.d("익명 뽑기", "익명뽑기?");
//        SimpleRetrofit.getPublicService().predict(1L).enqueue(new PredictCallback());
//    }

    private void random() {
        Random random = new Random();
        for (int i = 0; i < 6; ) {
            int newNumber = random.nextInt(45) + 1;
            if (!Arrays.asList(this.number).contains(newNumber))
                this.number[i++] = newNumber;
        }
        Arrays.sort(this.number);
    }

//    private void setPredictMode(Constant.EPredictMode predictMode) {
////        this.predictMode = predictMode;
//        this.modeChip.setText(predictMode.getText());
//        doPredict();
//    }

    /*
    show alert dialog
     */
    private void showNoLoginDialog() {
        AlertDialog ADialog = SimpleDialog.getDialog(
                this.getActivity(),
                NOT_LOGIN_TO_SAVE_TITLE,
                NOT_LOGIN_TO_SAVE_MESSAGE,
                (dialog, which) -> this.moveToLogin());
        this.alertDialogs.add(ADialog);
        ADialog.show();
    }

    private void showAlreadySavedDialog() {
        AlertDialog ADialog = SimpleDialog.getDialog(
                this.getActivity(),
                SAVED_ALREADY_TITLE,
                SAVED_ALREADY_MESSAGE,
                (dialog, which) -> this.moveToHistory()
        );
        this.alertDialogs.add(ADialog);
        ADialog.show();
    }

    /*
    navigation
     */
    private void moveToLogin() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_global_loginFragment);
    }

    private void moveToHistory() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_predictFragment_to_historyFragment);
    }
}