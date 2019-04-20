package com.pud.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.backendless.BackendlessUser;

import java.util.Date;
import java.util.List;

public class Comment implements Parcelable {

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    private String objectId;
    private String text;
    private int rating;
    private List<BackendlessUser> likes;
    private BackendlessUser byUser;
    private Date created;

    public Comment() {
    }

    protected Comment(Parcel in) {
        objectId = in.readString();
        text = in.readString();
        rating = in.readInt();
        created = new Date(in.readLong());
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<BackendlessUser> getLikes() {
        return likes;
    }

    public void setLikes(List<BackendlessUser> likes) {
        this.likes = likes;
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

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(text);
        dest.writeInt(rating);
        dest.writeLong(created.getTime());
    }
}
