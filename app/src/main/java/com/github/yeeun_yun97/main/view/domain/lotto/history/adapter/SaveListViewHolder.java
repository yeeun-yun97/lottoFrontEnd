package com.github.yeeun_yun97.main.view.domain.lotto.history.adapter;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.constant.Constant;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.EScore;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.OneSaveResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SaveListViewHolder extends RecyclerView.ViewHolder {

    //associate view
    private TextView resultTextView;
    private List<TextView> balls;


    public SaveListViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        this.balls= new ArrayList<>();
        this.balls.add(itemView.findViewById(R.id.savedNumbersItem_firstBall));
        this.balls.add(itemView.findViewById(R.id.savedNumbersItem_secondBall));
        this.balls.add(itemView.findViewById(R.id.savedNumbersItem_thirdBall));
        this.balls.add(itemView.findViewById(R.id.savedNumbersItem_fourthBall));
        this.balls.add(itemView.findViewById(R.id.savedNumbersItem_fifthBall));
        this.balls.add(itemView.findViewById(R.id.savedNumbersItem_sixthBall));

        this.resultTextView = itemView.findViewById(R.id.savedNumbersItem_resultTextView);
    }


    public void setValue(OneSaveResponse save) {
        int sumScore = 0;
        int index = 0;
        for (TextView textView : this.balls) {
            int number = save.getNumberList().get(index);
            EScore score = save.getScoreList().get(index++);
            sumScore += score.getScore();
            this.setSmallBall(textView, number, score);
        }
        this.resultTextView.setText(this.getSumResult(sumScore));
    }

    private String getSumResult(int sumScore) {
        if (sumScore < 30) {
            return "낙첨";
        } else if (sumScore < 40) {
            return "5등";
        } else if (sumScore < 50) {
            return "4등";
        } else if (sumScore < 55) {
            return "3등";
        } else if (sumScore < 60) {
            return "2등";
        } else {
            return "1등";
        }
    }


    private void setSmallBall(TextView textView, int num, EScore flag) {
        textView.setText(String.valueOf(num));
        textView.setBackgroundTintList(ColorStateList.valueOf(Constant.getLottoColor(itemView, num, flag)));
    }

    @AllArgsConstructor
    @Getter
    public enum ELottoRank {
        FIRST_PRIZE("1등"), SECOND_PRIZE("2등"),
        THIRD_PRIZE("3등"), FOURTH_PRIZE("4등"),
        FIFTH_PRIZE("5등");
        private String text;
    }


}
