package com.example.applicationcuisiner;

public class Admin extends User{

    public Admin(String firstName, String lastName, String email, String Password, String UserID){
        type = "Admin";
        this.firstName = firstName;
        this.lastname = lastName;
        this.email = email;
        this.password = getPassword();
        this.userID = UserID;
    }
}
