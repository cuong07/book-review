package com.example.book_reviews.api.book;

import com.example.book_reviews.dtos.LoginDTO;
import com.example.book_reviews.dtos.RegisterDTO;
import com.example.book_reviews.response.BookResponse;
import com.example.book_reviews.response.LoginResponse;
import com.example.book_reviews.response.SearchResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IBookApi {

    @Multipart
    @POST("/api/v1/book/images-to-text")
    Call<SearchResponse> getBookByImage(@Query("type") String type,
                                        @Query("user_id") Long userId,
                                        @Part MultipartBody.Part file
    );

    @GET("/api/v1/book")
    Call<SearchResponse> getBookByTitle(@Query("type") String type,
                                        @Query("user_id") Long userId,
                                        @Query("name") String name
    );

    @GET("/api/v1/book/user")
    Call<BookResponse> getAllBookSearch(
            @Query("user_id") Long userId
    );
}
