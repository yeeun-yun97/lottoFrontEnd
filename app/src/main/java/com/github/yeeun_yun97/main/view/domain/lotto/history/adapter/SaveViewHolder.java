package com.github.yeeun_yun97.main.view.domain.lotto.history.adapter;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.ReadSaveContent;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class SaveViewHolder extends RecyclerView.ViewHolder {

    private final SaveListAdapter adapter;
    //associate view
    private TextView expandTextView, roundTextView;
    private RecyclerView recyclerView;


    //working variable
    protected boolean isExpanded;

    public SaveViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        this.roundTextView=itemView.findViewById(R.id.savedNumbersTitle_roundTextView);
        this.expandTextView=itemView.findViewById(R.id.savedNumbersTitle_expandTextView);

        this.recyclerView= itemView.findViewById(R.id.expandableTable_recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(itemView.getContext());
        this.recyclerView.setLayoutManager(manager);
        this.adapter=new SaveListAdapter();
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setNestedScrollingEnabled(false);

        this.changeVisibility(true);
        this.expandTextView.setOnClickListener(v->changeVisibility(!isExpanded));
    }

    protected void changeVisibility(final boolean isExpanded) {
        this.isExpanded = isExpanded;
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, recyclerView.getHeight()) : ValueAnimator.ofInt(recyclerView.getHeight(), 0);
        va.setDuration(500);
        va.addUpdateListener(animation -> {
            recyclerView.getLayoutParams().height = (int) animation.getAnimatedValue();
            recyclerView.requestLayout();
            recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            expandTextView.setText(isExpanded ? "접기>" : "펼치기>");
        });
        va.start();
    }

    public void setValue(ReadSaveContent saveRoundList) {
        this.roundTextView.setText(saveRoundList.getTitleText());
        this.adapter.setItems(new Vector<>(saveRoundList.getContent()));
    }
}
