package com.northampton.friendschatbox.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendData implements Parcelable {
    public static final Parcelable.Creator<FriendData> CREATOR = new Parcelable.Creator<FriendData>() {
        @Override
        public FriendData createFromParcel(Parcel source) {
            return new FriendData(source);
        }

        @Override
        public FriendData[] newArray(int size) {
            return new FriendData[size];
        }
    };
    public int userId;
    public String fullName;

    public FriendData() {
    }

    public FriendData(int userId, String fullName) {
        this.userId = userId;
        this.fullName = fullName;
    }

    protected FriendData(Parcel in) {
        this.userId = in.readInt();
        this.fullName = in.readString();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.fullName);
    }
}
