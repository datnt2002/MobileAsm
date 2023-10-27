package com.example.mobileasm.models;

public class ObservationsModel {
    private int obsId;
    private String obsName;
    private String obsDate;
    private String obsComment;
    private byte[] obsImage;

    public ObservationsModel(int obsId, String obsName, String obsDate, String obsComment, byte[] obsImage) {
        this.obsId = obsId;
        this.obsName = obsName;
        this.obsDate = obsDate;
        this.obsComment = obsComment;
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