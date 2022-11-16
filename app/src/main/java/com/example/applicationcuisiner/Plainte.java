package com.example.applicationcuisiner;

public class Plainte {
    private String _id;
    private String _productname;
    private double _price;

    public Plainte() {
    }
    public Plainte(String id, String productname, double price) {
        _id = id;
        _productname = productname;
        _price = price;
    }
    public Plainte(String productname, double price) {
        _productname = productname;
        _price = price;
    }

    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }
    public void setProductName(String productname) {
        _productname = productname;
    }
    public String getProductName() {
        return _productname;
    }
    public void setPrice(double price) {
        _price = price;
    }
    public double getPrice() {
        return _price;
    }
}