package com.example.taxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class history_taxi extends AppCompatActivity {

    ImageView ivMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_history_taxi);

        ivMenu = findViewById(R.id.imageViewMenuHistory);

        ivMenu.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}