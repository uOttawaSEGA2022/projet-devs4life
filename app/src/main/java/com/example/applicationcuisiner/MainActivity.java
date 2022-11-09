package com.example.applicationcuisiner;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Cette classe contient la page d'ouverture de application.
 * Elle permet a l'utilisateur de passer aux prochaines page de l'application
 * via son adresse Email et un mot de passe,
 * si ceux-ci sont enregistrés dans la base de données Firebase.
 * Elle donne aussi l'option a l'utilisateur de s'inscrire en tant que client ou cuisinier.
 *
 * @author Chloé Al-Frenn
 * @author Carolina González
 * @author Arina Burlac
 * @author Damien Oportus
 *
 */
public class MainActivity extends AppCompatActivity {

    private TextView info;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fireStore;
    private String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info = findViewById(R.id.textView_Info);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        firebaseAuth = FirebaseAuth.getInstance();

    }

    /**
     * Cette methode s'assure que l'utilisateur à entré des informations dans les deux fields
     * "Email" et "Mot de passe". Si c'est le cas elle va appeler la methode checkLoginInfo
     * qui va verifier les informations de connection du client.
     * @param view view of the button
     */
    public void onCheckLoginInfo(View view){

        EditText email = findViewById(R.id.editText_Email);
        EditText password = findViewById(R.id.editText_MDP);
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(this,"Can't leave the fields empty",Toast.LENGTH_LONG).show();
        }
        else {
            checkLoginInfo(email.getText().toString(), password.getText().toString());
        }
    }

    /**
     * Cette methode verifies que l'email et le mot de passe existent
     * dans la base de données et sont associés a un utilisateur
     * et finalement elle cherche le type de l'utilisateur a l'aide de FireStore.
     * @param userEmail email de l'utilisateur qui se connecte
     * @param userPassword mot de passe de l'utilisateur qui se connecte
     */
    private void checkLoginInfo(String userEmail, String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    fireStore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = fireStore.collection("user").document(user.getUid());

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    System.out.println("here3 " + document.getString("Type"));
                                    type = document.getString("Type");
                                    System.out.println("here4" + type);
                                    if(type != null && type.equals("Admin")){
                                        AdminPage(user);
                                        System.out.println("this is an admin");
                                    } else if(type != null && type.equals("Cuisinier")){
                                        CuisinierPage(user);
                                        System.out.println("this is a cuisinier");
                                    } else if (type != null && type.equals("Client")){
                                        ClientPage(user);
                                        System.out.println("this is a client");
                                    }
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }
                else {
                    info.setText("Mauvais email ou mot de passe");
                }
            }
        });
    }


    /**
     * cette methode permet a l'administrateur d'aller vers sa page de bienvenue.
     * @param account compte de l'utilisateur qui veut se connecter
     */
    public void AdminPage(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AdminActivity.class));
        }else {
            Toast.makeText(this,"You can't sign in",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * cette methode permet au cuisinier d'aller vers sa page de bienvenue.
     * @param account compte de l'utilisateur qui veut se connecter
     */
    public void CuisinierPage(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, CuisinierActivity.class));
        }else {
            Toast.makeText(this,"You can't sign in",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * cette methode permet au client d'aller vers sa page de bienvenue.
     * @param account compte de l'utilisateur qui veut se connecter
     */
    public void ClientPage(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, ClientActivity.class));
        }else {
            Toast.makeText(this,"You can't sign in",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * cette methode permet au client d'ouvrir la page qui lui permet de s'inscrire
     * @param view view of the button
     */
    public void onClientSignUp(View view){
        Intent intent = new Intent(this, ClientRegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * cette methode permet au cuisinier d'ouvrir la page qui lui permet de s'inscrire
     * @param view view of the button
     */
    public void onCuisinierSignUp(View view){
        Intent intent = new Intent(this, CuisinierRegistrationActivity.class);
        startActivity(intent);
    }


}