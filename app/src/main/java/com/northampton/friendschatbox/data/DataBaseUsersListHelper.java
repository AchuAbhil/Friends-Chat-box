package com.northampton.friendschatbox.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBaseUsersListHelper extends SQLiteOpenHelper {

    public static final String ALL_USERS_TABLE = "ALL_USERS_TABLE";
    public static final String COLUMN_FULL_NAME = "COLUMN_FULL_NAME";
    public static final String COLUMN_EMAIL_ADDRESS = "COLUMN_EMAIL_ADDRESS";
    public static final String COLUMN_DATE_REGISTERED = "COLUMN_DATE_REGISTERED";
    public static final String COLUMN_DATE_UPDATED = "COLUMN_DATE_UPDATED";
    public static final String COLUMN_PASSWORD = "COLUMN_PASSWORD";
    public static final String COLUMN_HOBBIES = "COLUMN_HOBBIES";
    public static final String COLUMN_FRIENDS_LIST = "COLUMN_FRIENDS_LIST";
    public static final String COLUMN_FRIENDS_REQUEST_LIST = "COLUMN_FRIENDS_REQUEST_LIST";
    public static final String TAG = DataBaseUsersListHelper.class.getName();

    public DataBaseUsersListHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + ALL_USERS_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_EMAIL_ADDRESS + " TEXT, " +
                COLUMN_DATE_REGISTERED + " TEXT, " +
                COLUMN_DATE_UPDATED + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_HOBBIES + " TEXT, " +
                COLUMN_FRIENDS_LIST + " TEXT, " +
                COLUMN_FRIENDS_REQUEST_LIST + " TEXT " +
                ")";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    public boolean addUser(UserDetails userDetails) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FULL_NAME, userDetails.fullName);
        contentValues.put(COLUMN_EMAIL_ADDRESS, userDetails.emailAddress);
        contentValues.put(COLUMN_DATE_REGISTERED, userDetails.dateRegistered);
        contentValues.put(COLUMN_DATE_UPDATED, userDetails.dateUpdated);
        contentValues.put(COLUMN_PASSWORD, userDetails.password);
        contentValues.put(COLUMN_HOBBIES, userDetails.hobbies);


        long insert = sqLiteDatabase.insert(ALL_USERS_TABLE, null, contentValues);

        return insert != -1;
    }

    public List<UserDetails> getAllUsers(Context context) {

        List<UserDetails> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + ALL_USERS_TABLE;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String emailAddress = cursor.getString(2);
                String dateRegistered = cursor.getString(3);
                String dateUpdated = cursor.getString(4);
                String password = cursor.getString(5);
                String hobbies = cursor.getString(6);
                String friendsList = cursor.getString(7);
                String friendsRequestList = cursor.getString(8);
                Log.d(TAG, "getAllUsers: fullName: " + fullName);
                Log.d(TAG, "getAllUsers: emailAddress: " + emailAddress);
                Log.d(TAG, "getAllUsers: dateRegistered: " + dateRegistered);
                Log.d(TAG, "getAllUsers: dateUpdated: " + dateUpdated);
                Log.d(TAG, "getAllUsers: password: " + password);
                Log.d(TAG, "getAllUsers: hobbies: " + hobbies);
                Log.d(TAG, "getAllUsers: friendsList: " + friendsList);
                Log.d(TAG, "getAllUsers: friendsRequestList: " + friendsRequestList);

                UserDetails userDetails = new UserDetails(
                        userID,
                        fullName,
                        emailAddress,
                        dateRegistered,
                        dateUpdated,
                        password,
                        hobbies,
                        friendsList,
                        friendsRequestList
                );
                returnList.add(userDetails);
            } while (cursor.moveToNext());

        } else {
            Toast.makeText(context, "Fail to fetch Data", Toast.LENGTH_LONG).show();
            //failure case
        }

        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    // below is the method for updating our user based on the name
    public boolean updateUser(String originalName, UserDetails userDetails) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // on below line we are passing all contentValues
        // along with its key and value pair.
        contentValues.put(COLUMN_FULL_NAME, userDetails.fullName);
        contentValues.put(COLUMN_EMAIL_ADDRESS, userDetails.emailAddress);
        contentValues.put(COLUMN_DATE_UPDATED, userDetails.dateUpdated);
        contentValues.put(COLUMN_PASSWORD, userDetails.password);
        contentValues.put(COLUMN_HOBBIES, userDetails.hobbies);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        long insert = db.update(ALL_USERS_TABLE, contentValues, "COLUMN_EMAIL_ADDRESS=?", new String[]{originalName});
        db.close();
        return insert != -1;
    }

    // below is the method for updating our user based on the name
    public HashMap<Boolean, String> updateUserFriendRequestList(String originalName, String friendRequestToString) {
        // calling a method to get writable database.
        HashMap<Boolean, String> stringHashMap = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FRIENDS_REQUEST_LIST, friendRequestToString);
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        long insert = db.update(ALL_USERS_TABLE, contentValues, "COLUMN_EMAIL_ADDRESS=?", new String[]{originalName});
        stringHashMap.put(insert != -1, friendRequestToString);
        db.close();
        return stringHashMap;
    }

    // below is the method for updating our user based on the name
    public HashMap<Boolean, String> updateUserFriendList(String originalName, String friendRequestToString) {
        // calling a method to get writable database.
        HashMap<Boolean, String> stringHashMap = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FRIENDS_LIST, friendRequestToString);
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        long insert = db.update(ALL_USERS_TABLE, contentValues, "COLUMN_EMAIL_ADDRESS=?", new String[]{originalName});
        stringHashMap.put(insert != -1, friendRequestToString);
        db.close();
        return stringHashMap;
    }

    public boolean updateRequestFriendList(String originalName, String friendRequestToString) {
        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FRIENDS_REQUEST_LIST, friendRequestToString);
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        long insert = db.update(ALL_USERS_TABLE, contentValues, "COLUMN_EMAIL_ADDRESS=?", new String[]{originalName});
        db.close();
        return insert != -1;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // this method is called to check if the table exists already.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ALL_USERS_TABLE);
        onCreate(sqLiteDatabase);
    }

    public Boolean deleteOne(FriendRequestData friendData) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String queryString = "DELETE * FROM " + ALL_USERS_TABLE + " WHERE " + COLUMN_FULL_NAME + " = " + friendData.getFullName();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }
}
