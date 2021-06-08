package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.donationapp.models.Projet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateProjet extends AppCompatActivity {
    EditText mTitre,mDateLancement,mDureeRealisation,mDateEcheance,mBudget,mlieu,mAvancement,mDescription;

    Button UpdBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_projet);

        //get item content on form
        mTitre   = findViewById(R.id.titre);
        mDateLancement = findViewById(R.id.dateLancement);
        mDureeRealisation   = findViewById(R.id.dureeRealisation);
        mDateEcheance   =   findViewById(R.id.dateEcheance);
        mBudget = findViewById(R.id.budget);
        mlieu = findViewById(R.id.lieu);
        mDescription = findViewById(R.id.description);

        UpdBtn = findViewById(R.id.btnAddProj);
        Projet selecteditem=(Projet) (getIntent().getSerializableExtra("ProjetClass"));
        mTitre.setText(selecteditem.getTitre());
        mDateLancement.setText(selecteditem.getDateLancement());
        mDureeRealisation.setText(selecteditem.getDureeRealisation());
        mDateEcheance.setText(selecteditem.getDateEcheance()  );
        mBudget.setText(selecteditem.getBudget() );
        mlieu.setText(selecteditem.getLieu()  );
        mDescription.setText(selecteditem.getDescription() );

    }
}