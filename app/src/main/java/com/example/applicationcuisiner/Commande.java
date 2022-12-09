package com.example.applicationcuisiner;

public class Commande {
    private String client;
    private String status; //en attente accepté ou refusé
    private String name;
    private String description;
    private String price;
    private String typeDeCuisine;
    private String typeDeRepas;
    private String cook;
    private String id;
    private double rating;

    public Commande(){

    }

    public Commande(String name, String description, String price, String cook, String typeDeCuisine, String typeDeRepas, double rating, String client, String status, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cook = cook;
        this.typeDeCuisine = typeDeCuisine;
        this.typeDeRepas = typeDeRepas;
        this.rating = rating;
        this.client = client;
        this.status = status;
        this.id = id;
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

    public void setTypeDeRepas(String typeDeRepas) {
        this.typeDeRepas = typeDeRepas;
    }

    public void setCook(String cook) {
        this.cook = cook;
    }

    public void setRating(double rating){this.rating = rating;}

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

    public double getRating(){return rating;}

    public String getStatus() {
        return status;
    }

    public String getClient() {
        return client;
    }

    public String getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setId(String id) {
        this.id = id;
    }
}
