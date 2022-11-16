package com.example.applicationcuisiner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CuisinierAddFoodToMenu extends AppCompatActivity {


    // Access a Cloud Firestore instance from your Activity

    // Create a new user with a first and last name
    Map<String, Object> user = new HashMap<>();
    DatabaseReference databaseMenu;
    Button addBtn;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);
         db = FirebaseFirestore.getInstance();
         EditText foodName = findViewById(R.id.foodName);
         EditText foodDescription = findViewById(R.id.foodDescription);
         EditText foodPrice = findViewById(R.id.foodPrice);
         EditText typeOfFood = findViewById(R.id.typeOfFood);
         addBtn = findViewById(R.id.addFoodToDatabase);
         addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FoodName = foodName.getText().toString();
                String FoodDescription = foodDescription.getText().toString();
                String FoodPrice = foodPrice.getText().toString();
                String TypeOfFood = typeOfFood.getText().toString();
                Map<String, Object> menu = new HashMap<>();
                menu.put("Food Name", FoodName);
                menu.put("Food Description", FoodDescription);
                menu.put("Food Price", FoodPrice);
                menu.put("Type Of Food", TypeOfFood);

                db.collection("menu")
                        .add(menu)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(CuisinierAddFoodToMenu.this, "Successfull", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CuisinierAddFoodToMenu.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
