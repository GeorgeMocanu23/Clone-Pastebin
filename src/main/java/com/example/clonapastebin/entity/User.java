package com.example.clonapastebin.entity;

import org.bson.types.ObjectId;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;
    private String username;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Content> contentList = new ArrayList<>();

    public User() {}

    public User(ObjectId id, String username, String password, List<Content> contentList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.contentList = contentList;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }
}