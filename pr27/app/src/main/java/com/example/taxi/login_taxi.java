package com.example.taxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taxi.models.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_taxi extends AppCompatActivity {

    EditText etLogin, etPass;
    Button bSignIn;
    TextView tvCreateAcc;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference usersRef;
    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_taxi);

        etLogin = findViewById(R.id.editTextLogin);
        etPass = findViewById(R.id.editTextPassword);
        bSignIn = findViewById(R.id.buttonSignIn);
        tvCreateAcc = findViewById(R.id.textViewCreateAcc);
        root = findViewById(R.id.root_lay);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        usersRef = db.getReference("Users");

        bSignIn.setEnabled(false);
        EditText[] edList = {etLogin, etPass};
        CustomTextWatcher textWatcher = new CustomTextWatcher(edList, bSignIn);
        for (EditText editText : edList) editText.addTextChangedListener(textWatcher);

        tvCreateAcc.setOnClickListener(v -> {
            Intent i = new Intent(login_taxi.this, registration_taxi.class);
            startActivity(i);
        });

        bSignIn.setOnClickListener(v -> {

            if(TextUtils.isEmpty(etLogin.getText().toString())){
                Snackbar.make(root, "Введите логин", Snackbar.LENGTH_LONG).show();
                return;
            }

            if(etPass.getText().toString().length()<6) {
                Snackbar.make(root, "Введите пароль (>6 символов)", Snackbar.LENGTH_LONG).show();
                return;
            }

            auth.signInWithEmailAndPassword(etLogin.getText().toString(), etPass.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent i = new Intent(login_taxi.this, start_screen_taxi.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(root, "Ошибка авторизации " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        });
    }
}

