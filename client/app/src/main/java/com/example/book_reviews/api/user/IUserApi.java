package com.example.book_reviews.api.user;

import com.example.book_reviews.dtos.LoginDTO;
import com.example.book_reviews.dtos.RegisterDTO;
import com.example.book_reviews.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IUserApi {
    @POST("/api/v1/login")
    Call<LoginResponse> login(@Body LoginDTO loginDTO);

    @POST("/api/v1/register")
    Call<LoginResponse> register(@Body RegisterDTO registerDTO);
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/api/v1/current-user")
    Call<LoginResponse> getCurenntUser(@Header("authorization") String token);
}