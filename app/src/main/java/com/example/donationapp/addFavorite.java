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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donationapp.models.Favorite;
import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

public class addFavorite extends AppCompatActivity {

    TextView Name;
    Button favAddBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String idDonator;
    Boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_details);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Name = findViewById(R.id.assotitle);
        favAddBtn = findViewById(R.id.fav_item);
        //handle the already connected user
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),LoginDonater.class));
            finish();
        }

        // submit button listener
        favAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Button was Clicked", Toast.LENGTH_SHORT).show();
                String assoname = Name.getText().toString();

                    //add data in firebase
                    idDonator = fAuth.getCurrentUser().getUid();
                    Favorite favorite = new Favorite( assoname, idDonator);
                Log.d("TAG", "onSuccess: asso added favorites" + assoname + idDonator);
                    CollectionReference collectionReference = fStore.collection("favorites");
                    collectionReference.add(favorite).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TAG", "onSuccess: asso added favorites" + idDonator);
                            //retrieveProjects(assoID);
                            Intent intent = new Intent(getApplicationContext(),Recommendations.class);
                            intent.putExtra("idDonator", idDonator);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "Failed to create project");

                            Intent intent = new Intent(getApplicationContext(),ProjectsList.class);
                            intent.putExtra("idDonator", idDonator);
                            startActivity(intent);


                        }
                    });
                    Toast.makeText(addFavorite.this, "Favorite added successful", Toast.LENGTH_LONG).show();

            }
            });

        }
    }