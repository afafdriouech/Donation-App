package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateProjet extends AppCompatActivity {
    EditText mTitre,mDateLancement,mDureeRealisation,mDateEcheance,mBudget,mlieu,mAvancement,mDescription,midAsso,mImage;

    Button UpdBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    //attachement
    StorageReference storageReference;
    StorageReference sr;
    private static final int PICKFILE_RESULT_CODE = 1;
    TextView uploadTxt;
    Uri ImageUri;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_projet);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("projets");
        //get item content on form
        mTitre   = findViewById(R.id.titre);
        mDateLancement = findViewById(R.id.dateLancement);
        mDureeRealisation   = findViewById(R.id.dureeRealisation);
        mDateEcheance   =   findViewById(R.id.dateEcheance);
        mBudget = findViewById(R.id.budget);
        mlieu = findViewById(R.id.lieu);
        mDescription = findViewById(R.id.description);

        midAsso = findViewById(R.id.idasso);

        UpdBtn = findViewById(R.id.btnAddProj);
        Projet selecteditem=(Projet) (getIntent().getSerializableExtra("ProjClass"));
        mTitre.setText(selecteditem.getTitre());
        mDateLancement.setText(selecteditem.getDateLancement());
        mDureeRealisation.setText(selecteditem.getDureeRealisation());
        mDateEcheance.setText(selecteditem.getDateEcheance()  );
        mBudget.setText(selecteditem.getBudget() );
        mlieu.setText(selecteditem.getLieu()  );
        mDescription.setText(selecteditem.getDescription() );
        midAsso.setText(selecteditem.getIdAsso() );
        mImage = findViewById(R.id.image);

        // submit button listener
        UpdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Titre = mTitre.getText().toString();
                final String DateLancement = mDateLancement.getText().toString();
                final String DureeRealisation =  mDureeRealisation.getText().toString();
                final String DateEcheance = mDateEcheance.getText().toString();
                final String  Budget= mBudget.getText().toString();
                final String lieu = mlieu.getText().toString();
                final String Description = mDescription.getText().toString();

                final String idAsso = midAsso.getText().toString();

                //update data in firebase
                final String selectedKey = selecteditem.getKey();
                fStore = FirebaseFirestore.getInstance();
                Projet projetselected= new Projet(Titre, DateLancement, DureeRealisation, DateEcheance, Budget,  lieu,   Description, idAsso);
                fStore.collection("projets").document(selectedKey)
                        .set(projetselected)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void avoid) {

                                Intent intent = new Intent(getApplicationContext(),ProjectsList.class);
                                intent.putExtra("assoID",idAsso);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Failed to update");
                    }
                });

                Toast.makeText(UpdateProjet.this, "upload successful", Toast.LENGTH_LONG).show();
            }

        });

    }
}