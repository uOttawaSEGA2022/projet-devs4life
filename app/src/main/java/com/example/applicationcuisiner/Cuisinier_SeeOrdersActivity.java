
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

public class Cuisinier_SeeOrdersActivity extends AppCompatActivity {

    FirebaseFirestore db;
    String currentUserID;
    ListView menuLV;
    ArrayList<Commande> menuArrayList;
    private String cookName;
    private String cookLastName;
    private String fullcookName;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_see_orders);

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
                        menuLV = findViewById(R.id.lv_seeOrder);
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
        db.collection("repasOrdered").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                Repas repas= d.toObject(Repas.class);

                                if(repas.getCook().equals(fullcookName)){
                                    Commande order = d.toObject(Commande.class);
                                    menuArrayList.add(order);
                                }
                            }
                            // after that we are passing our array list to our adapter class.
                            RepasListAdapterforOrder adapter = new RepasListAdapterforOrder(Cuisinier_SeeOrdersActivity.this, menuArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            menuLV.setAdapter(adapter);





                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(Cuisinier_SeeOrdersActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(Cuisinier_SeeOrdersActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void onGoBackToCuisinierMainPageFromOrder(View view){
        Intent intent = new Intent(this, CuisinierActivity.class);
        startActivity(intent);
    }



}