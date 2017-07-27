package com.example.hamid_pc.parkingbookingsystem;


public class Slot {
    private String SlotName;
    private String SlotId;
    private String PlotId;
    private int NumOfArea;

    public Slot(String slotName, String slotId, String plotId, int numOfArea) {
        SlotName = slotName;
        SlotId = slotId;
        PlotId = plotId;
        NumOfArea = numOfArea;
    }

    public Slot() {
    }

    public String getSlotName() {
        return SlotName;
    }

    public void setSlotName(String slotName) {
        SlotName = slotName;
    }

    public String getSlotId() {
        return SlotId;
    }

    public void setSlotId(String slotId) {
        SlotId = slotId;
    }

    public String getPlotId() {
        return PlotId;
    }

    public void setPlotId(String plotId) {
        PlotId = plotId;
    }

    public int getNumOfArea() {
        return NumOfArea;
    }

    public void setNumOfArea(int numOfArea) {
        NumOfArea = numOfArea;
    }
}
