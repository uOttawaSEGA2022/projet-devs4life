package com.example.applicationcuisiner;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//ajouter une methode qui update les infos sur firebase grace a l'id
//stocker les cuisiniers dans une liste/hashmap triee par leur uid
//admin peut appeler updateinfo pour cuisinier et changer son status/moment banned ou on appelle une autre methode qui s'appelle startban/setban

public class Cuisinier extends User{

    String address;
    String description;
    String status; //Active, Banned or PermanentlyBanned
    Date momentBanned; //use the method getTime() and compare it to timeBanned to see if the user is still banned.
    long durationBanned;
    Timer time;
    //can i save an image? for the void check

    public Cuisinier(String userID,String firstname, String lastname, String email, String password, String address, String description){
        type = "Cuisinier";
        status = "Active";
        this.userID = userID;
        this.firstName = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public Date getMomentBanned() {
        return momentBanned;
    }

    public long getDurationBanned() {
        return durationBanned;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationBanned(long durationBanned) {
        this.durationBanned = durationBanned;
    }

    public void setMomentBanned(Date momentBanned) {
        this.momentBanned = momentBanned;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
