package com.northampton.friendschatbox.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendRequestData implements Parcelable {

    public static final Parcelable.Creator<FriendRequestData> CREATOR = new Parcelable.Creator<FriendRequestData>() {
        @Override
        public FriendRequestData createFromParcel(Parcel source) {
            return new FriendRequestData(source);
        }

        @Override
        public FriendRequestData[] newArray(int size) {
            return new FriendRequestData[size];
        }
    };
    private String fullName;
    private String emailAddress;
    private Boolean isRequestedAccepted;

    public FriendRequestData() {
    }

    public FriendRequestData(String fullName, String emailAddress, Boolean isRequestedAccepted) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.isRequestedAccepted = isRequestedAccepted;
    }

    protected FriendRequestData(Parcel in) {
        this.fullName = in.readString();
        this.emailAddress = in.readString();
        this.isRequestedAccepted = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getRequestedAccepted() {
        return isRequestedAccepted;
    }

    public void setRequestedAccepted(Boolean requestedAccepted) {
        isRequestedAccepted = requestedAccepted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullName);
        dest.writeString(this.emailAddress);
        dest.writeValue(this.isRequestedAccepted);
    }
}
