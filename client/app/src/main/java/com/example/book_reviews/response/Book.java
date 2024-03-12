package com.example.book_reviews.response;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

// "id": 11,
//         "name": "To Kill A Mockingbird\n",
//         "image": "http://res.cloudinary.com/dh4wwxi3i/image/upload/v1709535587/books/qjmnawf771d1h5nfpnap.png",
//         "author": "To Kill A Mockingbird\n",
//         "user_id": 1,
//         "updatedAt": "2024-03-04T06:59:50.032Z",
//         "createdAt": "2024-03-04T06:59:50.032Z"
public class Book {
    private Long id;
    private String name;
    private String image;
    private String author;
    @SerializedName("user_id")
    private String userId;
    private Date updatedAt;
    private Date createdAt;

    public Book() {
    }

    public String getFormattedPublicationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return sdf.format(createdAt);
    }

    public Book(Long id, String name, String image, String author, String userId, Date updatedAt, Date createdAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.author = author;
        this.userId = userId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
