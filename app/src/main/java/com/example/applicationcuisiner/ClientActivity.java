package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ListView repasLV;
    ArrayList<Repas> repasPropArrayList;
    String fullcookname;
    String documentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        db = FirebaseFirestore.getInstance();

        repasLV = findViewById(R.id.lv_seeMenuClient);
        repasPropArrayList = new ArrayList<>();

    }

    public void onClickBtnCuisine(View view) {
        loadDatainListviewForCuisine();
    }



    public void loadDatainListviewForCuisine() { //called in the on Click
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("repasProp").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                Repas repas= d.toObject(Repas.class);
                                // after getting data from Firebase we are
                                // storing that data in our array list

                                fullcookname = repas.getCook();

                                CollectionReference citiesRef = db.collection("user");
                                Query query = citiesRef.whereEqualTo("FullName", fullcookname);

                                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                System.out.println("the id of this cook:"+ fullcookname +"is " +document.getId());
                                                documentID = document.getId();

                                                DocumentReference docRef = db.collection("user").document(documentID);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            // Document found in the offline cache
                                                            DocumentSnapshot document = task.getResult();
                                                            String status = document.getString("Status");
                                                            if(status.equals("Active"))
                                                                repasPropArrayList.add(repas); //adds all the repas of the active cuisinier
                                                            //TODO: check if the criteria matches from the edittext
                                                            Log.d(TAG, "Cached document data: " + document.getData());
                                                        } else {
                                                            Log.d(TAG, "Cached get failed: ", task.getException());
                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    }
                                });

                            }
                            // after that we are passing our array list to our adapter class.
                            RepasListAdapterforRepas adapter = new RepasListAdapterforRepas(ClientActivity.this, repasPropArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            repasLV.setAdapter(adapter);





                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(ClientActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(ClientActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loadDatainListviewForRepas() { //called in the on Click
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("repasProp").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                Repas repas= d.toObject(Repas.class);
                                // after getting data from Firebase we are
                                // storing that data in our array list

                                fullcookname = repas.getCook();

                                CollectionReference citiesRef = db.collection("user");
                                Query query = citiesRef.whereEqualTo("FullName", fullcookname);

                                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                System.out.println("the id of this cook:"+ fullcookname +"is " +document.getId());
                                                documentID = document.getId();

                                                DocumentReference docRef = db.collection("user").document(documentID);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            // Document found in the offline cache
                                                            DocumentSnapshot document = task.getResult();
                                                            String status = document.getString("Status");
                                                            if(status.equals("Active"))
                                                                repasPropArrayList.add(repas); //adds all the repas of the active cuisinier
                                                            //TODO: check if the criteria matches from the edittext
                                                            Log.d(TAG, "Cached document data: " + document.getData());
                                                        } else {
                                                            Log.d(TAG, "Cached get failed: ", task.getException());
                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    }
                                });

                            }
                            // after that we are passing our array list to our adapter class.
                            RepasListAdapterforRepas adapter = new RepasListAdapterforRepas(ClientActivity.this, repasPropArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            repasLV.setAdapter(adapter);





                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(ClientActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(ClientActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onClientDisconnect(View view){
        Intent intent = new Intent(ClientActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}