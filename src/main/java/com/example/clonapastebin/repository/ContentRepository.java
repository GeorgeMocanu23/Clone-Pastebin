package com.example.clonapastebin.repository;

import com.example.clonapastebin.entity.Content;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends MongoRepository<Content, ObjectId> {
}