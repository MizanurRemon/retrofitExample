package com.example.retrofitexample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginInterface {
    @GET("login.php")
    Call<FetchData> savePost(@Query("email") String mail,
                          @Query("password") String pass);
}
