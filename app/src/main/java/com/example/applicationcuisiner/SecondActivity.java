package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    private TextView bienvenu;
    private FirebaseFirestore db;
    private String type;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        bienvenu = (TextView)findViewById(R.id.textView_Bienvenu);
        db = FirebaseFirestore.getInstance();

        db.collection("user").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {

                if(e != null){
                    Toast.makeText(SecondActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                for(DocumentChange documentChange: value.getDocumentChanges()){
                    type = documentChange.getDocument().getData().get("Type").toString();
                    bienvenu.setText("Bienvenue vous êtes un " + type);


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