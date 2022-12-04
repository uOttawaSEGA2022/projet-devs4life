package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CuisinierAddFoodToMenu extends AppCompatActivity {


    // Access a Cloud Firestore instance from your Activity

    // Create a new user with a first and last name
    Map<String, Object> user = new HashMap<>();
    DatabaseReference databaseMenu;
    Button addBtn;
    FirebaseFirestore db;
    String currentUserID;
    String cname;
    String lastname;
    EditText foodName, foodDescription, foodPrice, typeOfFood, typeOfRepas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_add_to_menu);
         db = FirebaseFirestore.getInstance();
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // below line is use to initialize our variables

        DocumentReference docRef = db.collection("user").document(currentUserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        cname = document.getString("Name");
                        lastname = document.getString("LastName");
                    }
                }
            }
        });

        TextView repasAjouteTV = findViewById(R.id.tv_repasAdded);

         foodName = findViewById(R.id.et_foodName);
         foodDescription = findViewById(R.id.et_foodDescription);
         foodPrice = findViewById(R.id.et_foodPrice);
         typeOfFood = findViewById(R.id.et_typeOfCuisine);
         typeOfRepas = findViewById(R.id.et_typeOfRepas);
         addBtn = findViewById(R.id.btn_addFoodToDatabase);
         addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (valide()) {

                    DocumentReference docRef = db.collection("menu").document(foodName.getText().toString()+ cname + " " + lastname);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    System.out.println("un repas avec le mm nom existe deja ");
                                    repasAjouteTV.setText("un repas avec le meme nom existe deja");

                                } else {
                                    Log.d(TAG, "No such document");
                                    repasAjouteTV.setText("Repas added!");
                                    String name = foodName.getText().toString();
                                    String description = foodDescription.getText().toString();
                                    String price = foodPrice.getText().toString();
                                    String typeFood = typeOfFood.getText().toString();
                                    String typeRepas = typeOfRepas.getText().toString();
                                    Map<String, Object> menu = new HashMap<>();
                                    menu.put("name", name);
                                    menu.put("description", description);
                                    menu.put("price", price);
                                    menu.put("typeDeCuisine", typeFood);
                                    menu.put("typeDeRepas", typeRepas);
                                    menu.put("cook", cname + " " + lastname);

                                    db.collection("menu").document(name + cname + " " + lastname)
                                            .set(menu)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                                repasAjouteTV.setText("");

                            }
                        }
                    });

                } else {
                    repasAjouteTV.setText("");
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onGoBack01(View view){
        Intent intent = new Intent(this, CuisinierActivity.class);
        startActivity(intent);
    }


    private boolean valide() {
        Boolean result = true;


        String name = foodName.getText().toString();
        TextView noNameTV = findViewById(R.id.tv_noFoodName);
        if(name.isEmpty()){
            result = false;
            noNameTV.setText("Champ Obligatoire");
        } else {
           noNameTV.setText("");
        }
        String description = foodDescription.getText().toString();
        TextView noDesTV = findViewById(R.id.tv_noFoodDescription);
        if(description.isEmpty()){
            result = false;
            noDesTV.setText("Champ Obligatoire");
        }else {
            noDesTV.setText("");
        }
        String price = foodPrice.getText().toString();
        TextView noPriceTV = findViewById(R.id.tv_noFoodPrice);
        if(price.isEmpty()){
            result = false;
            noPriceTV.setText("Champ Obligatoire");
        } else {
            noPriceTV.setText("");
        }
        String typeFood = typeOfFood.getText().toString();
        TextView noTypeFTV = findViewById(R.id.tv_noFoodCuisine);
        if(typeFood.isEmpty()){
            result = false;
            noTypeFTV.setText("Champ Obligatoire");
        } else {
            noTypeFTV.setText("");
        }
        String typeRepas = typeOfRepas.getText().toString();
        TextView noTypeRTV = findViewById(R.id.tv_noFoodRepas);
        if(typeRepas.isEmpty()){
            result = false;
            noTypeRTV.setText("Champ Obligatoire");
        } else {
            noTypeRTV.setText("");
        }


        return result;
    }
}
