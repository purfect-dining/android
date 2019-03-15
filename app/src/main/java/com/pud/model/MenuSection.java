package com.pud.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MenuSection implements Parcelable {

    public static final Creator<MenuSection> CREATOR = new Creator<MenuSection>() {
        @Override
        public MenuSection createFromParcel(Parcel in) {
            return new MenuSection(in);
        }

        @Override
        public MenuSection[] newArray(int size) {
            return new MenuSection[size];
        }
    };

    private String objectId;
    private String name;
    private List<MenuItem> menuItems;

    public MenuSection() {
    }

    protected MenuSection(Parcel in) {
        objectId = in.readString();
        name = in.readString();
        menuItems = in.createTypedArrayList(MenuItem.CREATOR);
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

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(name);
        dest.writeTypedList(menuItems);
    }
}