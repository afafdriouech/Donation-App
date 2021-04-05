package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAsso extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mSignupLink;
    FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_asso);

        mAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.btnLogin);
        mEmail      = findViewById(R.id.editTxtEmail);
        mPassword   = findViewById(R.id.editTxtPassword);
        mSignupLink = findViewById(R.id.signupLink);
        progressBar = findViewById(R.id.progressBar);
        //sign in click
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // association authentification
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginAsso.this, "signed in successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),liste_projets.class));
                        }
                        else{
                            Toast.makeText(LoginAsso.this, "Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                             progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


                mSignupLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), RegisterAsso.class));
                    }
                });

    }
}