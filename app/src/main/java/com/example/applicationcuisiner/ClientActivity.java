package com.example.applicationcuisiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    ListView repasLV;
    ArrayList<Repas> repasPropArrayList;
    String documentID;
    EditText criteres;
    String recherche;
    Button order;
    Button complain;
    String userID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        repasLV = findViewById(R.id.lv_seeMenuClient);
        repasPropArrayList = new ArrayList<>();
        criteres = findViewById(R.id.et_CritereDeRecherche);

    }


    public void onClickBtnCuisine(View view) {
        repasPropArrayList.clear();
        loadDatainListviewForCuisine();

    }

    public void onClickBtnRepas(View view) {
        repasPropArrayList.clear();
        loadDatainListviewForRepas();

    }

    public void onClickBtnTout(View view) {
        repasPropArrayList.clear();
        loadDatainListviewForEverything();

    }

    public void loadDatainListviewForCuisine() { //called in the on Click
        db.collection("repasProp").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Repas repas = d.toObject(Repas.class);
                                //tri par type de cuisine
                                recherche = criteres.getText().toString();
                                if (recherche.equals(repas.getTypeDeCuisine())){
                                //tri par status du cuisinier
                                CollectionReference cookRef = db.collection("user");
                                Query query = cookRef.whereEqualTo("FullName", repas.getCook());
                                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                System.out.println("the id of this cook:" + repas.getCook() + "is " + document.getId());
                                                documentID = document.getId();
                                                DocumentReference docRef = db.collection("user").document(documentID);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {

                                                            DocumentSnapshot document = task.getResult();
                                                            String status = document.getString("Status");

                                                            System.out.println("status of this cook " + repas.getCook() + "for this dish " + repas.getName() + " is " + status.equals("Active"));

                                                           if (status.equals("Active")) {
                                                               System.out.println("were in the if statement");
                                                              repasPropArrayList.add(repas);
                                                               RepasListAdapterforClient adapter = new RepasListAdapterforClient(ClientActivity.this, repasPropArrayList, userID);
                                                               repasLV.setAdapter(adapter);

                                                           }
                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    }
                                });

                                }
                            }

                        } else {
                            Toast.makeText(ClientActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ClientActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public void loadDatainListviewForRepas() { //called in the on Click
        db.collection("repasProp").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Repas repas = d.toObject(Repas.class);
                                //tri par type de cuisine
                                recherche = criteres.getText().toString();

                                if (recherche.equals(repas.getTypeDeRepas())){
                                    //tri par status du cuisinier
                                    CollectionReference cookRef = db.collection("user");
                                    Query query = cookRef.whereEqualTo("FullName", repas.getCook());
                                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    documentID = document.getId();
                                                    DocumentReference docRef = db.collection("user").document(documentID);
                                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot document = task.getResult();
                                                                String status = document.getString("Status");
                                                                if (status.equals("Active")) {
                                                                    repasPropArrayList.add(repas);
                                                                    RepasListAdapterforClient adapter = new RepasListAdapterforClient(ClientActivity.this, repasPropArrayList, userID);
                                                                    repasLV.setAdapter(adapter);

                                                                }
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        }
                                    });
                                }
                            }

                        } else {
                            Toast.makeText(ClientActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ClientActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loadDatainListviewForEverything() { //called in the on Click
        db.collection("repasProp").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Repas repas = d.toObject(Repas.class);
                                    //tri par status du cuisinier
                                    CollectionReference cookRef = db.collection("user");
                                    Query query = cookRef.whereEqualTo("FullName", repas.getCook());
                                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    System.out.println("the id of this cook:" + repas.getCook() + "is " + document.getId());
                                                    documentID = document.getId();
                                                    DocumentReference docRef = db.collection("user").document(documentID);
                                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {

                                                                DocumentSnapshot document = task.getResult();
                                                                String status = document.getString("Status");

                                                                System.out.println("status of this cook " + repas.getCook() + "for this dish " + repas.getName() + " is " + status.equals("Active"));

                                                                if (status.equals("Active")) {
                                                                    System.out.println("were in the if statement");
                                                                    repasPropArrayList.add(repas);
                                                                    RepasListAdapterforClient adapter = new RepasListAdapterforClient(ClientActivity.this, repasPropArrayList, userID);
                                                                    repasLV.setAdapter(adapter);

                                                                }
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        }
                                    });


                            }

                        } else {
                            Toast.makeText(ClientActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ClientActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onClientSeeOrders(View view){
        Intent intent = new Intent(ClientActivity.this, Client_SeeOrdersActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClientDisconnect(View view){
        Intent intent = new Intent(ClientActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}