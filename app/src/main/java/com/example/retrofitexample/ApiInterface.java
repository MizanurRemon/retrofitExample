package com.example.retrofitexample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("registration.php")
    Call<String> savePost(@Field("name") String name,
                                 @Field("mail") String mail,
                                 @Field("phone") String phone,
                                 @Field("password") String password);

}
