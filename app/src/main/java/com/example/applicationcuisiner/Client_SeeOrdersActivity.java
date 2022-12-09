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

public class Client_SeeOrdersActivity extends AppCompatActivity {

    FirebaseFirestore db;
    String currentUserID;
    ListView OrderClientLV;
    ArrayList<Commande> orderArrayList;
    private String clientName;
    private String clientLastName;
    private String fullclientName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_see_orders);

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
                        clientName = document.getString("Name");
                        clientLastName = document.getString("LastName");
                        fullclientName = clientName + " " + clientLastName;
                        System.out.println("name is " + fullclientName);


                        // below line is use to initialize our variables
                        OrderClientLV = findViewById(R.id.lv_seeOrderC);
                        orderArrayList= new ArrayList<>();

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

                                Commande order = d.toObject(Commande.class);
                                if(order.getClient().equals(fullclientName)){
                                    orderArrayList.add(order);
                                }
                            }
                            // after that we are passing our array list to our adapter class.
                            RepasListAdapterforClientOrder adapter = new RepasListAdapterforClientOrder(Client_SeeOrdersActivity.this, orderArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.

                            OrderClientLV.setAdapter(adapter);




                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(Client_SeeOrdersActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(Client_SeeOrdersActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //TODO: create a pop up to delete the refused one and rate the accepted one

    public void onGoBackToClientMainPageFromOrder(View view){
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }
}