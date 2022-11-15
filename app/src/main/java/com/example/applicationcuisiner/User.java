package com.example.applicationcuisiner;

/**
 * Classe d'utilisateur que tout les type d'utilisateur vont etendre.
 * no setType since the type cannot change.
 */
public class User {
    protected String lastname;
    protected String firstName;
    protected String email;
    protected String password;
    protected String type;
    protected String userID;

    public String getLastname() {
        return lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getUserID() {
        return userID;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
