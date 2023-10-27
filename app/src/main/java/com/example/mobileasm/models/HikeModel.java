package com.example.mobileasm.models;

public class HikeModel {
    private int hikeId;
    private String hikeName;
    private String hikeLocation;
    private String hikeDate;
    private boolean parkingAvailable;
    private int hikeLength;
    private int hikeLevel;
    private int hikeEstimate;
    private String hikeDescription;


    public HikeModel(int hikeId, String hikeName, String hikeLocation, String hikeDate, boolean parkingAvailable, int hikeLength, int hikeLevel, int hikeEstimate, String hikeDescription) {
        this.hikeId = hikeId;
        this.hikeName = hikeName;
        this.hikeLocation = hikeLocation;
        this.hikeDate = hikeDate;
        this.parkingAvailable = parkingAvailable;
        this.hikeLength = hikeLength;
        this.hikeLevel = hikeLevel;
        this.hikeEstimate = hikeEstimate;
        this.hikeDescription = hikeDescription;
    }

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getHikeLocation() {
        return hikeLocation;
    }

    public void setHikeLocation(String hikeLocation) {
        this.hikeLocation = hikeLocation;
    }

    public String getHikeDate() {
        return hikeDate;
    }

    public void setHikeDate(String hikeDate) {
        this.hikeDate = hikeDate;
    }

    public boolean isParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(boolean parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    public int getHikeLength() {
        return hikeLength;
    }

    public void setHikeLength(int hikeLength) {
        this.hikeLength = hikeLength;
    }

    public int getHikeLevel() {
        return hikeLevel;
    }

    public void setHikeLevel(int hikeLevel) {
        this.hikeLevel = hikeLevel;
    }

    public String getHikeDescription() {
        return hikeDescription;
    }

    public void setHikeDescription(String hikeDescription) {
        this.hikeDescription = hikeDescription;
    }

    public int getHikeEstimate() {
        return hikeEstimate;
    }

    public void setHikeEstimate(int hikeEstimate) {
        this.hikeEstimate = hikeEstimate;
    }
}
