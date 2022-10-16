package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private TextView info;
    private TextView clientSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText)findViewById(R.id.editText_Email);
        password = (EditText)findViewById(R.id.editText_MDP);
        login = (Button)findViewById(R.id.button_Connexion);
        info = (TextView)findViewById(R.id.textView_Info);
        clientSignUp = (TextView)findViewById(R.id.textView_ClientSignUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginInfo(email.getText().toString(), password.getText().toString());
            }
        });

        clientSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ClientRegistrationActivity.class));
            }
        });
    }

    private void checkLoginInfo(String userEmail, String userPassword){
        if((userEmail.equals("test@gmail.com")) && (userPassword.equals("1234"))){ //checks if the login info is valid
            Intent intent = new Intent(MainActivity.this, SecondActivity.class); // lets the user move into the next activity
            startActivity(intent);
        } else{
            info.setText("Mauvais email ou mot de passe"); //tell the user that the login info is wrong
        }

    }
}