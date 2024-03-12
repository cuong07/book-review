package com.example.book_reviews.api.user;


import com.example.book_reviews.dtos.LoginDTO;
import com.example.book_reviews.dtos.RegisterDTO;
import com.example.book_reviews.response.LoginResponse;
import com.example.book_reviews.utils.ApiClient;

import retrofit2.Call;

public class UserApi implements IUserApi {
    @Override
    public Call<LoginResponse> login(LoginDTO loginDTO) {
        return ApiClient.retrofit.create(IUserApi.class).login(loginDTO);
    }

    @Override
    public Call<LoginResponse> register(RegisterDTO registerDTO) {
        return ApiClient.retrofit.create(IUserApi.class).register(registerDTO);
    }

    @Override
    public Call<LoginResponse> getCurenntUser(String token) {
        return ApiClient.retrofit.create(IUserApi.class).getCurenntUser(token);
    }
}
