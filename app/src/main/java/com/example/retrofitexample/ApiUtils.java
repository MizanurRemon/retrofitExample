package com.example.retrofitexample;

import retrofit2.Retrofit;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://mitechbd.com/retrofitExample/";

    public static ApiInterface getAPIService() {

        return Api.getClient(BASE_URL).create(ApiInterface.class);
    }
    public static GetDataInterface getDataInterface(){
        return Api.getClient(BASE_URL).create(GetDataInterface.class);
    }

    public static LoginInterface loginInterface(){
        return Api.getClient(BASE_URL).create(LoginInterface.class);
    }
}
