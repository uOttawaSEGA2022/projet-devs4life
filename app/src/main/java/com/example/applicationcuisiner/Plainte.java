package com.example.applicationcuisiner;

public class Plainte {
    private String _id;
    private String _cooksname;

    private String _temp;

    public Plainte() {
    }
    public Plainte(String id, String cooksname,String temp) {
        _id = id;
        _cooksname = cooksname;
        _temp=temp;
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

    public void setTemp(String temp) {
        _temp=temp;
    }
    public String getTemp() {
        return _temp;
    }
}