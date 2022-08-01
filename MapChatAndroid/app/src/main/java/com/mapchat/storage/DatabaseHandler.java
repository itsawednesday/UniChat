package com.mapchat.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.mapchat.storage.models.Profile;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables

    private static DatabaseHandler database;

    private DatabaseHandler(Context context) {
        super(context, "MapStorage", null, 11);
    }

    /**
     * Use the application context, which will ensure that you
     * don't accidentally leak an Activity's context.
     * @param context
     * @return
     */

    public static synchronized DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (database == null) {
            database = new DatabaseHandler(context.getApplicationContext());
        }
        return database;
    }

    // Creating Tables

    /**
     * Creates the sqlite database
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Table creation
        Profile.onCreate(db);

        Log.d("Database : ", "Database and Table Created");
    }

    // Upgrading database

    /**
     *
     * updates the database tables
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Upgrade Tables

        Profile.onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */


    public synchronized Cursor getProfile() {
        SQLiteDatabase rdb = database.getReadableDatabase();
        return rdb.query("Profile", new String[]{"_ID", "ProfileName", "EmailId"}, null, null, null, null, null);
    }

    public synchronized boolean addProfile(ContentValues values) {
        database.getWritableDatabase().delete("Profile", null, null);
        long id = database.getWritableDatabase().insert("Profile", "", values);
        Log.d("Profile Inserted", "Id" + id);
        if (id <= 0) {
            return false;
        }
        return true;
    }

    public synchronized int deleteProfile() {
        int count = database.getWritableDatabase().delete("Profile", null, null);
        Log.d("Profile Deleted", "Logout");
        /*
        TO DO
        Delete all table data here
         */

        return count;
    }

    /**
     * Searches databse for values
     * Adds the values to contacts
     *
     * @param values
     * @return
     */

    public synchronized int addContacts(ContentValues[] values) {
        int returnVal = values.length;
        try {
            database.getWritableDatabase().beginTransaction();
            for (ContentValues cv : values) {
                database.getWritableDatabase().insert("Contact", "", cv);
            }
            database.getWritableDatabase().setTransactionSuccessful();
        } catch (SQLException e) {
            returnVal = 0;
        } finally {
            database.getWritableDatabase().endTransaction();
        }

        return returnVal;
    }

    /**
     *
     * updates database values for an entry
     *
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */

    public synchronized int updateContact(ContentValues values, String selection, String[] selectionArgs) {
        long id = database.getWritableDatabase().update("Contact", values, selection, selectionArgs);
        if (id <= 0) {
            return (int) id;
        }
        return (int) id;
    }

    /**
     * deletes an entry from the database
     * @param selection
     * @param selectionArgs
     * @return
     */

    public synchronized int deleteContact(String selection, String[] selectionArgs) {
        long id = database.getWritableDatabase().delete("Contact", selection, selectionArgs);
        if (id <= 0) {
            return (int) id;
        }
        return (int) id;
    }

    /**
     * returns the database values for the requested user.
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */

    public synchronized Cursor getRequestReceived(String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase rdb = database.getReadableDatabase();
        return rdb.query("RequestReceived", new String[]{"_ID", "UserID", "ProfileName", "ProfileImage", "Quote"}, selection, selectionArgs, null, null, sortOrder);
    }


    //Dual Chat Header

    /**
     * chat header method
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    public synchronized Cursor getDualChatHeaders(String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase rdb = database.getReadableDatabase();
        SQLiteQueryBuilder queryB = new SQLiteQueryBuilder();
        queryB.setTables("DualChat DC Inner Join Contact C On (DC.SenderUserID=C.UserID Or DC.ReceiverUserID=C.UserID)");
        return queryB.query(rdb, new String[]{"UserID", "ProfileName", "ProfileImage", "Max(MessageTime) As MessageTime", "(Select Count(*) From DualChat DCS Where(DCS.SenderUserID=UserID And DCS.SeenBy=1)) As MessageCount"}, selection, selectionArgs, "UserID,ProfileName,ProfileImage", null, "MessageTime DESC");
    }

}
