package com.pud.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DiningTiming implements Parcelable {

    public static final Creator<DiningTiming> CREATOR = new Creator<DiningTiming>() {
        @Override
        public DiningTiming createFromParcel(Parcel in) {
            return new DiningTiming(in);
        }

        @Override
        public DiningTiming[] newArray(int size) {
            return new DiningTiming[size];
        }
    };

    private String objectId;
    private DiningType diningType;
    private Place ofPlace;
    private List<MenuSection> menuSections;
    private List<Rating> ratings;
    private List<Comment> comments;

    public DiningTiming(Parcel in) {
        objectId = in.readString();
        ofPlace = in.readParcelable(Place.class.getClassLoader());
        diningType = in.readParcelable(DiningType.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeParcelable(ofPlace, flags);
        dest.writeParcelable(diningType, flags);
    }
}