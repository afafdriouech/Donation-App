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

import com.example.donationapp.models.Comment;
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

public class addComment extends AppCompatActivity {

    TextView Assoname, review;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String idDonator;
    Button comAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_details);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Assoname = findViewById(R.id.comAsso);
        review = findViewById(R.id.review);
        comAddBtn = findViewById(R.id.com_item);
        //handle the already connected user
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),LoginDonater.class));
            finish();
        }

        // submit button listener
        comAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String assoname =Assoname.getText().toString();
                String assoReview =review.getText().toString();
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
                Toast.makeText(addComment.this, "Favorite added successful", Toast.LENGTH_LONG).show();

            }
        });

    }
}