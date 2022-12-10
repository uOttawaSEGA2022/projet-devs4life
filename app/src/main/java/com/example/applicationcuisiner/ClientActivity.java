package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

import org.w3c.dom.Text;

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

    TextView clienterror;
    String userID;

    String currentUserID;
    String clientName;
    String clientLastName;
    String fullclientName;



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
        criteres = (EditText) findViewById(R.id.et_CritereDeRecherche);
        clienterror = findViewById(R.id.tv_clientErreur);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println("id is" + currentUserID);
        DocumentReference docRef = db.collection("user").document(currentUserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        System.out.println("were inside the document");
                        clientName = document.getString("Name");
                        clientLastName = document.getString("LastName");
                        fullclientName = clientName + " " + clientLastName;
                        System.out.println("name is " + fullclientName);

                        db.collection("repasOrdered")
                                .whereEqualTo("client", fullclientName)
                                .whereEqualTo("status", "Accepted")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                addNotification("accepted");
                                                break;
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });


                        db.collection("repasOrdered")
                                .whereEqualTo("client", fullclientName)
                                .whereEqualTo("status", "Refused")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                addNotification("refused");
                                                break;
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

                    }
                }
            }
        });




    }

    // Creates and displays a notification
    private void addNotification(String status) {

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, ClientActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        //Channel
        int NOTIFICATION_ID = 234;

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.citron_cropped)
                .setContentTitle("My notification")
                .setContentText("Your order has been "+ status)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }


    public void onClickBtnCuisine(View view) {
        String texte = criteres.getText().toString();
        if(texte.isEmpty()){
            repasPropArrayList.clear();
            System.out.println("field is empty");
            clienterror.setText("il faut entrer un critere");
        } else {
            repasPropArrayList.clear();
            loadDatainListviewForCuisine();
        }

    }

    public void onClickBtnRepas(View view) {
        String texte = criteres.getText().toString();
        if(texte.isEmpty()){
            repasPropArrayList.clear();
            System.out.println("field is empty");
            clienterror.setText("il faut entrer un critere");
        } else {
            repasPropArrayList.clear();
            loadDatainListviewForRepas();
        }

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