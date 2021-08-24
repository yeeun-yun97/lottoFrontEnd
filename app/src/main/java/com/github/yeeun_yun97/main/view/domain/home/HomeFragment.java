package com.github.yeeun_yun97.main.view.domain.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.constant.Constant;
import com.github.yeeun_yun97.main.model.dto.domain.home.ReadLottoRecentRoundResponse;
import com.github.yeeun_yun97.main.model.dto.domain.home.ReadUserInfoRequest;
import com.github.yeeun_yun97.main.model.dto.domain.home.ReadUserInfoResponse;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.EScore;
import com.github.yeeun_yun97.main.retrofit.SimpleRetrofit;
import com.github.yeeun_yun97.main.viewmodel.SimpleViewModelObserverFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Response;

import static com.github.yeeun_yun97.main.model.constant.Constant.DATE_FORMAT_HANGUEL;
import static com.github.yeeun_yun97.main.model.constant.Constant.LOG_IN;
import static com.github.yeeun_yun97.main.model.constant.Constant.LOG_IN_MESSAGE;
import static com.github.yeeun_yun97.main.model.constant.Constant.LOG_OUT;
import static com.github.yeeun_yun97.main.model.constant.Constant.RECENT_ROUND_DATE_SUFFIX;
import static com.github.yeeun_yun97.main.model.constant.Constant.RECENT_ROUND_TITLE_SUFFIX;
import static com.github.yeeun_yun97.main.model.constant.Constant.USER_SUFFIX;

/**
 * 홈 프래그먼트.
 */
public class HomeFragment extends SimpleViewModelObserverFragment {

    //associate view
    private TextView dateTextView, userNameTextView, titleTextView, hitDateTextView, splitTextView, noInternetTextView;
//    private Button button;
    private Vector<TextView> balls;
    private CalendarView calendarView;
    private Chip loginOutChip;

    //model
    private Date date;

    @Override
    protected void doOnChange() {
        this.initLogin();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.balls = new Vector<>();
        this.dateTextView = view.findViewById(R.id.homeFragment_dateTextView);
        this.calendarView = view.findViewById(R.id.homeFragment_calendarView);

        this.userNameTextView = view.findViewById(R.id.userDataLayout_userNameTextView);
        this.loginOutChip = view.findViewById(R.id.userDataLayout_loginOutChip);

        this.titleTextView = view.findViewById(R.id.recentHitLayout_titleTextView);
        this.hitDateTextView = view.findViewById(R.id.recentHitLayout_hitDateTextView);
        this.splitTextView = view.findViewById(R.id.recentHitLayout_splitTextView);

        this.noInternetTextView = view.findViewById(R.id.noInternetLayout_alertTextView);

        this.balls.add(view.findViewById(R.id.recentHitLayout_firstBall));
        this.balls.add(view.findViewById(R.id.recentHitLayout_secondBall));
        this.balls.add(view.findViewById(R.id.recentHitLayout_thirdBall));
        this.balls.add(view.findViewById(R.id.recentHitLayout_fourthBall));
        this.balls.add(view.findViewById(R.id.recentHitLayout_fifthBall));
        this.balls.add(view.findViewById(R.id.recentHitLayout_sixthBall));
        this.balls.add(view.findViewById(R.id.recentHitLayout_bonusBall));

        this.loginOutChip.setOnClickListener(v -> this.doLogInOut());
        this.noInternetTextView.setOnClickListener(v -> this.initRecentRound());

        this.initRecentRound();
        this.initCalendar();
        this.initText();

//        this.button= view.findViewById(R.id.button);
//        this.button.setOnClickListener(v -> {
//            Navigation.findNavController(this.getView()).navigate(R.id.action_global_pickNumberFragment);
//        });
    }

    @Override
    public void onResume() {
        super.onResume();

        BottomNavigationView bottomNavigationView = this.getActivity().findViewById(R.id.mainActivity_bottomNavigationView);
        if (bottomNavigationView.getSelectedItemId() != R.id.bottomMenu_home)
            bottomNavigationView.findViewById(R.id.bottomMenu_home).performClick();
    }


    /*
    server rest api
    */
    private class ReadUserInfoCallback extends SimpleCallback<ReadUserInfoResponse> {
        @Override
        protected void onBothResponse() {
            return;
        }

