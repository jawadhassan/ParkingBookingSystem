package com.example.hamid_pc.parkingbookingsystem;


import java.util.Date;

public class Area {

    private String mAreaId;
    private String mPlotId;
    private Date mBookingStartDate;
    private Date mBookingStartTime;
    private int mBookingHour;
    private String mUserId;
    private Boolean mBooked;

    public Area(String areaId, String plotId, Date bookingStartDate, Date bookingStartTime, int bookingHour, String userId, Boolean booked) {
        mAreaId = areaId;
        mPlotId = plotId;
        mBookingStartDate = bookingStartDate;
        mBookingStartTime = bookingStartTime;
        mBookingHour = bookingHour;
        mUserId = userId;
        mBooked = booked;
    }

    public Area() {
    }


    public Date getBookingStartTime() {
        return mBookingStartTime;
    }

    public void setBookingStartTime(Date bookingStartTime) {
        mBookingStartTime = bookingStartTime;
    }

    public String getAreaId() {
        return mAreaId;
    }

    public void setAreaId(String areaId) {
        mAreaId = areaId;
    }

    public String getPlotId() {
        return mPlotId;
    }

    public void setPlotId(String plotId) {
        mPlotId = plotId;
    }

    public Date getBookingStartDate() {
        return mBookingStartDate;
    }

    public void setBookingStartDate(Date bookingStartDate) {
        mBookingStartDate = bookingStartDate;
    }

    public int getBookingHour() {
        return mBookingHour;
    }

    public void setBookingHour(int bookingHour) {
        mBookingHour = bookingHour;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public Boolean getBooked() {
        return mBooked;
    }

    public void setBooked(Boolean booked) {
        mBooked = booked;
    }
}
