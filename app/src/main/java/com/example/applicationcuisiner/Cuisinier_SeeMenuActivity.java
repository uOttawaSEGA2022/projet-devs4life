package com.example.applicationcuisiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Cuisinier_SeeMenuActivity extends AppCompatActivity {

    FirebaseFirestore db;
    String currentUserID;
    ListView menuLV;
    ArrayList<Repas> menuArrayList;
    private String cookName;
    private String cookLastName;
    private String fullcookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_see_menu);

        db = FirebaseFirestore.getInstance();

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
                        cookName = document.getString("Name");
                        cookLastName = document.getString("LastName");
                        fullcookName = cookName + " " + cookLastName;
                        System.out.println("name is " + fullcookName);


                        // below line is use to initialize our variables
                        menuLV = findViewById(R.id.lv_seeMenu);
                        menuArrayList = new ArrayList<>();

                        // here we are calling a method
                        // to load data in our list view.
                        loadDatainListview();
                    }
                }
            }
        });


    }



    public void loadDatainListview() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("menu").get()
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
                                if(repas.getCook().equals(fullcookName)){
                                menuArrayList.add(repas);
                                }
                            }
                            // after that we are passing our array list to our adapter class.
                            RepasListAdapterforMenu adapter = new RepasListAdapterforMenu(Cuisinier_SeeMenuActivity.this, menuArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            menuLV.setAdapter(adapter);





                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(Cuisinier_SeeMenuActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(Cuisinier_SeeMenuActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void onGoBackToCuisinierMainPage(View view){
        Intent intent = new Intent(this, CuisinierActivity.class);
        startActivity(intent);
    }



}