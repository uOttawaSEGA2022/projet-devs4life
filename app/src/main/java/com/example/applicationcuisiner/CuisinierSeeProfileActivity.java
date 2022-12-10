package com.example.applicationcuisiner;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CuisinierSeeProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_see_profile);
        Button goBack = findViewById(R.id.btn_goBackProfile);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoBackFromProfile(v);
            }
        });

        TextView name = findViewById(R.id.tv_profileName);
        TextView email = findViewById(R.id.tv_profileEmail);
        TextView rating = findViewById(R.id.tv_profileRating);
        TextView description = findViewById(R.id.tv_profileDescription);
        TextView address = findViewById(R.id.tv_profileAddress);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        fireStore = FirebaseFirestore.getInstance();
        System.out.println(user.getUid());
        DocumentReference docRef = fireStore.collection("user").document(user.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name.setText("name: "+ document.get("FullName"));
                        email.setText("email: "+ document.get("Email"));
                        rating.setText("rating: "+ document.get("Rating"));
                        description.setText("description: "+ document.get("Description"));
                        address.setText("address: "+ document.get("Address"));
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


    public void onGoBackFromProfile(View view){
        Intent intent = new Intent(this, CuisinierActivity.class);
        startActivity(intent);
    }
}