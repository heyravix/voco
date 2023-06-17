package com.ravix.videocallui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    private EditText editTextName;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        editTextName = findViewById(R.id.editTextName);
        sharedPrefManager = new SharedPrefManager(this);

        // Check if the username is already saved
        String username = sharedPrefManager.getUsername();
        if (username != null) {
            // If username exists, directly navigate to the main page
            startMainPage();
        }
    }

    public void saveName(View view) {
        String username = editTextName.getText().toString().trim();
        if (!username.isEmpty()) {
            sharedPrefManager.saveUsername(username);
            startMainPage();
        }
    }

    private void startMainPage() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
