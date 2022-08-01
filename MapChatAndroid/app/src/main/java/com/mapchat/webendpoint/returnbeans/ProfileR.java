package com.mapchat.webendpoint.returnbeans;

public class ProfileR {
    private long id;
    private String profileName;
    private String emailId;

    public ProfileR() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ProfileR(long id, String profileName, String emailId) {
        super();
        this.id = id;
        this.profileName = profileName;
        this.emailId = emailId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
