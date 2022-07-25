package com.northampton.friendschatbox.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.northampton.friendschatbox.data.models.FriendData;

import java.util.ArrayList;
import java.util.List;

public class DataBaseFDUsersListHelper extends SQLiteOpenHelper {

    public static final String FD_LIST_TABLE = "FD_LIST_TABLE";
    public static final String COLUMN_FULL_NAME = "COLUMN_FULL_NAME";

    public DataBaseFDUsersListHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + FD_LIST_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_NAME + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    public boolean addFriend(FriendData friendData) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FULL_NAME, friendData.fullName);

        long insert = sqLiteDatabase.insert(FD_LIST_TABLE, null, contentValues);

        return insert != -1;
    }

    public List<FriendData> getAllFriends(Context context) {

        List<FriendData> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + FD_LIST_TABLE;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(0);
                String fullName = cursor.getString(1);

                FriendData friendData = new FriendData(
                        userID,
                        fullName
                );
                returnList.add(friendData);
            } while (cursor.moveToNext());

        } else {
            Toast.makeText(context, "Fail to fetch Data", Toast.LENGTH_LONG).show();
            //failure case
        }

        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
