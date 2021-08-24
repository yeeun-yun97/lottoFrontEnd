package com.github.yeeun_yun97.main.view.domain.lotto.history.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.OneSaveResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class SaveListAdapter extends RecyclerView.Adapter<SaveListViewHolder> {

    private Vector<OneSaveResponse> saveVector;

    public SaveListAdapter(){
        this.saveVector=new Vector<>();
    }

    @NonNull
    @NotNull
    @Override
    public SaveListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_numbers,parent,false);
       return new SaveListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SaveListViewHolder holder, int position) {
        if(saveVector.get(position)==null){
            Log.d("엥 널인데 여기가 왜나옴","??");
            return;
        }
        holder.setValue(saveVector.get(position));
    }

    @Override
    public int getItemCount() {
        return this.saveVector.size();
    }

    public void setItems(Vector<OneSaveResponse> saveVector) {
        Log.d("값 바낌","값 바낌");
        this.saveVector=saveVector;
        this.notifyDataSetChanged();
    }
}
