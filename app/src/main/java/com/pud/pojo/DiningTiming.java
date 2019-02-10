package com.pud.pojo;

import java.util.List;

public class DiningTiming {

    private String objectId;
    private String diningType;
    private Place ofPlace;
    private List<MenuSection> menuSections;
    private List<Rating> ratings;
    private List<Comment> comments;

    public String getObjectId() {
        return objectId;
    }

    public String getDiningType() {
        return diningType;
    }

    public void setDiningType(String diningType) {
        this.diningType = diningType;
    }

    public Place getOfPlace() {
        return ofPlace;
    }

    public void setOfPlace(Place ofPlace) {
        this.ofPlace = ofPlace;
    }

    public List<MenuSection> getMenuSections() {
        return menuSections;
    }

    public void setMenuSections(List<MenuSection> menuSections) {
        this.menuSections = menuSections;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}