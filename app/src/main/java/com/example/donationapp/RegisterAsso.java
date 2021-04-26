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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterAsso extends AppCompatActivity {

    EditText mAssoName,mEmail,mPassword,mAdr,mPhone,mDate,mDescription;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    private ProgressBar progressBar;
    FirebaseFirestore fStore;
    String assoID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_asso);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        mAssoName   = findViewById(R.id.editTxtName);
        mEmail      = findViewById(R.id.editTxtEmail);
        mPassword   = findViewById(R.id.editTxtPassword);
        mAdr        = findViewById(R.id.editTxtAdr);
        mPhone      = findViewById(R.id.PhoneNumber);
        mDate       = findViewById(R.id.Date);
        mDescription= findViewById(R.id.Description);
        mRegisterBtn= findViewById(R.id.btnRegister);
        mLoginBtn   = findViewById(R.id.signin);
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String assoName = mAssoName.getText().toString();
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String adresse = mAdr.getText().toString();
                final String phone = mPhone.getText().toString();
                final String date = mDate.getText().toString();
                final String description = mDescription.getText().toString();

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
                //registration in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(RegisterAsso.this, "user created",Toast.LENGTH_SHORT).show();
                            assoID = fAuth.getCurrentUser().getUid();

                            ///// A revoir
                            DocumentReference documentReference = fStore.collection("associations").document(assoID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("assoName",assoName);
                            user.put("email",email);
                            user.put("password",password);
                            user.put("adresse",adresse);
                            user.put("phone",phone);
                            user.put("dateCreation",date);
                            user.put("description",description);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user Profile is created for "+assoID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(),ProjectsList.class));
                        }
                        else{
                            Toast.makeText(RegisterAsso.this, "Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                    });
            }
        }
        );

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginAsso.class));
            }
        });

    }
}