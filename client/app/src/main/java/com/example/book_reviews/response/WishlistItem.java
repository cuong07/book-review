package com.example.book_reviews.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WishlistItem {
    private Long id;
    @SerializedName("book_id")
    private Long bookId;
    @SerializedName("wishlist_id")

    private Long wishlistId;
    private Date createdAt;
    private Date updatedAt;

    public WishlistItem() {
    }

    public WishlistItem(Long id, Long bookId, Long wishlistId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.bookId = bookId;
        this.wishlistId = wishlistId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
