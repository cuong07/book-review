package com.example.book_reviews.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    private Book book;
    private Data data;

    public SearchResponse() {
    }

    public SearchResponse(Book book, Data data) {
        this.book = book;
        this.data = data;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String status;
        private String copyright;
        @SerializedName("num_results")
        private Long numResults;
        private List<Review> results;

        public Data() {
        }

        public Data(String status, String copyright, Long numResults, List<Review> results) {
            this.status = status;
            this.copyright = copyright;
            this.numResults = numResults;
            this.results = results;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public Long getNumResults() {
            return numResults;
        }

        public void setNumResults(Long numResults) {
            this.numResults = numResults;
        }

        public List<Review> getResults() {
            return results;
        }

        public void setResults(List<Review> results) {
            this.results = results;
        }
    }
}