        @Override
        protected void onSuccessfulResponse(Response<ReadUserInfoResponse> responseEntity) {
            ReadUserInfoResponse response = responseEntity.body();
            userNameTextView.setText(response.getName() + USER_SUFFIX);
            loginOutChip.setText(LOG_OUT);
        }
    }

    private void doReadUserInfo() {
        ReadUserInfoRequest request = ReadUserInfoRequest.builder().user_id(this.auth.getUser_id()).build();
        SimpleRetrofit.getUserService(this.auth.getToken()).readUserInfo(request).enqueue(new ReadUserInfoCallback());
    }

    private class ReadHitDataCallback extends SimpleCallback<ReadLottoRecentRoundResponse> {
        @Override
        protected void onBothResponse() {
            return;
        }

        @Override
        protected void onSuccessfulResponse(Response<ReadLottoRecentRoundResponse> responseEntity) {
            ReadLottoRecentRoundResponse response = responseEntity.body();

            titleTextView.setText(response.getRound_num() + RECENT_ROUND_TITLE_SUFFIX);
            hitDateTextView.setText(Constant.setStringWrapped(response.getEndDate() + RECENT_ROUND_DATE_SUFFIX));

            List<Integer> roundNumbers = response.getHitNumbers();
            roundNumbers.add(response.getBonusNumber());
            for (int i = 0; i < 7; i++) {
                this.setBall(balls.get(i), roundNumbers.get(i), i != 6 ? EScore.HIT : EScore.BONUS_HIT);
            }
            setRecentRoundLayoutVisibility(true);
        }

        private void setBall(TextView ball, Integer val, EScore flag) {
            ball.setText(String.valueOf(val));
            if (getView() != null)
                ball.setBackgroundTintList(ColorStateList.valueOf(Constant.getLottoColor(getView(), val, flag)));
        }

        @Override
        public void onFailure(Call<ReadLottoRecentRoundResponse> call, Throwable t) {
            super.onFailure(call, t);
            noInternetTextView.setVisibility(View.VISIBLE);
        }
    }

    private void doReadHitData() {
        this.showLoadingView();
        SimpleRetrofit.getPublicService().readLottoRecentRound().enqueue(new ReadHitDataCallback());
    }


    /*
    method
     */
    private void setRecentRoundLayoutVisibility(boolean isLoaded) {
        this.noInternetTextView.setVisibility(View.INVISIBLE);
        int loadedViewVisibility = !isLoaded ? View.INVISIBLE : View.VISIBLE;

        this.titleTextView.setVisibility(loadedViewVisibility);
        this.hitDateTextView.setVisibility(loadedViewVisibility);
        this.splitTextView.setVisibility(loadedViewVisibility);
        for (TextView textView : balls)
            textView.setVisibility(loadedViewVisibility);
    }

    private void initLogin() {
        if (isTokenNull()) {
            this.loginOutChip.setText(LOG_IN);
            this.userNameTextView.setText(LOG_IN_MESSAGE);
        } else {
            this.doReadUserInfo();
        }
    }

    private void initCalendar() {
        this.date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        long endOfMonth = calendar.getTimeInMillis();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        long startOfMonth = calendar.getTimeInMillis();
        this.calendarView.setMaxDate(endOfMonth);
        this.calendarView.setMinDate(startOfMonth);
        this.calendarView.setDate(this.date.getTime());
        this.calendarView.setFocusable(false);
    }

    private void initText() {
        this.dateTextView.setText(new SimpleDateFormat(DATE_FORMAT_HANGUEL).format(this.date));
    }

    private void initRecentRound() {
        this.setRecentRoundLayoutVisibility(false);
        this.doReadHitData();
    }

    private void doLogInOut() {
        if (loginOutChip.getText().toString().equals(LOG_IN)) {
            this.moveToLogin();
        } else {
            this.doLogout();
        }
    }

    private void doLogout() {
        this.auth.setToken(Constant.EValue.NULL.name());
        this.auth.setUser_id(Constant.EValue.NULL.getId());
        this.saveValues();
    }

    /*
    navigation
     */
    private void moveToLogin() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_global_loginFragment);
    }

}