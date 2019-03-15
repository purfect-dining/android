package com.pud.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.backendless.BackendlessUser;

import java.util.Date;

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
    private String rating;
    private BackendlessUser byUser;
    private Date created;

    protected Comment(Parcel in) {
        objectId = in.readString();
        text = in.readString();
        rating = in.readString();
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
        dest.writeString(rating);
        dest.writeLong(created.getTime());
    }
}
