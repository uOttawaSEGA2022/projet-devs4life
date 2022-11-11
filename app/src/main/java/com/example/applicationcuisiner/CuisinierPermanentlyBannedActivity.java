package com.example.applicationcuisiner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * Cette classe est une Activité qui contient la page que les cuisinier
 * qui sont bannis peuvent voir.
 * Elle leur permet seulement de visualiser le message et de se deconnecter.
 *
 * @author Chloé Al-Frenn
 *
 */
public class CuisinierPermanentlyBannedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_permanently_banned);
    }

    /**
     * Lorsque le client clique sur le bouton il va etre redirectionner vers MainActivity
     * qui est la page de login.
     * @param view view du boutton
     */

    public void onDisconnect(View view){
        Intent intent = new Intent(CuisinierPermanentlyBannedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
