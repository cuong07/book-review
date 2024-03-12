package com.example.book_reviews.response;

import java.util.List;

public class BookResponse {
    private List<Book> data;
    private String message;

    public BookResponse(List<Book> data, String message) {
        this.data = data;
        this.message = message;
    }

    public List<Book> getData() {
        return data;
    }

    public void setData(List<Book> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookResponse() {
    }
}
