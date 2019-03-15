package com.pud.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem implements Parcelable {

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    private String objectId;
    private String name;
    private String allergens;

    public MenuItem() {
    }

    protected MenuItem(Parcel in) {
        objectId = in.readString();
        name = in.readString();
        allergens = in.readString();
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

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(name);
        dest.writeString(allergens);
    }
}