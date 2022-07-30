package com.northampton.friendschatbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class AppPreferences {

    private static AppPreferences sInstance;
    private final SharedPreferences mPref;

    public AppPreferences(Context context) {
        String preferenceName = context.getPackageName();
        mPref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    private static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppPreferences(context);
        }
    }

    public static synchronized AppPreferences getInstance(Context mContext) {
        if (sInstance == null) {
            initializeInstance(mContext);
        }
        return sInstance;
    }

    public UserDetails getUserInfo() {
        Gson gson = new Gson();
        UserDetails userDetails = new UserDetails();
        String json = mPref.getString("UserDetails", null);
        if (json != null) {
            userDetails = gson.fromJson(json, UserDetails.class);
        }
        return userDetails;
    }

    public void setUserInfo(UserDetails userDetails) {
        clear();

        SharedPreferences.Editor editor = mPref.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(userDetails);
        //UserDetails user1 = gson.fromJson(jsonString,UserDetails.class);
        if (jsonString != null) {
            editor.putString("UserDetails", jsonString);
            editor.commit();
        }

        editor.apply();
    }

    public List<FriendRequestData> getAllFriends() {
        List<FriendRequestData> temp;
        Gson gson = new Gson();
        String content = mPref.getString("FriendList", "");
        temp = new ArrayList<>();
        if (content != null) {
            if (content.isEmpty()) {
                temp = new ArrayList<>();
            } else {
                Type type = new TypeToken<List<FriendRequestData>>() {
                }.getType();
                temp = gson.fromJson(content, type);
            }
        }
        return temp;
    }

    public void setAllFriends(List<FriendRequestData> list) {
        SharedPreferences.Editor editor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("FriendList", json);
        editor.apply();
    }

    public void addFriend(FriendRequestData friendRequestData) {
        List<FriendRequestData> temp = getAllFriends();
        SharedPreferences.Editor editor = mPref.edit();
        temp.add(friendRequestData);
        Gson gson = new Gson();
        String jsonString = gson.toJson(temp);
        editor.putString("FriendList", jsonString).apply();
    }

    public List<FriendRequestData> getAllFriendRequest() {
        List<FriendRequestData> temp;
        Gson gson = new Gson();
        String content = mPref.getString("FriendRequestList", "");
        temp = new ArrayList<>();
        if (content != null) {
            if (content.isEmpty()) {
                temp = new ArrayList<>();
            } else {
                Type type = new TypeToken<List<FriendRequestData>>() {
                }.getType();
                temp = gson.fromJson(content, type);
            }
        }
        return temp;
    }

    public void setAllFriendRequest(List<FriendRequestData> list) {
        SharedPreferences.Editor editor = mPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("FriendRequestList", json);
        editor.apply();
    }

    public List<FriendRequestData> convertToList(String json) {
        List<FriendRequestData> temp;
        Gson gson = new Gson();

        if (json.isEmpty()) {
            temp = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<FriendRequestData>>() {
            }.getType();
            temp = gson.fromJson(json, type);
        }
        return temp;
    }

    public void addFriendRequest(FriendRequestData friendRequestData) {
        List<FriendRequestData> temp = getAllFriends();
        SharedPreferences.Editor editor = mPref.edit();
        temp.add(friendRequestData);
        Gson gson = new Gson();
        String jsonString = gson.toJson(temp);
        editor.putString("FriendRequestList", jsonString).apply();
    }

    public String getFriendRequestToString() {
        return mPref.getString("FriendRequestList", "");
    }

    public String getFriendsToString() {
        return mPref.getString("FriendList", "");
    }

    public void clear() {
        mPref.edit().clear().apply();
    }
}
