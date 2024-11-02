package com.ecommerce.ecommerce;

public class AnimeSeason {
    private String season;
    private Integer year;

    // Getters and setters

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getYear() {
        return year != null ? year: 0;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
