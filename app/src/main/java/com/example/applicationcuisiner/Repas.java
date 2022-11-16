package com.example.applicationcuisiner;

public class Repas {
    private String name;
    private String description;
    private String price;
    private String typeDeCuisine;
    private String typeDeRepas;
    private String cook;

    public Repas(){

    }

    public Repas(String name, String description, String price, String cook, String typeDeCuisine, String typeDeRepas) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cook = cook;
        this.typeDeCuisine = typeDeCuisine;
        this.typeDeRepas = typeDeRepas;
    }

    public void setName(String name){ this.name = name;}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTypeDeCuisine(String typeDeCuisine) {
        this.typeDeCuisine = typeDeCuisine;
    }

    public void setTypDdeRepas(String typeDeRepas) {
        this.typeDeRepas = typeDeRepas;
    }

    public void setCook(String cook) {
        this.cook = cook;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getTypeDeCuisine() {
        return typeDeCuisine;
    }

    public String getTypeDeRepas() {
        return typeDeRepas;
    }

    public String getCook() {
        return cook;
    }
}

