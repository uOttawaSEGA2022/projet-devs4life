package com.example.applicationcuisiner;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CuisinierTemporarilyBannedActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore;
    private String currentUserID;
    TextView countdownText;
    long duration; //timer duration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_temporarily_banned);

        countdownText = findViewById(R.id.tv_coutdown);

        duration = TimeUnit.MINUTES.toMillis(1);

        long timeBannedInMili = 2000000;

        new CountDownTimer(timeBannedInMili, 1000) {

                public void onTick(long millisUntilFinished) {
                    countdownText.setText("Time left for the ban: " + String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                public void onFinish() {
                    countdownText.setText("done!");
                }
            }.start();

    }




    /**
     * Lorsque le cuisinier clique sur le bouton il va etre redirectionner vers MainActivity
     * qui est la page de login.
     * @param view view du boutton
     */

    public void onDisconnect(View view){
        Intent intent = new Intent(CuisinierTemporarilyBannedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}