package com.example.applicationcuisiner;

public class Repas {
    private String name;
    private String description;
    private double price;
    private String typeDeCuisine;
    private String typedeRepas;

    public void setName(String name){ this.name = name;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTypeDeCuisine(String typeDeCuisine) {
        this.typeDeCuisine = typeDeCuisine;
    }

    public void setTypedeRepas(String typedeRepas) {
        this.typedeRepas = typedeRepas;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getTypeDeCuisine() {
        return typeDeCuisine;
    }

    public String getTypedeRepas() {
        return typedeRepas;
    }
}

