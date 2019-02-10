package com.pud.pojo;

import com.backendless.BackendlessUser;

import java.util.Date;

public class Comment {

    private String objectId;
    private String text;
    private String rating;
    private BackendlessUser byUser;
    private Date created;

    public String getObjectId() {
        return objectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BackendlessUser getByUser() {
        return byUser;
    }

    public void setByUser(BackendlessUser byUser) {
        this.byUser = byUser;
    }

    public Date getCreated() {
        return created;
    }

}
