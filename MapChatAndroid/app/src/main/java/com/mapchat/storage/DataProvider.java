package com.mapchat.storage;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Nullable;

/**
 * Custom content provider for the database
 *
 */

public class DataProvider extends ContentProvider {

    private static final String AUTHORITY = "mapchat.app.contentprovider";
    public static final Uri PROFILE_URI = Uri.parse("content://" + AUTHORITY + "/profile");
    private static final int PROFILE = 0;
    //public static final Uri UI_DESIGN_URI = Uri.parse("content://" + AUTHORITY + "/ui_design");
    private static final UriMatcher uriMatcher = getUriMatcher();
    //private static final int UI_DESIGN = 1;
    private DatabaseHandler database;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "profile", PROFILE);
        //uriMatcher.addURI(AUTHORITY, "ui_design", UI_DESIGN);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        database = DatabaseHandler.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)) {
            case PROFILE: {
                Cursor c = database.getProfile();
                c.setNotificationUri(getContext().getContentResolver(), PROFILE_URI);
                return c;
            }
            /*case UI_DESIGN: {
                Cursor c = database.getUIDesign(selection, selectionArgs, sortOrder);
                c.setNotificationUri(getContext().getContentResolver(), UI_DESIGN_URI);
                return c;
            }*/

            default:
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case PROFILE:
                boolean success = database.addProfile(values);
                getContext().getContentResolver().notifyChange(PROFILE_URI, null);
                return ContentUris.withAppendedId(PROFILE_URI, 1);


            default:
        }

        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        switch (uriMatcher.match(uri)) {
            case PROFILE: {
                //int r = database.addDualChats(values);
                getContext().getContentResolver().notifyChange(PROFILE_URI, null);
                //getContext().getContentResolver().notifyChange(UI_DESIGN_URI, null);
                return 0;
            }

            default:
        }

        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case PROFILE: {
                int r = database.deleteProfile();
                getContext().getContentResolver().notifyChange(PROFILE_URI, null);
                return r;
            }

            default:
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case PROFILE: {
                //int r = database.updateDualChat(values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(PROFILE_URI, null);
                return 0;
            }

            default:
        }

        return 0;
    }
}
