package com.ecommerce.ecommerce;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String name;
    private String pictureUrl;
    private List<String> favoriteAnimeIds;
    // フォロー機能用
    private Set<String> followers = new HashSet<>(); // 自分をフォローしているユーザーのID
    private Set<String> following = new HashSet<>(); // 自分がフォローしているユーザーのID
    
    private boolean isFollowing;
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPictureUrl() { return pictureUrl; }
    public void setPictureUrl(String pictureUrl) { this.pictureUrl = pictureUrl; }

    public List<String> getFavoriteAnimeIds() {
        return favoriteAnimeIds;
    }

    public void setFavoriteAnimeIds(List<String> favoriteAnimeIds) {
        this.favoriteAnimeIds = favoriteAnimeIds;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }

    public Set<String> getFollowing() {
        return following;
    }

    public void setFollowing(Set<String> following) {
        this.following = following;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

}
