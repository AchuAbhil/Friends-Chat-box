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

    public void clear() {
        mPref.edit().clear().apply();
    }
}
