package com.example.applicationcuisiner;

/**
 * Classe qui etends user.
 * Elle store les informations des clients.
 */

//ajouter une methode qui update les infos sur firebase grace a l'id //public void updateInfo ()
    // on l'appelle dans registration activity au lieu de le faire dans l'activite telle quel
//stocker les clients dans une liste/hashmap triee par leur uid

public class Client extends User{
    private String address;
    private int cardNumber;
    private int cardExpiration;
    private int cardCCV;

    /**
     * Class constructor which is called from CuisinierRegistrationActivity, it sets the type to Client automatically.
     *
     * @param firstname entered in CuisinierRegistrationActivity
     * @param lastname entered in CuisinierRegistrationActivity
     * @param email entered in CuisinierRegistrationActivity
     * @param password entered in CuisinierRegistrationActivity
     * @param address entered in CuisinierRegistrationActivity
     * @param cardNumber entered in CuisinierRegistrationActivity
     * @param cardExpiration entered in CuisinierRegistrationActivity
     * @param cardCCV entered in CuisinierRegistrationActivity
     */
    public Client(String userID,String firstname, String lastname, String email, String password, String address, int cardNumber, int cardExpiration, int cardCCV){
        type = "Client";
        this.userID = userID;
        this.firstName = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.cardNumber = cardNumber;
        this.cardExpiration = cardExpiration;
        this.cardCCV = cardCCV;
    }

    public String getAddress() {
        return address;
    }

    public int getCardNumber(){
        return cardNumber;
    }

    public int getCardExpiration() {
        return cardExpiration;
    }

    public int getCardCCV() {
        return cardCCV;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardExpiration(int cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public void setCardCCV(int cardCCV) {
        this.cardCCV = cardCCV;
    }


}
