package com.example.vediccricket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String isLoggedIn;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        isLoggedIn = isLoggedIn();
        Toast.makeText(this, isLoggedIn, Toast.LENGTH_SHORT).show();
        Log.d("USER", isLoggedIn);
        if(isLoggedIn.equals("NA") == true) {
                        new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Log.d("USER_2", User.name+" "+User.coins+" "+User.level);
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }, 1500);
        }
        else{
            setUserStatistics();
//            new Handler().postDelayed(new Runnable(){
//                @Override
//                public void run() {
//                    Log.d("USER_2", User.name+" "+User.coins+" "+User.level);
//                    Intent intent = new Intent(SplashScreen.this, practice_activity.class);
//                    finish();
//                    startActivity(intent);
//                }
//            }, 3000);
        }


    }

    private void setUserStatistics() {
        User.topicsLearned = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("user").document(isLoggedIn)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                          User.name = document.get("name").toString();
                          User.coins = Integer.parseInt(document.get("coins").toString());
                          User.level = Integer.parseInt(document.get("level").toString());
                          Log.d("USER", User.name+" "+User.coins+" "+User.level);
                    }
                } else {
                    Toast.makeText(SplashScreen.this, task.getException()+"", Toast.LENGTH_SHORT).show();
                }
            }
        });

        firebaseFirestore.collection("user").document(isLoggedIn).collection("topics_learned")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                User.topicsLearned.add(document.get("name").toString());
                                Log.d("USER", document.get("name").toString());
                            }
                        }else {
                            Toast.makeText(SplashScreen.this, task.getException()+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnSuccessListener(command -> {
            Log.d("USER_2", User.name+" "+User.coins+" "+User.level);
            Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
            finish();
            startActivity(intent);
        });
    }

    private String isLoggedIn()
    {
        return sharedPreferences.getString("userName", "NA");
    }
}