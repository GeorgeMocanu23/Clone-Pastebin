package com.example.clonapastebin.request;

public class AddContentRequest {
    private String content;

    public AddContentRequest() {}

    public AddContentRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}