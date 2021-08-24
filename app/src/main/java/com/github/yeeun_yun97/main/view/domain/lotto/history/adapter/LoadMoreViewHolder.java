package com.github.yeeun_yun97.main.view.domain.lotto.history.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yeeun_yun97.R;

import org.jetbrains.annotations.NotNull;

public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public LoadMoreViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.loadingItem_textView);
        itemView.setOnClickListener(v -> this.loading(true));
        this.loading(false);
    }

    public void loading(boolean isLoad) {
        this.textView.setVisibility(isLoad ? View.INVISIBLE : View.VISIBLE);
    }

    public void setListener(View.OnClickListener listener) {
        this.itemView.setOnClickListener(listener);
    }
}
