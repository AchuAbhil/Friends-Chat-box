package com.northampton.friendschatbox.data;

import android.content.Context;
import android.os.AsyncTask;



public class DatabaseFactory {
    private static DatabaseFactory object;

    private DatabaseFactory(Context context) {
    }

    static public DatabaseFactory setupObject(Context context) {
        if (object == null) {
            object = new DatabaseFactory(context);
        }
        return object;
    }

    static public DatabaseFactory getInstance() {
        if (object == null) {
            throw new IllegalStateException("Object is not setup");
        }
        return object;
    }
/*
    public void insertUserData(List<CircleData> loginModel) {
        AsyncTask.execute(() -> {
            appDatabase.getLocationDao().insert(loginModel);
        });
    }

    public void getCircleDataFromDatabase(DatabaseCallBack<List<CircleData>> listDatabaseCallBack) {
        AsyncTask.execute(() -> {
            List<CircleData> result = appDatabase.getLocationDao().getCircleData();
            listDatabaseCallBack.onSuccess(result);
        });
    }

    public void deleteData() {
        AsyncTask.execute(() -> {
            appDatabase.getLocationDao().delete();

        });
    }

    public void deleteDataById(String id) {
        AsyncTask.execute(() -> {
            appDatabase.getLocationDao().delete(id);

        });
    }
//
//    public void clearRecords(){
//        AsyncTask.execute(() -> {
//            appDatabase.getLocationDao().delete();
//        });
//    }

    public void insertFriendsData(UserFriendsList friendsList) {
        AsyncTask.execute(() -> {
            appDatabase.getFriendsData().insert(friendsList);
        });
    }

    public void getFriendsDataFromDatabase(DatabaseCallBack<LiveData<List<UserFriendsList>>> listDatabaseCallBack) {
        AsyncTask.execute(() -> {
            LiveData<List<UserFriendsList>> result = appDatabase.getFriendsData().getFriendsData();
            listDatabaseCallBack.onSuccess(result);
        });
    }

    public void insertCircleFriendsDetailData(List<FriendsList> FriendsList) {
        AsyncTask.execute(() -> {
            appDatabase.getCircleFriendData().insert(FriendsList);
        });
    }

    public void getCircleFriendsDataFromDatabase(Integer circleIDs, DatabaseCallBack<List<FriendsList>> listDatabaseCallBack) {
        AsyncTask.execute(() -> {
            List<FriendsList> result = appDatabase.getCircleFriendData().getCircleDetail(circleIDs);
            listDatabaseCallBack.onSuccess(result);
        });
    }

    public void insertCircleActiveInactiveData(List<ActiveInActiveBody> activeInActiveBody) {
        AsyncTask.execute(() -> {
            appDatabase.getCircleActiveInactiveData().insert(activeInActiveBody);
        });
    }

    public void getCircleActiveInactiveDataFromDatabase(Boolean isActive,Integer circleID,DatabaseCallBack<List<ActiveInActiveBody>> listDatabaseCallBack) {
        AsyncTask.execute(() -> {
            List<ActiveInActiveBody> result = appDatabase.getCircleActiveInactiveData().getCircleDetail(circleID,isActive);
            listDatabaseCallBack.onSuccess(result);
        });
    }*/
}
