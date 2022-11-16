package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CuisinierActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier);

        Button btnAdd = (Button)findViewById(R.id.addFoodBtn);

        View.OnClickListener btnListener = view -> startActivity(new Intent(CuisinierActivity.this, CuisinierAddFoodToMenu.class));
        btnAdd.setOnClickListener(btnListener);
    }
}