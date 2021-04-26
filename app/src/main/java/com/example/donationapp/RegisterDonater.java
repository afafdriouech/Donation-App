package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donationapp.models.Donater;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterDonater extends AppCompatActivity {


    EditText mDonaterName,mEmail,mPassword,mAdr,mPhone,mDate;
    Button mRegisterBtn;
    TextView mLoginBtn;
    private ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String donaterID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donater);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mDonaterName   = findViewById(R.id.editTxtNameD);
        mEmail      = findViewById(R.id.editTxtEmailD);
        mPassword   = findViewById(R.id.editTxtPasswordD);
        mAdr        = findViewById(R.id.editTxtAdrD);
        mPhone      = findViewById(R.id.PhoneNumberD);
        mRegisterBtn= findViewById(R.id.btnRegisterD);
        mLoginBtn   = findViewById(R.id.signinD);
        progressBar = findViewById(R.id.progressBar);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String DonaterName = mDonaterName.getText().toString();
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String adresse = mAdr.getText().toString();
                final String phone = mPhone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be > 5 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //registration in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            donaterID = fAuth.getCurrentUser().getUid();
                            Donater donater= new Donater(DonaterName,email,password,adresse,phone);
                            DocumentReference documentReference = fStore.collection("donaters").document(donaterID);
                            documentReference.set(donater).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user Profile is created for donater"+donaterID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Associations.class));
                        }
                        else {
                            Toast.makeText(RegisterDonater.this, "Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}