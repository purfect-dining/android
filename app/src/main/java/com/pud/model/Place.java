package com.pud.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Place implements Parcelable {

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    private String objectId;
    private String name;
    private String phone;
    private String address;
    private List<DiningTiming> diningTimings;

    public Place(Parcel in) {
        objectId = in.readString();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        diningTimings = in.createTypedArrayList(DiningTiming.CREATOR);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeTypedList(diningTimings);
    }
}