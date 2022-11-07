package com.example.applicationcuisiner;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class SecondActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore;
    private String currentUserID;
    private String type;
    private String status;



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