package com.mapchat.storage.models;

import android.database.sqlite.SQLiteDatabase;

public class Profile {

    public static final String ID = "_ID";
    public static final String PROFILE_NAME = "ProfileName";
    public static final String EMAIL_ID = "EmailId";

    private long _id;
    private String profileName;
    private String emailId;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL("Create Table Profile(_ID Integer Primary Key autoincrement, ProfileName Text Not Null, EmailId Text Not Null);");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        // Drop older table if existed
        database.execSQL("Drop Table If Exists Profile;");
        onCreate(database);
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
