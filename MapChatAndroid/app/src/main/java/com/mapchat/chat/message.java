package com.mapchat.chat;


/**
 * Message class for object creation to transfer data between users
 * Define two strings that consist of the message the user sends and their name.
 */

public class message {

    //Define two strings that consist of the message the user sends and their name.
    private String message;
    private long createdAt;
    private String user;

    public message(String message, long createdAt, String user) {
        this.message = message;
        this.createdAt = createdAt;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getUser() {
        return user;
    }
}
