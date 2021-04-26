package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class liste_projets extends AppCompatActivity {

    FirebaseFirestore fStore;
    DrawerLayout drawerLayout;
    ImageButton AddBtn;

    TextView ViewProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_projets);

        //load data instantly here
        /*fStore = FirebaseFirestore.getInstance();
        retrieveProjects();
        setContentView(R.layout.activity_test_projets);*/


        drawerLayout = findViewById(R.id.drawer_layout);
        AddBtn = findViewById(R.id.add);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addProject.class));
            }
        });

        ViewProject = findViewById(R.id.viewProject);
        ViewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Project_details.class));
            }
        });

    }

    public void ClickMenu(View view) {
        MenuNavigationActivity.openDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MenuNavigationActivity.redirectActivity(this,Associations.class);
    }

    public void ClickProjet(View view) {
        MenuNavigationActivity.redirectActivity(this, liste_projets.class);
    }

    public void ClickDonationCall(View view) {
        MenuNavigationActivity.redirectActivity(this, Liste_appeldon.class);
    }

    public void ClickDonCalled(View view) {
        MenuNavigationActivity.redirectActivity(this, TestMenuActivity.class);
    }

    public void ClickDonators(View view) {
        MenuNavigationActivity.redirectActivity(this, Liste_donateurs.class);
    }

    public void ClickLogout(View view) {
        MenuNavigationActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MenuNavigationActivity.closeDrawer(drawerLayout);
    }

    // retrieve data from DB instantly here
    private void retrieveProjects() {
        CollectionReference collectionReference=fStore.collection("projets");
        DocumentReference doc = collectionReference.document("B0a3kOok10dwm53M6Xpk");
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    String titreDB = documentSnapshot.getString("titre");
                    TextView textView = (TextView)findViewById(R.id.titreView);
                    textView.setText(titreDB);
                }
                else { System.out.println("erroooooooooooooooooooor");}
            }
        });
    }
}