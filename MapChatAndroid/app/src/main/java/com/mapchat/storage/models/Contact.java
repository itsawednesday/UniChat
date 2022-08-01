package com.mapchat.storage.models;

import android.database.sqlite.SQLiteDatabase;

public class Contact {

    public static final String ID = "_ID";
    public static final String PROFILE_NAME = "ProfileName";
    public static final String EMAIL_ID = "EmailId";
    public static final String STATUS = "Status";

    private long _id;
    private String profileName;
    private String emailId;
    private int status;

    public static void onCreate(SQLiteDatabase database) {
        //database.execSQL("Create Table Contact(_ID Integer Primary Key autoincrement, ProfileName Text Not Null, EmailId Text Not Null, Status Integer Not Null);");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        // Drop older table if existed
        //database.execSQL("Drop Table If Exists Contact;");
        //onCreate(database);
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
