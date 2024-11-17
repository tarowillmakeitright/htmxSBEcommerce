package com.ecommerce.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository; // Injected repository instance

    public List<Comment> getCommentsByAnimeId(String animeId) {
        return commentRepository.findByAnimeId(animeId); // Instance method call
    }
    // Fetch limited comments for an anime
    public List<Comment> getLimitedCommentsByAnimeId(String animeId, int limit) {
        return commentRepository.findByAnimeId(animeId)
                .stream()
                .limit(limit) // Limit to the specified number of comments
                .toList();
    }

    public void addComment(String animeId, String userId, String userName, String content) {
        Comment comment = new Comment(animeId, userId, userName, content);
        commentRepository.save(comment); // Call save on the injected instance
    }
}