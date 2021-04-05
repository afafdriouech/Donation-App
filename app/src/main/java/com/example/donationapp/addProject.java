package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;

import com.example.donationapp.models.Projet;

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

import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addProject extends AppCompatActivity {

    DrawerLayout drawerLayout;

    EditText mTitre,mDateLancement,mDureeRealisation,mDateEcheance,mBudget,mlieu,mAvancement,mDescription;
    Button mAddBtn,mImage;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String assoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        drawerLayout = findViewById(R.id.drawer_layout);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mTitre   = findViewById(R.id.titre);
        mDateLancement = findViewById(R.id.dateLancement);
        mDureeRealisation   = findViewById(R.id.dureeRealisation);
        mDateEcheance   =   findViewById(R.id.dateEcheance);
        mBudget = findViewById(R.id.budget);
        mlieu = findViewById(R.id.lieu);
        mDescription = findViewById(R.id.description);
        mImage = findViewById(R.id.image);
        mAddBtn = findViewById(R.id.btnAddProj);

        //handle the already connected user
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),LoginAsso.class));
            finish();
        }

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titre = mTitre.getText().toString();
                final String dateLancement = mDateLancement.getText().toString();
                final String dureeRealisation = mDureeRealisation.getText().toString();
                final String dateEcheance = mDateEcheance.getText().toString();
                final String budget = mBudget.getText().toString();
                final String lieu = mlieu.getText().toString();
                //final String avancement = mAvancement.getText().toString();
                final String description = mDescription.getText().toString();
                final String image = mImage.getText().toString();

                //registration in firebase
                assoID = fAuth.getCurrentUser().getUid();
                /*Projet projet = new Projet(titre,dateLancement,dureeRealisation,dateEcheance,budget,lieu,avancement,description,image,assoID);
                CollectionReference collectionReference=fStore.collection("projets");
                collectionReference.add(projet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "onSuccess: projet created for association"+assoID);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });*/

                Map<String,Object> proj = new HashMap<>();
                proj.put("titre",titre);
                proj.put("description",description);
                proj.put("idAsso",assoID);
                proj.put("DateEcheance",dateEcheance);
                proj.put("DateLancement",dateLancement);
                proj.put("DureeRealisation",dureeRealisation);
                proj.put("avancement",null);
                proj.put("budget",budget);
                proj.put("lieu",lieu);
                proj.put("image",image);
                CollectionReference collectionReference=fStore.collection("projets");
                collectionReference.add(proj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "onSuccess: projet created for association"+assoID);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });

            }
        }
        );
    }

    public void ClickMenu(View view){
        MenuNavigationActivity.openDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickProjet(View view){
        MenuNavigationActivity.redirectActivity(this,liste_projets.class);
    }
}