package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ClientAddRate extends AppCompatActivity {

    FirebaseFirestore db;
    String currentUserID;
    RatingBar ratingBar;
    String cookId;
    Button rateTheCook;
    Button leave;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_rate);
        db = FirebaseFirestore.getInstance();
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ratingBar = findViewById(R.id.ratingBar);
        rateTheCook = findViewById(R.id.btn_rateCook);
        leave = findViewById(R.id.btn_rateGoBack);
        rateTheCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRateButton(v);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoBackFromRate(v);
            }
        });
        if(getIntent().getExtras() != null) {
            cookId= getIntent().getStringExtra("cookId");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onGoBackFromRate(View view){
        Intent intent = new Intent(this, Client_SeeOrdersActivity.class);
        startActivity(intent);
    }

    public void onRateButton(View view){
       String rate = Float.toString(ratingBar.getRating());
        System.out.println(cookId);
        db.collection("user").document(cookId)
                .update("Rating", rate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

    }

}
