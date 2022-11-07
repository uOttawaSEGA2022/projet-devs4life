package com.example.applicationcuisiner;

public class Plainte {
    private String _id;
    private String _cuisinier;
    private String _plainte;

    public Plainte() {
    }
    public Plainte(String id, String cuisinier, String plainte) {
        _id = id;
        _cuisinier = cuisinier;
        _plainte=plainte;
    }
    public Plainte(String cuisinier, String plainte) {
        _cuisinier = cuisinier;
        _plainte=plainte;
    }

    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }
    public void setProductName(String cuisinier) {_cuisinier = cuisinier;
    }
    public String getCuisinierName() {
        return _cuisinier;
    }
    public void setPlainte(String plainte) {
        _plainte = plainte;
    }
    public String getPlainte() {
        return _plainte;
    }
}