package com.ecommerce.ecommerce;

import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByAnimeId(String animeId); // Fetch comments for a specific anime
}