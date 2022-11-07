package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class SecondActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore;
    private String currentUserID;
    private String type;
    private String status;
    long duration; //timer duration



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView bienvenue = (TextView) findViewById(R.id.textView_Bienvenu);

        //check the id of the user who's logged in
        fireStore = FirebaseFirestore.getInstance();
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();


// Gets user document from Firestore as reference
        DocumentReference docRef = fireStore.collection("user").document(currentUserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        type = document.getString("Type");
                        status = document.getString("Status");
                        if(type != null && type.equals("Admin")){
                            bienvenue.setText("Bienvenue vous etes un "+type);
                            //start admin activity
                        }
                        else if(type != null && type.equals("Cuisinier")){
                            if(status != null && status.equals("PermanentlyBanned")){
                                bienvenue.setText("Malheureusement vous etes banni de maniere permanente, vous n'aurez plus acces au reste de l'application");
                            } else if (status != null && status.equals("Banned")){
                                bienvenue.setText("Malheureusement vous etes banni pour:");

                                //initialize timer duration
                                duration = TimeUnit.MINUTES.toMillis(1);

                                //initialize coundown timer
                                new CountDownTimer(duration, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        //When tick convert milliseconds to minutes and second

                                        String sDuration = String.format(Locale.ENGLISH, "%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                                   //set converted string on text view
                                        bienvenue.setText("Malheureusement vous etes banni pour: " + sDuration);
                                    }

                                    @Override
                                    public void onFinish() {
                                        //hidetext view
                                        bienvenue.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Countdown timer had ended", Toast.LENGTH_LONG).show();
                                       //set the status back to active
                                        fireStore.collection("user").document(currentUserID).update("Status", "Active").addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                }.start();





                            } else {
                                bienvenue.setText("Bienvenue vous etes un "+type);
                                //start cuisinier activity
                            }
                        } else{
                            bienvenue.setText("Bienvenue vous etes un "+type);
                            //start client activity
                        }
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                } else {
                    Log.d("LOGGER", "get failed with ", task.getException());
                }
            }
        });






    }

    public void onDisconnect(View view){
        Intent intent = new Intent(SecondActivity.this, MainActivity.class); // lets the user move into the next activity
        startActivity(intent);
        Toast.makeText(SecondActivity.this,"Logout successfull", Toast.LENGTH_SHORT).show();
        finish();
    }
}