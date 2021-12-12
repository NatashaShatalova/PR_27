package com.example.taxi;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.taxi.models.Users;

public class registration_taxi extends AppCompatActivity {

    EditText  etLogin, etMail, etPass1, etPass2;
    Button bSignUp;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference usersRef;
    ConstraintLayout root;

    public boolean isEmailValid(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registration_taxi);

        etLogin = findViewById(R.id.editTextLoginReg);
        etMail = findViewById(R.id.editTextEmailReg);
        etPass1 = findViewById(R.id.editTextPassword1Reg);
        etPass2 = findViewById(R.id.editTextPassword2Reg);
        bSignUp = (Button) findViewById(R.id.buttonSignUp);
        root = (ConstraintLayout) findViewById(R.id.root_lay_reg);
        bSignUp.setEnabled(false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        usersRef = db.getReference("Users");

        EditText[] edList = {etLogin, etMail, etPass1, etPass2};
        CustomTextWatcher textWatcher = new CustomTextWatcher(edList, bSignUp);
        for (EditText editText : edList) editText.addTextChangedListener(textWatcher);

        bSignUp.setOnClickListener(v -> {
            if (!isEmailValid(etMail.getText().toString())) {
                Snackbar.make(root, "Проверьте почту", Snackbar.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(etLogin.getText().toString())) {
                Snackbar.make(root, "Введите логин", Snackbar.LENGTH_LONG).show();
                return;
            }

            if (etPass1.getText().toString().length() < 6 && etPass2.getText().length() < 6) {
                Snackbar.make(root, "Введите пароль (>6 символов)", Snackbar.LENGTH_LONG).show();
                return;
            }

            if(!etPass1.getText().toString().equals(etPass2.getText().toString())){
                Snackbar.make(root, "Пароли не совпадают", Snackbar.LENGTH_LONG).show();
                return;
            }

            auth.createUserWithEmailAndPassword(etMail.getText().toString(), etPass1.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Users users = new Users();
                            users.setLogin(etLogin.getText().toString());
                            users.setEmail(etMail.getText().toString());
                            users.setPass(etPass1.getText().toString());

                            usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                    setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Snackbar.make(root, "Регистрация прошла успешно", Snackbar.LENGTH_LONG).show();
                                    Intent i = new Intent(registration_taxi.this, start_screen_taxi.class);
                                    startActivity(i);
                                }
                            });
                        }
                    });
        });
    }
}