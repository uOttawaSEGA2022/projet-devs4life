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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CuisinierTemporarilyBannedActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore;
    private String currentUserID;
    TextView countdownText;
    long duration; //timer duration
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_temporarily_banned);

        fireStore = FirebaseFirestore.getInstance();
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        countdownText = findViewById(R.id.tv_coutdown);

        duration = TimeUnit.MINUTES.toMillis(1);

         DocumentReference docRef = fireStore.collection("user").document(currentUserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        time = document.getString("Time");
                        long timeBannedInMinutes = Long.parseLong(time);
                        long timeBannedInMili = timeBannedInMinutes * 60000;


                        new CountDownTimer(timeBannedInMili, 1000) {

                            public void onTick(long millisUntilFinished) {
                                countdownText.setText("Time left for the ban: " + String.format("%02d:%02d",
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            }

                            public void onFinish() {
                                countdownText.setText("done!");

                                fireStore.collection("user").document(currentUserID).update("Status", "Active").addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error updating document", e);
                                            }
                                        });

                            }
                        }.start();
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

//        long timeBannedInMili = timeBannedInMinutes * 60000;
//
//
//        new CountDownTimer(timeBannedInMili, 1000) {
//
//                public void onTick(long millisUntilFinished) {
//                    countdownText.setText("Time left for the ban: " + String.format("%02d:%02d",
//                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
//                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
//                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
//                }
//
//                public void onFinish() {
//                    countdownText.setText("done!");
//
//                    fireStore.collection("user").document(currentUserID).update("Status", "Active").addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w(TAG, "Error updating document", e);
//                                }
//                            });
//
//                }
//            }.start();

    }


    /**
     * Lorsque le cuisinier clique sur le bouton il va etre redirectionner vers MainActivity
     * qui est la page de login.
     * @param view view du boutton
     */

    public void OnDisconnectTemporaire(View view){
        Intent intent = new Intent(CuisinierTemporarilyBannedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}