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
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginDonater extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mSignupLink;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String donaterID;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_donater);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mLoginBtn = findViewById(R.id.btnLoginD);
        mEmail      = findViewById(R.id.TxtEmailD);
        mPassword   = findViewById(R.id.TxtPasswdD);
        progressBar = findViewById(R.id.progressBar);
        mSignupLink = findViewById(R.id.signupLinkD);

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

                //donater authentificatiom

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            donaterID = fAuth.getCurrentUser().getUid();
                            Toast.makeText(LoginDonater.this, "signed in successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),AllProjects.class);
                            intent.putExtra("donaterID",donaterID);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LoginDonater.this, "Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterDonater.class));
            }
        });

    }
}