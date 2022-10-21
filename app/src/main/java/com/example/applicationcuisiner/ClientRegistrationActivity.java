package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe permet au client d'entrer toute ses informations d'autentification.
 * Si jamais les champs informations ne sont pas remplis on renvoit le message que le
 * champ est obligatoire. Sinon on va enregistrer les informations dans la base de donnée
 * firebase de plus on donne l'option au client de revenir au menu de connexion.
 * @author Chloé Al-Frenn
 * @author Carolina González
 */

public class ClientRegistrationActivity extends AppCompatActivity {

    private Client client;
    private EditText clientFirstName, clientLastName, clientEmail, clientPassword, clientAddress, clientCreditNumber, clientCreditExp, clientCreditCVV;
    private Button registerClient;
    private FirebaseAuth authentication;
    private FirebaseFirestore store;
    private String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientregistration);


        authentication=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();

        clientFirstName = (EditText) findViewById(R.id.editText_PrenomClient);
        clientLastName = (EditText) findViewById(R.id.editText_NomClient);
        clientEmail = (EditText) findViewById(R.id.editText_EmailClient);
        clientPassword = (EditText) findViewById(R.id.editText_MdpClient);
        clientAddress = (EditText) findViewById(R.id.editText_AdresseClient);
        clientCreditNumber = (EditText) findViewById(R.id.editText_NumCarteClient);
        clientCreditExp = (EditText) findViewById(R.id.editText_CarteExpClient);
        clientCreditCVV = (EditText) findViewById(R.id.editText_CVVClient);
        type = "Client";


    }

    public void onRegister(View view){
        if(valide()){
            authentication.createUserWithEmailAndPassword(clientEmail.getText().toString(),clientPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                //Store data

                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser clients=authentication.getCurrentUser();
                    DocumentReference collect=store.collection("Clients").document(clients.getUid());
                    Toast.makeText(ClientRegistrationActivity.this,"Votre compte a ete crée",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    Map<String,Object> userInfo=new HashMap<>();
                    userInfo.put("Name",clientFirstName.getText().toString());
                    userInfo.put("LastName",clientLastName.getText().toString());
                    userInfo.put("Email",clientEmail.getText().toString());
                    userInfo.put("Password",clientPassword.getText().toString());
                    userInfo.put("Address",clientAddress.getText().toString());
                    userInfo.put("Card",clientCreditNumber.getText().toString());
                    userInfo.put("Exp",clientCreditExp.getText().toString());
                    userInfo.put("CCV",clientCreditCVV.getText().toString());
                    userInfo.put("Type", type);

                    store.collection("Clients")
                            .add(userInfo)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                    client = new Client(clientFirstName.getText().toString(),clientLastName.getText().toString(),clientEmail.getText().toString(),clientPassword.getText().toString(),clientAddress.getText().toString(),clientCreditNumber.getText().toString(),clientCreditExp.getText().toString(),clientCreditCVV.getText().toString() );

                    finish();//user cannot go back to registration
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ClientRegistrationActivity.this,"Erreur de registre",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    public void onGoBack(View view){
        startActivity(new Intent(ClientRegistrationActivity.this, MainActivity.class));
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
            clientFirstName.setHint("Ce champ est obligatoire : Prenom");
        }
        String nom = clientLastName.getText().toString();
        if(nom.isEmpty()){
            result = false;
            clientLastName.setHint("Ce champ est obligatoire : nom");
        }
        String email = clientEmail.getText().toString();
        if(email.isEmpty()){
            result = false;
            clientEmail.setHint("Ce champ est obligatoire : email");
        }
        String password = clientPassword.getText().toString();
        if(password.isEmpty()){
            result = false;
            clientPassword.setHint("Ce champ est obligatoire : mot de passe");
        }
        String adresse = clientAddress.getText().toString();
        if(adresse.isEmpty()){
            result = false;
            clientAddress.setHint("Ce champ est obligatoire : adresse");
        }
        String numCarte = clientCreditNumber.getText().toString();
        if(numCarte.isEmpty()){
            result=false;
            clientCreditNumber.setHint("Ce champ est obligatoire : numero de carte de credit");
        }
        String expCarte = clientCreditExp.getText().toString();
        if(expCarte.isEmpty()){
            result= false;
            clientCreditExp.setHint("Ce champ est obligatoire : date d'expiration de carte de credit");
        } String cvvCarte = clientCreditCVV.getText().toString();
        if(cvvCarte.isEmpty()){
            result = false;
            clientCreditCVV.setHint("Ce champ est obligatoire : cvv de carte de credit");
        }

        return result;
    }



}