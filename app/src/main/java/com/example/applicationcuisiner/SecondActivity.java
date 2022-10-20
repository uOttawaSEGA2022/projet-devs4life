package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    public SecondActivity(Object object) {
        System.out.println("Welcome "+object.getClass().getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onDisconnect(View view){
        Intent intent = new Intent(SecondActivity.this, MainActivity.class); // lets the user move into the next activity
        startActivity(intent);
    }
}