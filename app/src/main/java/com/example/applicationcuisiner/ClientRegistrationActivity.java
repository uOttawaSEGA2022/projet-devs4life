package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * Cette classe permet au client d'entrer toute ses informations d'autentification.
 * Si jamais les champs informations ne sont pas remplis on renvoit le message que le
 * champ est obligatoire. Sinon on va enregistrer les informations dans la base de donnée
 * firebase de plus on donne l'option au client de revenir au menu de connexion.
 * @author Chloé Al-Frenn
 */

public class ClientRegistrationActivity extends AppCompatActivity {

    private EditText clientFirstName, clientLastName, clientEmail, clientPassword, clientAdress, clientCreditNumber, clientCreditExp, clientCreditCVV;
    private Button registerButton;
    private TextView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientregistration);
        assignVariables();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valide()){
                    //upload to the database
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClientRegistrationActivity.this, MainActivity.class)); //redirect the user to the main activity
            }
        });
    }

    private void assignVariables() {
        clientFirstName = (EditText) findViewById(R.id.editText_PrenomClient);
        clientLastName = (EditText) findViewById(R.id.editText_NomClient);
        clientEmail = (EditText) findViewById(R.id.editText_EmailClient);
        clientPassword = (EditText) findViewById(R.id.editText_MdpClient);
        clientAdress = (EditText) findViewById(R.id.editText_AdresseClient);
        clientCreditNumber = (EditText) findViewById(R.id.editText_NumCarteClient);
        clientCreditExp = (EditText) findViewById(R.id.editText_CarteExpClient);
        clientCreditCVV = (EditText) findViewById(R.id.editText_CVVClient);
        registerButton = (Button) findViewById(R.id.button_RegisterClient);
        goBack = (TextView) findViewById(R.id.textView_returnClient);
    }
    /**
     * Cette methode verifie que l'utilisateur a entré des donnees dans tout
     * les edit text present. Sinon on renvoie le message que le champ est obligatoire.
     * Return true seulement si tout les champs sont remplis.
     *
     */

    private boolean valide(){
        Boolean result = true;

        String prenom = clientFirstName.getText().toString();
        if(prenom.isEmpty()){
            result = false;
            clientFirstName.setHint("Ce champ est obligatoire: Prenom");
        }
        String nom = clientLastName.getText().toString();
        if(nom.isEmpty()){
            result = false;
            clientLastName.setHint("Ce champ est obligatoire: nom");
        }
        String email = clientEmail.getText().toString();
        if(email.isEmpty()){
            result = false;
            clientEmail.setHint("Ce champ est obligatoire: email");
        }
        String password = clientPassword.getText().toString();
        if(password.isEmpty()){
            result = false;
            clientPassword.setHint("Ce champ est obligatoire: mot de passe");
        }
        String adresse = clientAdress.getText().toString();
        if(adresse.isEmpty()){
            result = false;
            clientAdress.setHint("Ce champ est obligatoire: adresse");
        }
        String numCarte = clientCreditNumber.getText().toString();
        if(numCarte.isEmpty()){
            result=false;
            clientCreditNumber.setHint("Ce champ est obligatoire: numero de carte de credit");
        }
        String expCarte = clientCreditExp.getText().toString();
        if(expCarte.isEmpty()){
            result= false;
            clientCreditExp.setHint("Ce champ est obligatoire: date d'expiration de carte de credit");
        } String cvvCarte = clientCreditCVV.getText().toString();
        if(cvvCarte.isEmpty()){
            result = false;
            clientCreditCVV.setHint("Ce champ est obligatoire: cvv de carte de credit");
        }

        return result;
    }



}