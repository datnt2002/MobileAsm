package com.example.mobileasm.models;

public class ObservationsModel {
    private int obsId;
    private String obsName;
    private String obsDate;
    private String obsTime;
    private String obsComment;
    private String obsSighting;
    private String obsWeather;
    private byte[] obsImage;

    public ObservationsModel(int obsId, String obsName, String obsDate,String obsTime, String obsComment, byte[] obsImage) {
        this.obsId = obsId;
        this.obsName = obsName;
        this.obsDate = obsDate;
        this.obsTime = obsTime;
        this.obsComment = obsComment;
        this.obsImage = obsImage;
    }

    public ObservationsModel(int obsId, String obsName, String obsDate, String obsTime, String obsComment, String obsSighting, String obsWeather, byte[] obsImage) {
        this.obsId = obsId;
        this.obsName = obsName;
        this.obsDate = obsDate;
        this.obsTime = obsTime;
        this.obsComment = obsComment;
        this.obsSighting = obsSighting;
        this.obsWeather = obsWeather;
        this.obsImage = obsImage;
    }

    public ObservationsModel() {
    }

    public int getObsId() {
        return obsId;
    }

    public void setObsId(int obsId) {
        this.obsId = obsId;
    }

    public String getObsName() {
        return obsName;
    }

    public void setObsName(String obsName) {
        this.obsName = obsName;
    }

    public String getObsDate() {
        return obsDate;
    }


    public void setObsDate(String obsDate) {
        this.obsDate = obsDate;
    }

    public String getObsTime() {
        return obsTime;
    }

    public void setObsTime(String obsTime) {
        this.obsTime = obsTime;
    }

    public String getObsSighting() {
        return obsSighting;
    }

    public void setObsSighting(String obsSighting) {
        this.obsSighting = obsSighting;
    }

    public String getObsWeather() {
        return obsWeather;
    }

    public void setObsWeather(String obsWeather) {
        this.obsWeather = obsWeather;
    }

    public String getObsComment() {
        return obsComment;
    }

    public void setObsComment(String obsComment) {
        this.obsComment = obsComment;
    }

    public byte[] getObsImage() {
        return obsImage;
    }

    public void setObsImage(byte[] obsImage) {
        this.obsImage = obsImage;
    }

}
