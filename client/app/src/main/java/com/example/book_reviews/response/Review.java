package com.example.book_reviews.response;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//                    "url": "http://movies.nytimes.com/2011/05/13/movies/hey-boo-harper-lee-and-to-kill-a-mockingbird.html",
//                            "publication_dt": "2011-05-13",
//                            "byline": "NEIL GENZLINGER",
//                            "book_title": "To Kill a Mockingbird",
//                            "book_author": "Harper Lee",
//                            "summary": "Mary McDonagh Murphy’s documentary tells how “To Kill a Mockingbird” was written and how it influenced America.",
//                            "uuid": "00000000-0000-0000-0000-000000000000",
//                            "uri": "nyt://book/00000000-0000-0000-0000-000000000000",
//                            "isbn13": [
//                            "9781439550410"
//                            ]
public class Review {
    private String url;

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getFormattedPublicationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return sdf.format(publicationDate);
    }

    @SerializedName("publication_dt")
    private Date publicationDate;
    private String byline;
    @SerializedName("book_title")
    private String bookTitle;

    @SerializedName("book_author")
    private String bookAuthor;
    private String summary;
    private String uuid;
    private String uri;
    private List<String> isbn13;

    public Review() {
    }

    public Review(String url, Date publicationDate, String byline, String bookTitle, String bookAuthor, String summary, String uuid, String uri, List<String> isbn13) {
        this.url = url;
        this.publicationDate = publicationDate;
        this.byline = byline;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.summary = summary;
        this.uuid = uuid;
        this.uri = uri;
        this.isbn13 = isbn13;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(List<String> isbn13) {
        this.isbn13 = isbn13;
    }
}
