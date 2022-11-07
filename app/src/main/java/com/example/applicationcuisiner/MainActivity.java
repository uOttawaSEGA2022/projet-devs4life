package com.example.applicationcuisiner;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Cette classe contient la page d'ouverture de l'application.
 * Elle permet a l'utilisateur de se connecter a l'application
 * via son adresse Email et un mot de passe.
 * Elle donne aussi l'option a l'utilisateur de s'inscrire en
 * tant que client et cuisinier(pas encore implementé).
 *
 * @author Chloé Al-Frenn
 * @author Carolina González
 * @author Arina Burlac
 */
public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private TextView info;
    private Button clientSignUp;
    private Button cuisinierSignUp;
    private FirebaseAuth firebaseAuth;

    public DatabaseReference databaseReference;
    public Button clientRegister;
    public Button cuisinierRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.button_Connexion);
        info = (TextView)findViewById(R.id.textView_Info);
        clientSignUp = (Button)findViewById(R.id.Button_ClientSignUp);
        cuisinierSignUp = (Button)findViewById(R.id.Button_CuisinierSignUp);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,SecondActivity.class));

        }else {
            Toast.makeText(this,"You can't sign in",Toast.LENGTH_LONG).show();
        }

    }


    /**
     * cette methode va appeler la methode qui verifie si les informations de connexions sont bonnes
     * lorsque le client clique sur le bouton connexion.
     * @param view
     */
    public void onCheckLoginInfo(View view){

        email = (EditText)findViewById(R.id.editText_Email);
        password = (EditText)findViewById(R.id.editText_MDP);
        if(email.getText().toString() == "admin@gmail.com") {
            Intent intent = new Intent(this, Administrateur.class);
            startActivity(intent);


        }
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(this,"Can't leave empty",Toast.LENGTH_LONG).show();
        }
        else if (email.getText().toString()=="admin@gmail.com" ){
            Intent intent = new Intent(this, Administrateur.class);
            startActivity(intent);
        }
        else {
            checkLoginInfo(email.getText().toString(), password.getText().toString());
        }

    }

    /**
     * cette methode permet au client d'ouvrir la page qui lui permet de s'inscrire lorsqu'il clique sur le texte
     * qui lui dit "s'inscrire en tant que client"
     * @param view
     */
    public void onClientSignUp(View view){
        Intent intent = new Intent(this, ClientRegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * cette methode permet au client d'ouvrir la page qui lui permet de s'inscrire lorsqu'il clique sur le texte
     * qui lui dit "s'inscrire en tant que cuisinier"
     * @param view
     */
    public void onCuisinierSignUp(View view){
        Intent intent = new Intent(this, CuisinierRegistrationActivity.class);
        startActivity(intent);
    }
    public void onAdminSignUp(View view){
        Intent intent = new Intent(this, Administrateur.class);
        startActivity(intent);
    }
/**
 * Cette methode permet a l'application de verifier les informations
 * de connexion des utilisateurs. Si les informations sont mauvaise on
 * retourne le message "mauvais email ou mot de passe"  Si les informations
 * sont bonnes alors l'application va ouvrir la prochaine page pour
 * l'utilisateur.
* */
    private void checkLoginInfo(String userEmail, String userPassword){

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                }
                else {
                    info.setText("Mauvais email ou mot de passe");
                    updateUI(null);
                    //tell the user that the login info is wrong
                }
            }
        });


    }






}