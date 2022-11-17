package com.example.applicationcuisiner;

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

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

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

    TextView noCooksname;
    TextView noPlainte;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        databsePlaintes = FirebaseDatabase.getInstance().getReference("plaintes");

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

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdatePlainte);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeletePlainte);

        dialogBuilder.setTitle(cooksname);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlainte(plainteId);
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
            ;

        }
        if (TextUtils.isEmpty(name)){
            noCooksname.setText("Cooksname obligatoire");
            Toast.makeText(this,"Please enter a name",Toast.LENGTH_LONG).show();
        }
        if (!TextUtils.isEmpty(name)){
            noCooksname.setText("");
        }
        if (TextUtils.isEmpty(name)){
            noPlainte.setText("Plainte obligatoire");
            Toast.makeText(this,"Please enter a name",Toast.LENGTH_LONG).show();
        }
        if (!TextUtils.isEmpty(temp)){
            noPlainte.setText("");
        }




    }

    /**
     * Lorsque l'administrateur clique sur le bouton il va etre redirectionner vers MainActivity
     * qui est la page de login.
     * @param view view du boutton
     */

    public void onDisconnectAdministrator(View view){
        Intent intent = new Intent(AdministratorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}