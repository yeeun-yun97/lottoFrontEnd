package com.github.yeeun_yun97.main.view.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.constant.Constant;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.EScore;

public class NumberBall extends LinearLayout {

    private View view;
    private TextView numberTextView;
    private ImageView imageView;


    public NumberBall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, new int[1], 0, 0);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.view = layoutInflater.inflate(R.layout.item_number_ball, this, false);
        this.addView(this.view);

        this.imageView= this.view.findViewById(R.id.numberBallItem_imageView);
        this.numberTextView = this.view.findViewById(R.id.numberBallItem_numberTextView);
    }

    public void setNumber(int i) {
        this.numberTextView.setText(String.valueOf(i));
        this.imageView.setColorFilter(Constant.getLottoColor(this.view,i, EScore.HIT), PorterDuff.Mode.DARKEN);
    }

    public void showAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_number_ball);
        this.view.startAnimation(animation);
    }

}
