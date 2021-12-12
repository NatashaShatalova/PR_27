package com.example.taxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class settings_taxi extends AppCompatActivity {

    ImageView ivMenu;
    TextView tvExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings_taxi);

        ivMenu = findViewById(R.id.imageViewMenuSettings);
        tvExit = findViewById(R.id.textViewSettingsExit);

        ivMenu.setOnClickListener(v -> {
            onBackPressed();
        });

        tvExit.setOnClickListener(v -> {
            Intent i = new Intent(settings_taxi.this, login_taxi.class);
            startActivity(i);
        });
    }
}