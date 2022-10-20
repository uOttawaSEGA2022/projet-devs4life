package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class CuisinierRegistration extends AppCompatActivity {

    private EditText cuisinierFirstName, cuisinierLastName, cuisinierEmail, cuisinierPassword, cuisinierAdress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinierregistration);
        //assignVariables();
    }
}