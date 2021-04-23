package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class LoginAsso extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mSignupLink;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String assoID;
    private ProgressBar progressBar;

    List<Projet> projectList= new ArrayList<Projet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_asso);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mLoginBtn = findViewById(R.id.btnLogin);
        mEmail      = findViewById(R.id.editTxtEmail);
        mPassword   = findViewById(R.id.editTxtPassword);
        mSignupLink = findViewById(R.id.signupLink);
        progressBar = findViewById(R.id.progressBar);
        //sign in click
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // association authentification
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            assoID = fAuth.getCurrentUser().getUid();
                            Toast.makeText(LoginAsso.this, "signed in successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ProjectsList.class);
                            intent.putExtra("assoID",assoID);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LoginAsso.this, "Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                             progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


                mSignupLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), RegisterAsso.class));
                    }
                });

    }

    // retrieve data from DB
    private void retrieveProjects(String assoID){
        Task<QuerySnapshot> collectionReference=fStore.collection("projets").
                whereEqualTo("idAsso",assoID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Get the query snapshot from the task result
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot != null) {

                        // Get the contact list from the query snapshot
                        projectList = querySnapshot.toObjects(Projet.class);
                    }
                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
    
    // retrieve data from DB
    /*private void retrieveProjects(String assoID){
        Task<QuerySnapshot> collectionReference=fStore.collection("projets").
                whereEqualTo("idAsso",assoID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });*/


        /*CollectionReference collectionReference=fStore.collection("projets");
        DocumentReference doc = collectionReference.document("B0a3kOok10dwm53M6Xpk");
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    String titreDB = documentSnapshot.getString("titre");
                    Intent intent = new Intent(getApplicationContext(),Test_projets.class);
                    intent.putExtra("titre",titreDB);
                    startActivity(intent);
                }
                else { System.out.println("erroooooooooooooooooooor");}
            }
        });*/
    }
}