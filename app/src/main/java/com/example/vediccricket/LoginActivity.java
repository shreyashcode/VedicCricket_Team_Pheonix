package com.example.vediccricket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public EditText username;
    public Button login;
    public String name;
    public FirebaseFirestore firebaseFirestore;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // in splash screen, check if this is a new user if not then give intent to this activity else give intent to next activity
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        login.setOnClickListener(v->{
            firebaseFirestore = FirebaseFirestore.getInstance();
            User.name = username.getText().toString();
            editor.putString("userName", User.name);
            editor.apply();

            Map<String, Object> map = new HashMap<>();
            map.put("name", User.name);
            map.put("coins", 0);
            map.put("level", 0);
            firebaseFirestore.collection("user").document(User.name).set(map);
            finish();
            Intent intent = new Intent(LoginActivity.this, practice_activity.class);
            startActivity(intent);
        });
    }
}