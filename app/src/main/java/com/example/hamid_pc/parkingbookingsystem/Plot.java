package com.example.hamid_pc.parkingbookingsystem;


public class Plot {

    private String mPlotName;
    private int mNumOfArea;
    private String mUuid;

    public Plot(String plotName, int numOfArea, String uuid) {
        mPlotName = plotName;
        mNumOfArea = numOfArea;
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

    public int getNumOfArea() {
        return mNumOfArea;
    }

    public void setNumOfArea(int numOfArea) {
        mNumOfArea = numOfArea;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }
}
