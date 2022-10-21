package com.example.applicationcuisiner;

import com.example.applicationcuisiner.User;

/**
 * La classe com.example.applicationcuisiner.Cuisinier étend la classe abstraite com.example.applicationcuisiner.User et
 * a pour objectif de permettre d'enregistrer d'informations
 * propres aux utilisateurs cuisiniers.
 * @author Chloé Al-Frenn
 */

public class Cuisinier extends User {
    protected String address;
    //getter

    public String getAddress() {

        return address;
    }

    //setter
    public void setAddress(String address) {
        this.address = address;
    }

}
