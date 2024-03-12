package com.example.book_reviews.api.wishlist;

import com.example.book_reviews.response.BaseRespose;
import com.example.book_reviews.response.LoginResponse;
import com.example.book_reviews.response.WishlishResponse;
import com.example.book_reviews.response.WishlistItem;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IWishlistApi {
    @POST("/api/v1/wishlist")
    Call<BaseRespose<WishlistItem>> addToWishlist(@Query("book_id") Long bookId,
                                                  @Query("user_id") Long userId);

    @DELETE("/api/v1/wishlist")
    Call<BaseRespose<Object>> deleteWishlistItem(@Query("book_id") Long bookId);

    @GET("/api/v1/wishlist")
    Call<BaseRespose<WishlishResponse>> getWishlistByUser(@Query("user_id") Long userId);
}
