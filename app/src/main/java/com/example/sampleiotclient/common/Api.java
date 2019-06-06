package com.example.sampleiotclient.common;


import com.example.sampleiotclient.pojo.device.Device;
import com.example.sampleiotclient.pojo.index.PrivacyChoiceResponse;
import com.example.sampleiotclient.pojo.index.PrivacyPolicyReportResponse;
import com.example.sampleiotclient.pojo.privacy.PrivacyChoice;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public class Api {

    private static ApiInterface api;
    private static Retrofit retrofit = null;

    public static ApiInterface getApi(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .cache(null)
                        .build())
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder()
                                .create()))
                .build();
        api = retrofit.create(ApiInterface.class);
        return api;
    }

    public interface ApiInterface {

        // 取得與該gateway所綁定的裝置列表
        @GET("/device")
        Call<List<Device>> readDevices();

        // 新增並綁定裝置與gateway
        @POST("/device/{udn}")
        Call<Device> bindDeviceAndGateway(@Path("udn") String udn);

        // 取得該裝置的隱私政策與相關資料
        @GET("/privacy/{udn}/{account}")
        Call<PrivacyPolicyReportResponse> readPrivacyPolicyReportByDevice(@Path("udn") String udn, @Path("account") String account);

        // 表達隱私偏好
        @POST("/choice")
        Call<PrivacyChoiceResponse> setPrivacyChoice(@Body PrivacyChoice privacyChoice);

        // 取得隱私偏好記錄
        @GET("/choice/{account}")
        Call<List<PrivacyChoiceResponse>> readPrivacyChoiceRecordsByUser(@Path("account") String account);

    }
}
