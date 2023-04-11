package com.example.clonapastebin.entity;

import org.bson.types.ObjectId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Content {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;
    private String content;

    public Content() {}

    public Content(ObjectId id, String content) {
        this.id = id;
        this.content = content;

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}