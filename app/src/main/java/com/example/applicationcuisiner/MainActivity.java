package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    DatabaseReference databaseReference;
    Button clientRegister;
    Button cuisinierRegister;
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


    }

    /**
     * cette methode va appeler la methode qui verifie si les informations de connexions sont bonnes
     * lorsque le client clique sur le bouton connexion.
     * @param view
     */
    public void onCheckLoginInfo(View view){
        email = (EditText)findViewById(R.id.editText_Email);
        password = (EditText)findViewById(R.id.editText_MDP);
        checkLoginInfo(email.getText().toString(), password.getText().toString());
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
/**
 * Cette methode permet a l'application de verifier les informations
 * de connexion des utilisateurs. Si les informations sont mauvaise on
 * retourne le message "mauvais email ou mot de passe"  Si les informations
 * sont bonnes alors l'application va ouvrir la prochaine page pour
 * l'utilisateur.
* */
    private void checkLoginInfo(String userEmail, String userPassword){
        if((userEmail.equals("test@gmail.com")) && (userPassword.equals("1234"))){ //checks if the login info is valid
            Intent intent = new Intent(MainActivity.this, SecondActivity.class); // lets the user move into the next activity
            startActivity(intent);
        } else{
            info.setText("Mauvais email ou mot de passe"); //tell the user that the login info is wrong
        }

    }




}