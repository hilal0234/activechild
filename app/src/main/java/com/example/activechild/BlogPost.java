package com.example.activechild;

public class BlogPost {
    private String title;
    private String content;
    private String imageUrl;

    public BlogPost() {} // Firestore için boş constructor gerekli

    public BlogPost(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
}
