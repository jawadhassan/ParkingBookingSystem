package com.example.hamid_pc.parkingbookingsystem;


public class Area {

    private String mAreaId;
    private String mPlotId;
    private String mBookingStartDate;
    private String mBookingEndDate;
    private String mUserId;
    private Boolean mBooked;

    public Area(String areaId, String plotId, String bookingStartDate, String bookingEndDate, String userId, Boolean booked) {
        mAreaId = areaId;
        mPlotId = plotId;
        mBookingStartDate = bookingStartDate;
        mBookingEndDate = bookingEndDate;
        mUserId = userId;
        mBooked = booked;
    }

    public Area() {
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

    public String getBookingStartDate() {
        return mBookingStartDate;
    }

    public void setBookingStartDate(String bookingStartDate) {
        mBookingStartDate = bookingStartDate;
    }

    public String getBookingEndDate() {
        return mBookingEndDate;
    }

    public void setBookingEndDate(String bookingEndDate) {
        mBookingEndDate = bookingEndDate;
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
