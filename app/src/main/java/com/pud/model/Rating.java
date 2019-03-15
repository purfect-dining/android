package com.pud.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rating implements Parcelable {

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    private String objectId;
    private int rating;
    private DiningTiming ofDiningTiming;

    protected Rating(Parcel in) {
        objectId = in.readString();
        rating = in.readInt();
    }

    public String getObjectId() {
        return objectId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public DiningTiming getOfDiningTiming() {
        return ofDiningTiming;
    }

    public void setOfDiningTiming(DiningTiming ofDiningTiming) {
        this.ofDiningTiming = ofDiningTiming;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeInt(rating);
    }
}