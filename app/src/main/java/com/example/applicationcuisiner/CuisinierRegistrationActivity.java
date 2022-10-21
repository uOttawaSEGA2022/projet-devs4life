package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CuisinierRegistrationActivity extends AppCompatActivity {
    private EditText cuisinierFirstName, cuisinierLastName, cuisinierEmail, cuisinierPassword, cuisinierAdress, cuisinierDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_registration);
        assignVariables();
    }
    //a implementer
    public void onRegister(View view){
        if(valide()){

        }
    }
    public void onGoBack(View view){
        startActivity(new Intent(CuisinierRegistrationActivity.this, MainActivity.class));
    }
    private void assignVariables() {
        cuisinierFirstName = (EditText) findViewById(R.id.editTextText_PrenomCuisinier);
        cuisinierLastName = (EditText) findViewById(R.id.editText_NomCuisinier);
        cuisinierEmail = (EditText) findViewById(R.id.editText_AdressCourrielCuisinier);
        cuisinierPassword = (EditText) findViewById(R.id.editText_Mode_de_passeCuisinier);
        cuisinierAdress = (EditText) findViewById(R.id.editText_AdresseRamassageCuisinier);
        cuisinierDescription = (EditText) findViewById(R.id.editText_DescriptionCuisinier);
    }
    /**
     * Cette methode verifie que l'utilisateur a entré des donnees dans tout
     * les edit text present. Sinon on renvoie le message que le champ est obligatoire.
     * Return true seulement si tout les champs sont remplis.
     *
     */

    private boolean valide() {
        Boolean result = true;

        String prenom = cuisinierFirstName.getText().toString();
        if (prenom.isEmpty()) {
            result = false;
            cuisinierFirstName.setHint("Ce champ est obligatoire: Prenom");
        }
        String nom = cuisinierLastName.getText().toString();
        if (nom.isEmpty()) {
            result = false;
            cuisinierLastName.setHint("Ce champ est obligatoire: nom");
        }
        String email = cuisinierEmail.getText().toString();
        if (email.isEmpty()) {
            result = false;
            cuisinierEmail.setHint("Ce champ est obligatoire: email");
        }
        String password = cuisinierPassword.getText().toString();
        if (password.isEmpty()) {
            result = false;
            cuisinierPassword.setHint("Ce champ est obligatoire: mot de passe");
        }
        String adresse = cuisinierAdress.getText().toString();
        if (adresse.isEmpty()) {
            result = false;
            cuisinierAdress.setHint("Ce champ est obligatoire: adresse");
        }
        String description = cuisinierDescription.getText().toString();
        if (description.isEmpty()) {
            result = false;
            cuisinierDescription.setHint("Ce champ est obligatoire: Description de soi-même");
        }

        return result;
    }
}