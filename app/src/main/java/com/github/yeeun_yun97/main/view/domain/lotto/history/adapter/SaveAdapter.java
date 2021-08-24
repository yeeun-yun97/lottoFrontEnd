package com.github.yeeun_yun97.main.view.domain.lotto.history.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.ReadSaveContent;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class SaveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private Vector<ReadSaveContent> saveVector;

    private View.OnClickListener listener;

    public SaveAdapter(View.OnClickListener listener) {
        this.listener = listener;
        this.saveVector = new Vector<>();
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_table, parent, false);
            return new SaveViewHolder(view);
        } else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadMoreViewHolder(view) {};
        }
    }

    @Override
    public int getItemViewType(int position) {
        return this.saveVector.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
      if (holder instanceof SaveViewHolder)
            ((SaveViewHolder) holder).setValue(saveVector.get(position));
        else if (holder instanceof LoadMoreViewHolder)
            ((LoadMoreViewHolder) holder).setListener(this.listener);
    }


    @Override
    public int getItemCount() {
        return this.saveVector.size();
    }

    public void setItems(Vector<ReadSaveContent> saveVector) {
        this.saveVector = saveVector;
        this.notifyDataSetChanged();
    }

    public Vector<ReadSaveContent> getItems() {
        return this.saveVector;
    }

    public void addMoreLoadView() {
        this.saveVector.add(null);
        this.notifyItemInserted(getItemCount() - 1);
    }
}
