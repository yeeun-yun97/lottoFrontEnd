package com.github.yeeun_yun97.main.retrofit;

import com.github.yeeun_yun97.main.model.dto.domain.lotto.pick.CreateNumberCombinationRequest;
import com.github.yeeun_yun97.main.model.dto.domain.user.login.LoginRequest;
import com.github.yeeun_yun97.main.model.dto.domain.user.login.LoginResponse;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.pick.LottoPredictResponse;
import com.github.yeeun_yun97.main.model.dto.domain.home.ReadLottoRecentRoundResponse;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.ReadSaveRequest;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.history.ReadSaveResponse;
import com.github.yeeun_yun97.main.model.dto.domain.home.ReadUserInfoRequest;
import com.github.yeeun_yun97.main.model.dto.domain.home.ReadUserInfoResponse;
import com.github.yeeun_yun97.main.model.dto.domain.user.register.RegisterRequest;
import com.github.yeeun_yun97.main.model.dto.domain.user.register.RegisterResponse;
import com.github.yeeun_yun97.main.model.dto.SimpleOkResponse;
import com.github.yeeun_yun97.main.model.dto.domain.lotto.pick.UpdateSaveRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LottoService {

    @POST("/user/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("/user/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("/lotto/predict")
    Call<LottoPredictResponse> predict(@Query("user_id") long user_id);

    @POST("/user/saveNumber")
    Call<SimpleOkResponse> createNumberCombination(@Body CreateNumberCombinationRequest request);

    @POST("/user/saveHistory")
    Call<SimpleOkResponse> updateSaveToHistory(@Body UpdateSaveRequest request);

    @POST("/user/readUserInfo")
    Call<ReadUserInfoResponse> readUserInfo(@Body ReadUserInfoRequest request);

    @POST("/user/readHistory")
    Call<ReadSaveResponse> readSaveHistory(@Body ReadSaveRequest request);

    @GET("/lotto/readHitData")
    Call<ReadLottoRecentRoundResponse> readLottoRecentRound();
}
