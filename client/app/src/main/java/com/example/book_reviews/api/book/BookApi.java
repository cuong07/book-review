package com.example.book_reviews.api.book;

import com.example.book_reviews.api.user.IUserApi;
import com.example.book_reviews.response.BookResponse;
import com.example.book_reviews.response.LoginResponse;
import com.example.book_reviews.response.SearchResponse;
import com.example.book_reviews.utils.ApiClient;

import okhttp3.MultipartBody;
import retrofit2.Call;

public class BookApi implements IBookApi {
    @Override
    public Call<SearchResponse> getBookByImage(String type, Long userId, MultipartBody.Part file) {
        return ApiClient.retrofit.create(IBookApi.class).getBookByImage(type, userId, file);
    }

    @Override
    public Call<SearchResponse> getBookByTitle(String type, Long userId, String name) {
        return ApiClient.retrofit.create(IBookApi.class).getBookByTitle(type, userId, name);
    }

    @Override
    public Call<BookResponse> getAllBookSearch(Long userId) {
        return ApiClient.retrofit.create(IBookApi.class).getAllBookSearch(userId);
    }


}
