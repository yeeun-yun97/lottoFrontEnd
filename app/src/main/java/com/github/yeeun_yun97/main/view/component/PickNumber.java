package com.github.yeeun_yun97.main.view.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;

import com.github.yeeun_yun97.R;
import com.google.android.material.chip.Chip;

import java.util.Arrays;
import java.util.Vector;

public class PickNumber extends LinearLayout {

    private View view;
    private Vector<NumberPicker> pickers;
    private Vector<Chip> chips;


    public PickNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.pickers = new Vector<>();
        this.chips = new Vector<>();

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, new int[1], 0, 0);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.view = layoutInflater.inflate(R.layout.item_number_picker, this, false);
        this.addView(this.view);

        this.pickers.add(view.findViewById(R.id.pickNumberItem_numberPicker1));
        this.pickers.add(view.findViewById(R.id.pickNumberItem_numberPicker2));
        this.pickers.add(view.findViewById(R.id.pickNumberItem_numberPicker3));
        this.pickers.add(view.findViewById(R.id.pickNumberItem_numberPicker4));
        this.pickers.add(view.findViewById(R.id.pickNumberItem_numberPicker5));
        this.pickers.add(view.findViewById(R.id.pickNumberItem_numberPicker6));

        this.chips.add(view.findViewById(R.id.pickNumberItem_numberFixer1));
        this.chips.add(view.findViewById(R.id.pickNumberItem_numberFixer2));
        this.chips.add(view.findViewById(R.id.pickNumberItem_numberFixer3));
        this.chips.add(view.findViewById(R.id.pickNumberItem_numberFixer4));
        this.chips.add(view.findViewById(R.id.pickNumberItem_numberFixer5));
        this.chips.add(view.findViewById(R.id.pickNumberItem_numberFixer6));

        this.init();
    }

    private void init() {
        for (int i = 0; i < 6; i++) {
            NumberPicker picker = this.pickers.get(i);
            picker.setMinValue(1);
            picker.setMaxValue(45);
            picker.setValue(i + 1);

            Chip fixer = this.chips.get(i);
            fixer.setCheckable(true);
            fixer.setOnCheckedChangeListener((buttonView, isChecked) -> lockPicker(isChecked,this.chips.indexOf(fixer)));
        }


        int def = 1;
        for (NumberPicker picker : this.pickers) {
            picker.setMinValue(1);
            picker.setMaxValue(45);
            picker.setValue(def++);
        }
    }

    private void lockPicker(boolean isChecked, int index) {
        NumberPicker target = pickers.get(index);
        if (isChecked) {
            target.setEnabled(false);
            target.setBackgroundColor(getResources().getColor(R.color.light_gray, getContext().getTheme()));
        }else{
            target.setEnabled(true);
            target.setBackgroundColor(getResources().getColor(R.color.white, getContext().getTheme()));
        }
    }

    public int[] getNumbers() {
        int[] arr = new int[6];
        for (int i = 0; i < 6; i++) {
            arr[i] = this.pickers.get(i).getValue();
        }
        Arrays.sort(arr);
        return arr;
    }

    public int[] getFixedNumbers(){
        int[] arr = new int[6];
        Arrays.fill(arr,-1);

        int target=0;
        for(int i=0; i<6;i++){
            NumberPicker picker= pickers.get(i);
            Chip chip = chips.get(i);
            if(chip.isChecked()){
                arr[target++]=picker.getValue();
            }
        }
        return arr;
    }

    public void setValues(int[] number) {
        for(int i=0; i<6; i++){
            this.lockPicker(false,i);
            pickers.get(i).setValue(number[i]);
        }
    }
}
