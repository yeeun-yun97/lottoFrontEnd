package com.github.yeeun_yun97.main.view.domain.lotto.history;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.yeeun_yun97.R;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.ReadSaveContent;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.ReadSaveRequest;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.ReadSaveResponse;
import com.github.yeeun_yun97.main.retrofit.SimpleRetrofit;
import com.github.yeeun_yun97.main.view.domain.lotto.history.adapter.SaveAdapter;
import com.github.yeeun_yun97.main.viewmodel.SimpleViewModelObserverFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 저장된 값을 확인하는 프래그먼트.
 */
public class HistoryFragment extends SimpleViewModelObserverFragment {

    //associate view
    private RecyclerView recyclerView;
    private TextView noInternetTextView;
    private TextView noResultTextView;

    //working variable
    private int page_num;
    private SaveAdapter adapter;
    private boolean isLoadingMore;
    private boolean hasNext;
    private boolean isLoading = false;


    @Override
    protected void doOnChange() {
        if (!this.isTokenNull()) {
            this.refresh();
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.recyclerView = view.findViewById(R.id.historyFragment_recyclerView);
        this.noInternetTextView=view.findViewById(R.id.noInternetLayout_alertTextView);
        this.noResultTextView=view.findViewById(R.id.historyFragment_noResultTextView);

        this.noInternetTextView.setVisibility(View.INVISIBLE);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setNestedScrollingEnabled(false);
        this.adapter = new SaveAdapter(v -> this.loadMore());
        this.recyclerView.setAdapter(this.adapter);
        this.page_num = 0;

        this.noInternetTextView.setOnClickListener(v->this.refresh());
    }

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = this.getActivity().findViewById(R.id.mainActivity_bottomNavigationView);
        if (bottomNavigationView.getSelectedItemId() != R.id.bottomMenu_save)
            bottomNavigationView.findViewById(R.id.bottomMenu_save).performClick();
    }

    /*
    server rest api
     */
    private class ReadSaveCallback extends SimpleCallback<ReadSaveResponse> {
        @Override
        protected void onBothResponse() {
            isLoading=false;
        }

        @Override
        protected void onSuccessfulResponse(Response<ReadSaveResponse> responseEntity) {
            ReadSaveResponse response = responseEntity.body();
            hasNext = !response.isLast();
            setResponseData(response);
        }

        @Override
        public void onFailure(Call<ReadSaveResponse> call, Throwable t) {
            super.onFailure(call, t);
            isLoading=false;
            noInternetTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    private void doReadSave() {
        this.showLoadingView();
        this.noInternetTextView.setVisibility(View.INVISIBLE);
        ReadSaveRequest request = ReadSaveRequest.builder().user_id(this.auth.getUser_id()).page_num(this.page_num).build();
        SimpleRetrofit.getUserService(this.auth.getToken()).readSaveHistory(request).enqueue(new ReadSaveCallback());
    }


    /*
    method
     */
    private void refresh() {
        if (this.isLoading) {
            return;
        }
        this.showNoResponseView(false);
        this.isLoading = true;
        this.isLoadingMore = false;
        this.page_num = 0;
        this.recyclerView.scrollTo(0, 0);
        this.doReadSave();
    }

    private void showNoResponseView(boolean flag) {
        this.noResultTextView.setVisibility(flag?View.VISIBLE:View.INVISIBLE);
    }

    private void loadMore() {
        if (this.isLoading) {
            return;
        }
        if (this.hasNext == true) {
            this.isLoading = true;
            this.isLoadingMore = true;
            this.page_num++;
            this.doReadSave();
        } else {
            Toast.makeText(getContext(), "마지막입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setResponseData(ReadSaveResponse response) {
        Vector<ReadSaveContent> saveVector;
        if (this.isLoadingMore) {
            saveVector = this.adapter.getItems();
            saveVector.remove(null);
        } else {
            saveVector = new Vector<>();
        }
        for(ReadSaveContent content: response.getContent()){
            if(!content.getContent().isEmpty())
                saveVector.add(content);
        }
        this.adapter.setItems(saveVector);
        if(adapter.getItemCount()==0) this.showNoResponseView(true);
        if (hasNext) this.adapter.addMoreLoadView();
        this.isLoading = false;
    }


}
