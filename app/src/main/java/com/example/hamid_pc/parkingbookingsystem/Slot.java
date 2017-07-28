package com.example.hamid_pc.parkingbookingsystem;


public class Slot {
    private String SlotName;
    private String SlotId;
    private int NumOfArea;

    public Slot(String slotName, String slotId, int numOfArea) {
        SlotName = slotName;
        SlotId = slotId;

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

    public int getNumOfArea() {
        return NumOfArea;
    }

    public void setNumOfArea(int numOfArea) {
        NumOfArea = numOfArea;
    }
}
