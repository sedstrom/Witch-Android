package com.example.witch.responsemodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Github {

    @GET("users/{username}")
    Call<ResponseViewModel> getUser(@Path("username") String username);

}
