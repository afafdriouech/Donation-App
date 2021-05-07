package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donationapp.models.Association;
import com.example.donationapp.models.Comment;
import com.example.donationapp.models.Favorite;
import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class Association_details extends AppCompatActivity implements Serializable {

    TextView Name;
    Button favAddBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String idDonator;
    Boolean checked;


    public TextView adresse,assoName,description;
    public TextView dateCreation;
    public TextView email;
    public TextView password;
    public TextView phone;
    EditText review;
    Button comAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_details);
        // getting buttons ids
        adresse=findViewById(R.id.assoadr);
        assoName=findViewById(R.id.assotitle);
        dateCreation=findViewById(R.id.assodate);
        email=findViewById(R.id.assoem);
        phone=findViewById(R.id.assoph);
        description=findViewById(R.id.assodescrip);


        // getting the selected project from calling intent
        Association selectedAsso=(Association) (getIntent().getSerializableExtra("AssociationClass"));
        //setting the card view with the project infos
        adresse.setText("Adresse: "+selectedAsso.getAdresse());
        assoName.setText(selectedAsso.getAssoName());
        dateCreation.setText("Date lancement: "+selectedAsso.getDateCreation());
        email.setText("Email: "+selectedAsso.getEmail());
        phone.setText("Phone: "+selectedAsso.getPhone());
        description.setText("Description: "+selectedAsso.getDescription());


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        favAddBtn = findViewById(R.id.fav_item);
        //test comment

        review = findViewById(R.id.comreview);
        comAddBtn = findViewById(R.id.com_btn);
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
                String assoname = assoName.getText().toString();

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
                        Intent intent = new Intent(getApplicationContext(),Association_details.class);
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
                Toast.makeText(Association_details.this, "Favorite added successful", Toast.LENGTH_LONG).show();

            }
        });

        //test
        comAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String assoname =assoName.getText().toString();
                final String assoReview =review.getText().toString();
                //add data in firebase
                idDonator = fAuth.getCurrentUser().getUid();
                Comment comment = new Comment( assoname, idDonator,assoReview);
                Log.d("TAG", "onSuccess: Comment  added to db" + assoname + idDonator);
                CollectionReference collectionReference = fStore.collection("comments");
                collectionReference.add(comment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "onSuccess: asso added favorites" + idDonator);
                        //retrieveProjects(assoID);
                        Intent intent = new Intent(getApplicationContext(),Association_details.class);
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
                Toast.makeText(Association_details.this, "Favorite added successful", Toast.LENGTH_LONG).show();

            }
        });

    }

}
