package com.test;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiClient {

    String BASE_URL = "https://api.businessclass.com/uk/user/login/";

    @POST(".")
    Call<ResponseBody> login(@Body LoginModel body);

}