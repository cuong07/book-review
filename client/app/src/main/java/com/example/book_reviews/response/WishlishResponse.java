package com.example.book_reviews.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class WishlishResponse {
    private Long id;
    @SerializedName("user_id")

    private Long userId;
    private String name;
    private Date createdAt;

    private Date updatedAt;

    @SerializedName("wishlist_items")
    private List<WishlistData> wishlistItems;

    public WishlishResponse(Long id, Long userId, String name, Date createdAt, Date updatedAt, List<WishlistData> wishlistItems) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.wishlistItems = wishlistItems;
    }

    public WishlishResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<WishlistData> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishlistItems(List<WishlistData> wishlistItems) {
        this.wishlistItems = wishlistItems;
    }

    public class WishlistData {
        private WishlistItem wishlistItem;
        private Book book;

        public WishlistData(WishlistItem wishlistItem, Book book) {
            this.wishlistItem = wishlistItem;
            this.book = book;
        }

        public WishlistData() {
        }

        public WishlistItem getWishlistItem() {
            return wishlistItem;
        }

        public void setWishlistItem(WishlistItem wishlistItem) {
            this.wishlistItem = wishlistItem;
        }

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }
    }
}
