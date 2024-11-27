package com.ecommerce.ecommerce;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String name;
    private String pictureUrl;

    private List<String> favoriteAnimeIds;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPictureUrl() { return pictureUrl; }
    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

    // Getters and Setters
    public List<String> getFavoriteAnimeIds() {
        return favoriteAnimeIds;
    }

    public void setFavoriteAnimeIds(List<String> favoriteAnimeIds) {
        this.favoriteAnimeIds = favoriteAnimeIds;
    }
}