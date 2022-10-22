package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    private TextView bienvenu;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String type;
    private String userId;
    private String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        bienvenu = (TextView)findViewById(R.id.textView_Bienvenu);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


    }

    public void onDisconnect(View view){
        firebaseAuth.signOut();
        Intent intent = new Intent(SecondActivity.this, MainActivity.class); // lets the user move into the next activity
        startActivity(intent);
        finish();
        Toast.makeText(SecondActivity.this,"Logout successfull", Toast.LENGTH_SHORT).show();
    }
}