package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CuisinierTemporarilyBannedActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_temporarily_banned);

        fireStore = FirebaseFirestore.getInstance();
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // Gets user document from Firestore as reference
        DocumentReference docRef = fireStore.collection("user").document(currentUserID);
    }




    /**
     * Lorsque le cuisinier clique sur le bouton il va etre redirectionner vers MainActivity
     * qui est la page de login.
     * @param view view du boutton
     */

    public void onDisconnect(View view){
        Intent intent = new Intent(CuisinierTemporarilyBannedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}