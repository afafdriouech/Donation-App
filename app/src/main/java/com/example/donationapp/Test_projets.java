package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Test_projets extends AppCompatActivity {

    public TextView mTitre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_projets);
        mTitre = findViewById(R.id.titreView);
        showData();
    }

    private void showData() {
        Intent intent = getIntent();
        String titre = intent.getStringExtra("titre");
        mTitre.setText(titre);
    }
}