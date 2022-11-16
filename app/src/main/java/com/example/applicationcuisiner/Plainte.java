package com.example.applicationcuisiner;

public class Plainte {
    private String _id;
    private String _cooksname;

    private String _plainte;

    public Plainte() {
    }
    public Plainte(String id, String cooksname,String plainte) {
        _id = id;
        _cooksname = cooksname;
        _plainte=plainte;
    }

    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }
    public void setCooksname(String cooksname) {
        _cooksname = cooksname;
    }
    public String getCooksname() {
        return _cooksname;
    }

    public void setPlainte(String plainte) {
        _plainte=plainte;
    }
    public String getPlainte() {
        return _plainte;
    }
}