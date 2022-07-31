package com.northampton.friendschatbox.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    public String friendsRequestList;
    public String friendsList;
    public List<FriendRequestData> friendsRequest;
    public List<FriendRequestData> friends;

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
            String friendsList,
            String friendRequestDataList
    ) {
        this.userId = userId;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.dateRegistered = dateRegistered;
        this.dateUpdated = dateUpdated;
        this.password = password;
        this.hobbies = hobbies;
        this.friendsList = friendsList;
        this.friendsRequestList = friendRequestDataList;
        this.friends = getFriends();
        this.friendsRequest = getFriendsRequest();
    }

    protected UserDetails(Parcel in) {
        this.userId = in.readInt();
        this.fullName = in.readString();
        this.emailAddress = in.readString();
        this.dateRegistered = in.readString();
        this.dateUpdated = in.readString();
        this.password = in.readString();
        this.friendsList = in.readString();
        this.friendsRequestList = in.readString();
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

    public String getFriendsRequestList() {
        return friendsRequestList;
    }

    public void setFriendsRequestList(String friendsRequestList) {
        this.friendsRequestList = friendsRequestList;
    }

    public String getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(String friendsList) {
        this.friendsList = friendsList;
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
        dest.writeString(this.friendsRequestList);
        dest.writeString(this.friendsList);
    }

    public List<FriendRequestData> convertToList(String json) {
        List<FriendRequestData> temp;
        Gson gson = new Gson();
        temp = new ArrayList<>();
        if (json != null) {
            if (json.isEmpty()) {
                temp = new ArrayList<>();
            } else {
                Type type = new TypeToken<List<FriendRequestData>>() {
                }.getType();
                temp = gson.fromJson(json, type);
            }
        }
        return temp;
    }

    public List<FriendRequestData> getFriendsRequest() {
        friendsRequest = convertToList(friendsRequestList);
        return friendsRequest;
    }

    public void setFriendsRequest(List<FriendRequestData> friendsRequest) {
        this.friendsRequest = friendsRequest;
    }

    public List<FriendRequestData> getFriends() {
        friends = convertToList(friendsList);
        return friends;
    }

    public void setFriends(List<FriendRequestData> friends) {
        this.friends = friends;
    }
}
