package com.example.hamid_pc.parkingbookingsystem;


public class Booking {


    private String mBookingId;
    private String mPlotId;
    private String mAreaId;
    private String mUserId;
    private int mAreaNum;
    private Long mStartDateTime;
    private int mHour;

    public Booking(String bookingId, String plotId, String areaId, String userId, int areaNum, Long startDateTime, int hour) {
        mPlotId = plotId;
        mAreaId = areaId;
        mUserId = userId;
        mStartDateTime = startDateTime;
        mAreaNum = areaNum;
        mHour = hour;
        mBookingId = bookingId;
    }

    public Booking() {
    }

    public String getBookingId() {
        return mBookingId;
    }

    public void setBookingId(String bookingId) {
        mBookingId = bookingId;
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

    public Long getStartDateTime() {
        return mStartDateTime;
    }

    public void setStartDateTime(Long startDateTime) {
        mStartDateTime = startDateTime;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }
}


