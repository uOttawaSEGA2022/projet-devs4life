package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdministratorActivity extends AppCompatActivity {
    DatabaseReference databsePlaintes;

    EditText editTextName;
    EditText editTextPlainte;
    Button buttonAddPlainte;
    ListView listViewPlaintes;
    List<Plainte> plaintes;
    EditText editTextTemp;
    FirebaseFirestore db;
    String documentID;
    TextView noCooksname;
    TextView noPlainte;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        databsePlaintes = FirebaseDatabase.getInstance().getReference("plaintes");
        db = FirebaseFirestore.getInstance();
        editTextName = (EditText) findViewById(R.id.editTextName);

        editTextTemp= (EditText) findViewById(R.id.editTextTemp);
        listViewPlaintes = (ListView) findViewById(R.id.listViewPlaintes);
        buttonAddPlainte = (Button) findViewById(R.id.addButton);

        noCooksname = findViewById(R.id.textView_noCooksname);
        noPlainte = findViewById(R.id.textView_noPlainte);

        plaintes = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddPlainte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlainte();
            }
        });

        listViewPlaintes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plainte plainte = plaintes.get(i);
                showUpdateDeleteDialog(plainte.getId(), plainte.getCooksname());
                return true;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        databsePlaintes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getChildrenCount());
                System.out.println(dataSnapshot.getKey());

                plaintes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Plainte plainte = postSnapshot.getValue(Plainte.class);
                    plaintes.add(plainte);
                }
                PlainteList productsAdapter = new PlainteList(AdministratorActivity.this, plaintes);
                listViewPlaintes.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showUpdateDeleteDialog(final String plainteId, String cooksname) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update, null);
        dialogBuilder.setView(dialogView);

        final Button buttonSuspend = (Button) dialogView.findViewById(R.id.buttonSuspendPerm);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeletePlainte);
        final Button buttonSuspendFor = (Button) dialogView.findViewById(R.id.buttonSuspendFor);
        final EditText timeSuspendend = (EditText) dialogView.findViewById(R.id.editTextTime);


        dialogBuilder.setTitle(cooksname);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banPerm(plainteId, cooksname);
                b.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlainte(plainteId);
                b.dismiss();
            }
        });

        buttonSuspendFor.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                banTemp(plainteId, cooksname,  timeSuspendend.getText().toString());
                //SuspendFor(cooksname,time); //cooksname parameter
                b.dismiss();

            }

        });
    }


    private void banPerm(String id, String cook){
// Create a reference to the cities collection
        CollectionReference citiesRef = db.collection("user");

// Create a query against the collection.
        Query query = citiesRef.whereEqualTo("Name", cook);
        //TODO: if we need the full name just create a new field full name in cuisinier which will contain the addition of the name and last name
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("the id of this cook:"+ cook +"is " +document.getId());
                        documentID= document.getId();
                        //TODO: change the value status
                        db.collection("user").document(documentID)
                                .update(
                                        "Status", "PermanentlyBanned"
                                );
                        //document.getData();

                    }
                }
            }
        });

        deletePlainte(id);//delete the plainte once the cook is banned
    }

    private void banTemp(String id, String cook, String timeBan){
// Create a reference to the cities collection
        CollectionReference citiesRef = db.collection("user");

// Create a query against the collection.
        Query query = citiesRef.whereEqualTo("Name", cook);
        //TODO: if we need the full name just create a new field full name in cuisinier which will contain the addition of the name and last name
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("the id of this cook:"+ cook +"is " +document.getId());
                        documentID= document.getId();
                        //TODO: change the value status
                        //TODO: change the value of time banned
                        db.collection("user").document(documentID)
                                .update(
                                        "Status", "Banned",
                                        "Time", timeBan
                                );
                        //document.getData();
                        //TODO: change the method in cuisinier tempbanned to match the new time :)

                        System.out.println("the date of this cook:"+ cook +"is " +document.getData());
                        //document.getData();
                    }
                }
            }
        });

        deletePlainte(id);//delete the plainte once the cook is banned
    }

    private boolean deletePlainte(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("plaintes").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(),"Plainte Deleted", Toast.LENGTH_LONG).show();
        return true;

    }

    private void addPlainte() {

        String name = editTextName.getText().toString().trim();

        String temp = editTextTemp.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(temp)){

            String id = databsePlaintes.push().getKey();
            Plainte plainte = new Plainte(id,name,temp);
            databsePlaintes.child(id).setValue(plainte);
            editTextName.setText("");

            editTextTemp.setText("");
            Toast.makeText(this,"Plainte added",Toast.LENGTH_LONG).show();


        }
        if (TextUtils.isEmpty(name)){
            noCooksname.setText("Cooksname obligatoire");
            Toast.makeText(this,"Please enter a Cooksname",Toast.LENGTH_LONG).show();
        }
        if (!TextUtils.isEmpty(name)){
            noCooksname.setText("");
        }
        if (TextUtils.isEmpty(name)){
            noPlainte.setText("Plainte obligatoire");
            Toast.makeText(this,"Please enter a plainte",Toast.LENGTH_LONG).show();
        }
        if (!TextUtils.isEmpty(temp)){
            noPlainte.setText("");
        }




    }
/*
    private void SuspendFor(cooksname,time) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("plaintes").child(cooksname);
        dR.setValue(time);
        Toast.makeText(getApplicationContext(), "Cook banned", Toast.LENGTH_SHORT).show();

    }

 */


    public void onDisconnectAdministrator(View view){
        Intent intent = new Intent(AdministratorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}