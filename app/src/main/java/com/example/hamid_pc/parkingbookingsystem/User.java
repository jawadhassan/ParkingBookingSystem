package com.example.hamid_pc.parkingbookingsystem;


public class User {
    private String mUuid;
    private String mName;
    private String mEmail;
    private String mRole;

    public User(String mUuid, String mName, String mEmail, String mRole) {
        this.mUuid = mUuid;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mRole = mRole;
    }

    public User() {
    }

    public String getmUuid() {
        return mUuid;
    }

    public void setmUuid(String mUuid) {
        this.mUuid = mUuid;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmRole() {
        return mRole;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }
}
