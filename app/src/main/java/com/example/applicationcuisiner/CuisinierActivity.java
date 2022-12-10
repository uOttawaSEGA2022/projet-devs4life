package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CuisinierActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier);

        Button btnAdd = (Button)findViewById(R.id.btn_addRepas);
        Button btnSeeMenu = (Button) findViewById(R.id.btn_seeMenu);

        View.OnClickListener btnListener = view -> startActivity(new Intent(CuisinierActivity.this, CuisinierAddFoodToMenu.class));
        btnAdd.setOnClickListener(btnListener);


    }


    public void onGoToSeeOrders(View view){
        Intent intent = new Intent(this, Cuisinier_SeeOrdersActivity.class);
        startActivity(intent);
    }

    public void onGoToMenu(View view){
        Intent intent = new Intent(this, Cuisinier_SeeMenuActivity.class);
        startActivity(intent);
    }

    public void OnGoToRepasPropose(View view){
        Intent intent = new Intent(this, Cuisinier_SeeRepasPropActivity.class);
        startActivity(intent);
    }

    public void onDisconnectCuisinier(View view){
        Intent intent = new Intent(CuisinierActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onGoToProfile(View view){
        Intent intent = new Intent(this, CuisinierSeeProfileActivity.class);
        startActivity(intent);
    }
}