package com.github.yeeun_yun97.main.model.constant;

import android.view.View;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.EScore;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constant {



    public final static String SYSTEM_OFF_DIALOG_TITLE="앱을 종료하시겠습니까?";
    public final static String SYSTEM_OFF_DIALOG_MESSAGE="확인을 누르시면 앱이 종료됩니다.";

    //homeFragment
    public final static String USER_SUFFIX=" 님";
    public final static String LOG_IN="로그인";
    public final static String LOG_IN_MESSAGE="로그인 해 주세요";
    public final static String LOG_OUT="로그아웃";

    public final static String RECENT_ROUND_TITLE_SUFFIX="회 당첨결과";
    public final static String RECENT_ROUND_DATE_SUFFIX=" 추첨";

    public final static String DATE_FORMAT_HANGUEL="yyyy년 MM월 dd일";


    //predictFragment
    public final static String SAVE_HISTORY_SUCCESS="성공적으로 저장하였습니다.";
    public final static String SAVED_ALREADY_TITLE="이미 저장된 값입니다.";
    public final static String SAVED_ALREADY_MESSAGE="히스토리로 이동하여 확인하시겠습니까?";
    public final static String NOT_LOGIN_TO_SAVE_TITLE="저장하려면 로그인해야 합니다.";
    public final static String NOT_LOGIN_TO_SAVE_MESSAGE="로그인 하러 이동하시겠습니까?";
    public final static String RANDOM_SAVE_NOT_SUPPORTED="랜덤 모드는 아직 저장을 지원하지 않습니다.";
    public final static String NO_INTERNET_RANDOM_MODE="서버와의 통신이 되지 않습니다. 랜덤 모드로 변경됩니다.";

    //registerFragment
    public final static String REGISTER_SUCCESS_TITLE="회원가입 성공";
    public final static String REGISTER_SUCCESS_MESSAGE="회원가입을 성공하였습니다.";

    @AllArgsConstructor
    @Getter
    public enum ERecyclerViewItemCode{
        LOADING_ITEM(0),
        LOAD_MORE_OPTION_ITEM(1),
        ITEM(2);
        private int viewType;
    }

    @AllArgsConstructor
    @Getter
    public enum EPredictMode{
        RANDOM("랜덤"),PREDICT("추천");
        private String text;

        public EPredictMode toggle(){
            if(this==RANDOM)return PREDICT;
            else return RANDOM;
        }
    }


    public static String setStringWrapped(String string){
        return "("+string+")";
    }


    @AllArgsConstructor
    @Getter
    public enum EValue{
        NULL(-1L);
    private Long id;
    }

    public static int getLottoColor(View view,int i, EScore flag) {
        int id;
        if (flag == EScore.FALSE) {
            id = R.color.light_gray;
        } else if (flag == EScore.HIT) {
            if (i < 11) {
                id = R.color.lotto_yellow;
            } else if (i < 21) {
                id = R.color.lotto_blue;
            } else if (i < 31) {
                id = R.color.lotto_red;
            } else if (i < 41) {
                id = R.color.lotto_gray;
            } else {
                id = R.color.lotto_green;
            }
        } else {
            if (i < 11) {
                id = R.color.lotto_bonus_yellow;
            } else if (i < 21) {
                id = R.color.lotto_bonus_blue;
            } else if (i < 31) {
                id = R.color.lotto_bonus_red;
            } else if (i < 41) {
                id = R.color.lotto_bonus_gray;
            } else {
                id = R.color.lotto_bonus_green;
            }
        }
        return view.getResources().getColor(id, view.getContext().getTheme());
    }


}
