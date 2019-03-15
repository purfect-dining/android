package com.pud.model;

import java.util.Date;
import java.util.List;

public class DiningTiming {

    private String objectId;
    private DiningType diningType;
    private Place ofPlace;
    private List<MenuSection> menuSections;
    private List<Rating> ratings;
    private List<Comment> comments;

    public String getObjectId() {
        return objectId;
    }

    public DiningType getDiningType() {
        return diningType;
    }

    public void setDiningType(DiningType diningType) {
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