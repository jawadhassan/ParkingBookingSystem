package com.example.hamid_pc.parkingbookingsystem;



public class Area {

    private String mAreaId;
    private String mPlotId;
    private int mAreaNum;

    public Area(String areaId, String plotId, int areaNum) {
        mAreaId = areaId;
        mPlotId = plotId;
        mAreaNum = areaNum;
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


    public int getAreaNum() {
        return mAreaNum;
    }

    public void setAreaNum(int areaNum) {
        mAreaNum = areaNum;
    }
}
