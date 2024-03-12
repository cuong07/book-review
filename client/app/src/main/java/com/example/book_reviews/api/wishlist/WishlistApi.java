package com.example.book_reviews.api.wishlist;

import com.example.book_reviews.api.user.IUserApi;
import com.example.book_reviews.response.BaseRespose;
import com.example.book_reviews.response.WishlishResponse;
import com.example.book_reviews.response.WishlistItem;
import com.example.book_reviews.utils.ApiClient;

import retrofit2.Call;

public class WishlistApi implements IWishlistApi {
    @Override
    public Call<BaseRespose<WishlistItem>> addToWishlist(Long bookId, Long userId) {
        return ApiClient.retrofit.create(IWishlistApi.class).addToWishlist(bookId, userId);
    }

    @Override
    public Call<BaseRespose<Object>> deleteWishlistItem(Long bookId) {
        return ApiClient.retrofit.create(IWishlistApi.class).deleteWishlistItem(bookId);
    }

    @Override
    public Call<BaseRespose<WishlishResponse>> getWishlistByUser(Long userId) {
        return ApiClient.retrofit.create(IWishlistApi.class).getWishlistByUser(userId);
    }
}
