package com.example.hamid_pc.parkingbookingsystem;


import java.util.Date;

public class Booking {


    private String mPlotId;
    private String mAreaId;
    private String mUserId;
    private int mAreaNum;
    private Date mStartDate;
    private Date mStartTime;
    private int mHour;

    public Booking(String plotId, String areaId, String userId, int areaNum, Date startDate, Date startTime, int hour) {
        mPlotId = plotId;
        mAreaId = areaId;
        mUserId = userId;
        mStartDate = startDate;
        mStartTime = startTime;
        mAreaNum = areaNum;
        mHour = hour;
    }

    public Booking() {
    }

    public int getAreaNum() {
        return mAreaNum;
    }

    public void setAreaNum(int areaNum) {
        mAreaNum = areaNum;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getPlotId() {
        return mPlotId;
    }

    public void setPlotId(String plotId) {
        mPlotId = plotId;
    }

    public String getAreaId() {
        return mAreaId;
    }

    public void setAreaId(String areaId) {
        mAreaId = areaId;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Date startTime) {
        mStartTime = startTime;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }
}


