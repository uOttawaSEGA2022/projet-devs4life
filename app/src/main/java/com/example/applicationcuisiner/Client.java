package com.example.applicationcuisiner;

import com.example.applicationcuisiner.User;
import com.google.firebase.database.DatabaseReference;

/**
 * Classe com.example.applicationcuisiner.Client qui etends la classe abstraite com.example.applicationcuisiner.User.
 * Elle permet d'enregistrer les informations qui sont uniques aux utilisateurs clients.
 *
 *
 * @author Chloé Al-Frenn
 */
public class Client extends User {

    protected String address;
    protected String cardNumber;
    protected String cardExpiration;
    protected String cardCVV;


public Client(String firstname, String lastname, String email, String password, String address, String cardNumber, String cardExpiration, String cardCVV){
    this.firstName = firstname;
    this.lastName = lastname;
    this.email = email;
    this.password = password;
    this.address = address;
    this.cardNumber = cardNumber;
    this.cardExpiration = cardExpiration;
    this.cardCVV = cardCVV;
}
    //getters

    public String getAddress() {
        return address;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public String getCardExpiration(){
        return cardExpiration;
    }

    public String getCardCVV(){
        return cardCVV;
    }

    //setters

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

}
