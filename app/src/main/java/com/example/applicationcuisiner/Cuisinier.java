package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//ajouter une methode qui update les infos sur firebase grace a l'id
//stocker les cuisiniers dans une liste/hashmap triee par leur uid
//admin peut appeler updateinfo pour cuisinier et changer son status/moment banned ou on appelle une autre methode qui s'appelle startban/setban

public class Cuisinier extends User{

    private String address;
    private String description;
    private int index;
    private String status; //Active, Banned or PermanentlyBanned
    private long durationBanned;

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

    public void setStatus(String status) {
        this.status = status;
    }



}
