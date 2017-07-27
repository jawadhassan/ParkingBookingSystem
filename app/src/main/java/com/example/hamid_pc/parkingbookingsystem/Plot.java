package com.example.hamid_pc.parkingbookingsystem;


public class Plot {

    private String mPlotName;
    private int mNumOfSlot;
    private String mUuid;

    public Plot(String plotName, int numOfSlot, String uuid) {
        mPlotName = plotName;
        mNumOfSlot = numOfSlot;
        mUuid = uuid;
    }

    public Plot() {
    }

    public String getPlotName() {
        return mPlotName;
    }

    public void setPlotName(String plotName) {
        mPlotName = plotName;
    }

    public int getNumOfSlot() {
        return mNumOfSlot;
    }

    public void setNumOfSlot(int numOfSlot) {
        mNumOfSlot = numOfSlot;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }
}
