package com.northampton.friendschatbox.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetails implements Parcelable {

    public static final Parcelable.Creator<UserDetails> CREATOR = new Parcelable.Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };
    public int userId;
    public String fullName;
    public String emailAddress;
    public String dateRegistered;
    public String dateUpdated;
    public String password;
    public String hobbies;
    public String sex;
    public String friendsTable;

    public UserDetails() {
    }

    public UserDetails(
            int userId,
            String fullName,
            String emailAddress,
            String dateRegistered,
            String dateUpdated,
            String password,
            String hobbies,
            String sex,
            String friendsTable
    ) {
        this.userId = userId;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.dateRegistered = dateRegistered;
        this.dateUpdated = dateUpdated;
        this.password = password;
        this.hobbies = hobbies;
        this.sex = sex;
        this.friendsTable = friendsTable;
    }

    public UserDetails(
            String fullName,
            String emailAddress,
            String dateRegistered,
            String dateUpdated,
            String password,
            String hobbies,
            String sex,
            String friendsTable
    ) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.dateRegistered = dateRegistered;
        this.dateUpdated = dateUpdated;
        this.password = password;
        this.hobbies = hobbies;
        this.sex = sex;
        this.friendsTable = friendsTable;
    }

    protected UserDetails(Parcel in) {
        this.userId = in.readInt();
        this.fullName = in.readString();
        this.emailAddress = in.readString();
        this.dateRegistered = in.readString();
        this.dateUpdated = in.readString();
        this.password = in.readString();
        this.sex = in.readString();
        this.friendsTable = in.readString();
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFriendsTable() {
        return friendsTable;
    }

    public void setFriendsTable(String friendsTable) {
        this.friendsTable = friendsTable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.fullName);
        dest.writeString(this.emailAddress);
        dest.writeString(this.dateRegistered);
        dest.writeString(this.dateUpdated);
        dest.writeString(this.password);
        dest.writeString(this.sex);
        dest.writeString(this.friendsTable);
    }
}
