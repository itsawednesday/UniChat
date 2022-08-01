package com.mapchat.webendpoint.returnbeans;

public class ContactR {
    private long id;
    private String userEmailId;
    private String contactEmailId;
    /*
     * 0 = Request Sent, 1 = Accepted, 2 = Blocked
     * */
    private int status;

    public ContactR() {
    }

    public ContactR(long id, String userEmailId, String contactEmailId, int status) {
        this.id = id;
        this.userEmailId = userEmailId;
        this.contactEmailId = contactEmailId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getContactEmailId() {
        return contactEmailId;
    }

    public void setContactEmailId(String contactEmailId) {
        this.contactEmailId = contactEmailId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
