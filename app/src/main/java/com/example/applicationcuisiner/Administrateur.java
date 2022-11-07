package com.example.applicationcuisiner;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class Administrateur extends AppCompatActivity {
    DatabaseReference databaseProducts;


    ListView listViewPlaintes;

    List<Plainte> plaintes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        databaseProducts = FirebaseDatabase.getInstance().getReference("plaintes");

        listViewPlaintes = (ListView) findViewById(R.id.listViewPlaintes);


        plaintes = new ArrayList<>();


        listViewPlaintes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Plainte plainte = plaintes.get(i);
                showUpdateDeleteDialog(plainte.getId(), plainte.getCuisinierName());
                return true;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getChildrenCount());
                System.out.println(dataSnapshot.getKey());

                plaintes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Plainte plainte = postSnapshot.getValue(Plainte.class);
                    plaintes.add(plainte);
                }
                Plaintes_Liste productsAdapter = new Plaintes_Liste(Administrateur.this,plaintes);
                listViewPlaintes.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showUpdateDeleteDialog(final String productId, String productName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.actions, null);
        dialogBuilder.setView(dialogView);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(productName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlainte(productId);
                b.dismiss();
            }
        });
    }

    private boolean deletePlainte(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("plaintes").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(),"Plainte Deleted", Toast.LENGTH_LONG).show();
        return true;


    }


}

