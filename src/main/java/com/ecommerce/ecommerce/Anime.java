package com.ecommerce.ecommerce;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "anime")
public class Anime {

    @Id
    private String id;

    private String title;
    private String type;
    private Integer episodes;
    private String status;
    private AnimeSeason animeSeason;
    private String picture;
    private String thumbnail;
    private List<String> synonyms;
    private List<String> relatedAnime;
    private List<String> tags;
    private List<String> sources;
    private int goodVotes = 0; // 👍 の数
    private int badVotes = 0;  // 👎 の数
    private Score score; // ← 追加

    public Score getScore() {
        return score;
    }
    
    public void setScore(Score score) {
        this.score = score;
    }
    // Getters and Setters
    public int getGoodVotes() { return goodVotes; }
    public void setGoodVotes(int goodVotes) { this.goodVotes = goodVotes; }

    public int getBadVotes() { return badVotes; }
    public void setBadVotes(int badVotes) { this.badVotes = badVotes; }
    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AnimeSeason getAnimeSeason() {
        return animeSeason;
    }

    public void setAnimeSeason(AnimeSeason animeSeason) {
        this.animeSeason = animeSeason;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getRelatedAnime() {
        return relatedAnime;
    }

    public void setRelatedAnime(List<String> relatedAnime) {
        this.relatedAnime = relatedAnime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

}
