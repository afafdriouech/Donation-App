package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Registration<FirebaseAuth> extends AppCompatActivity {

    EditText FullName,Email,Password,City,Phone;
    Button RegisterBtn;
    TextView LoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
   // FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
}