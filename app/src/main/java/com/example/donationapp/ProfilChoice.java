package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProfilChoice extends AppCompatActivity {

    private ImageView mAssoClick;
    private ImageView mDonaterClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_choice);
        mAssoClick=findViewById(R.id.imgAsso);
        mDonaterClick=findViewById(R.id.imgDonater);
        mAssoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginAsso.class));
            }
        });

        mDonaterClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginDonater.class));
            }
        });
    }
}