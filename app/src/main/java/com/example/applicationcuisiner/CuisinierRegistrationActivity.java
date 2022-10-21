package com.example.applicationcuisiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe est destinée à permettre à l'utilisateur qui souhaite se connecter de saisir toutes
 * ses informations d'authentification lorsqu'il veut se connecter en tant que cuisnier.
 * Lorsque les champs d'information ne sont pas introduits,
 * un message est renvoyé indiquant que le champ est obligatoire.
 * Sinon, les informations seront enregistrer dans la base de données firebase.
 * Le cuisinier peut revenire au menu de connexion à l'aide de firebase.
 * @author Arina Burlac
 * @author Chloé Al-Frenn
 * @author Carolina González
 */

public class CuisinierRegistrationActivity extends AppCompatActivity {
    private EditText cuisinierFirstName, cuisinierLastName, cuisinierEmail, cuisinierPassword, cuisinierAdress, cuisinierDescription;
    private Button registerCuisinier;
    private FirebaseAuth authentication;
    private FirebaseFirestore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_registration);

        authentication=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();

        cuisinierFirstName = (EditText) findViewById(R.id.editTextText_PrenomCuisinier);
        cuisinierLastName = (EditText) findViewById(R.id.editText_NomCuisinier);
        cuisinierEmail = (EditText) findViewById(R.id.editText_AdressCourrielCuisinier);
        cuisinierPassword = (EditText) findViewById(R.id.editText_Mode_de_passeCuisinier);
        cuisinierAdress = (EditText) findViewById(R.id.editText_AdresseRamassageCuisinier);
        cuisinierDescription = (EditText) findViewById(R.id.editText_DescriptionCuisinier);
    }

    public void onRegister(View view){
        if(valide()){
            authentication.createUserWithEmailAndPassword(cuisinierEmail.getText().toString(),cuisinierPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                FirebaseUser user = authentication.getCurrentUser();
                DocumentReference collect = store.collection("Users").document(user.getUid());
                //Store data

                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(CuisinierRegistrationActivity.this,"Votre compte a ete crée",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Map<String,Object> userInfo=new HashMap<>();
                    userInfo.put("Name",cuisinierFirstName.getText().toString());
                    userInfo.put("LastName",cuisinierLastName.getText().toString());
                    userInfo.put("Email",cuisinierEmail.getText().toString());
                    userInfo.put("Password",cuisinierPassword.getText().toString());
                    userInfo.put("Address",cuisinierAdress.getText().toString());
                    userInfo.put("Description",cuisinierDescription.getText().toString());

                    finish();//user cannot go back to registration
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CuisinierRegistrationActivity.this,"Erreur de registre",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void onGoBack(View view){
        startActivity(new Intent(CuisinierRegistrationActivity.this, MainActivity.class));
    }

    /**
     * Cette methode vérifie que l'utilisateur a saisi toutes les informations d'authentification dans tout
     * les edit text. Si l'utilisateur a saisi toutes les données, un message est renvoyé indiquant que le champ est obligatoire.
     * La méthode va return true si et seulement si tous les champs ont été remplis.
     */

    private boolean valide() {
        Boolean result = true;

        String prenom = cuisinierFirstName.getText().toString();
        if (prenom.isEmpty()) {
            result = false;
            cuisinierFirstName.setHint("Ce champ est obligatoire  Prenom");
        }
        String nom = cuisinierLastName.getText().toString();
        if (nom.isEmpty()) {
            result = false;
            cuisinierLastName.setHint("Ce champ est obligatoire : nom");
        }
        String email = cuisinierEmail.getText().toString();
        if (email.isEmpty()) {
            result = false;
            cuisinierEmail.setHint("Ce champ est obligatoire : email");
        }
        String password = cuisinierPassword.getText().toString();
        if (password.isEmpty()) {
            result = false;
            cuisinierPassword.setHint("Ce champ est obligatoire : mot de passe");
        }
        String adresse = cuisinierAdress.getText().toString();
        if (adresse.isEmpty()) {
            result = false;
            cuisinierAdress.setHint("Ce champ est obligatoire : adresse");
        }
        String description = cuisinierDescription.getText().toString();
        if (description.isEmpty()) {
            result = false;
            cuisinierDescription.setHint("Ce champ est obligatoire : Description de soi-même");
        }

        return result;
    }
}