package com.example.hamid_pc.parkingbookingsystem;


public class Feedback {

    private String userId;
    private String feedBack;
    private String feedBackReply;

    public Feedback(String userId, String feedBack, String feedBackReply) {
        this.userId = userId;
        this.feedBack = feedBack;
        this.feedBackReply = feedBackReply;
    }

    public Feedback() {
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getFeedBackReply() {
        return feedBackReply;
    }

    public void setFeedBackReply(String feedBackReply) {
        this.feedBackReply = feedBackReply;
    }
}
