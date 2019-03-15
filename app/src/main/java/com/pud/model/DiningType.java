package com.pud.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DiningType implements Parcelable {

    public static final Creator<DiningType> CREATOR = new Creator<DiningType>() {
        @Override
        public DiningType createFromParcel(Parcel in) {
            return new DiningType(in);
        }

        @Override
        public DiningType[] newArray(int size) {
            return new DiningType[size];
        }
    };

    private String objectId;
    private String name;
    private List<DiningTiming> diningTimings;

    protected DiningType(Parcel in) {
        objectId = in.readString();
        name = in.readString();
    }

    public String getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DiningTiming> getDiningTimings() {
        return diningTimings;
    }

    public void setDiningTimings(List<DiningTiming> diningTimings) {
        this.diningTimings = diningTimings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(name);
    }
}