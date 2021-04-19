package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.donationapp.models.Projet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class addProject extends AppCompatActivity {

    DrawerLayout drawerLayout;

    EditText mTitre,mDateLancement,mDureeRealisation,mDateEcheance,mBudget,mlieu,mAvancement,mDescription;
    Button mAddBtn,mImage;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    StorageReference storageReference;
    String assoID;
    //attachement
    private static final int PICKFILE_RESULT_CODE = 1;
    TextView uploadTxt;
    Uri ImageUri;
    StorageReference sr;
    //StorageTask mUploadTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("projets");

        mTitre   = findViewById(R.id.titre);
        mDateLancement = findViewById(R.id.dateLancement);
        mDureeRealisation   = findViewById(R.id.dureeRealisation);
        mDateEcheance   =   findViewById(R.id.dateEcheance);
        mBudget = findViewById(R.id.budget);
        mlieu = findViewById(R.id.lieu);
        mDescription = findViewById(R.id.description);
        mImage = findViewById(R.id.image);
        mAddBtn = findViewById(R.id.btnAddProj);
        uploadTxt = findViewById(R.id.successOnUpload);
        drawerLayout= findViewById(R.id.drawer_layout);

        //add attachement listener
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        //handle the already connected user
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),LoginAsso.class));
            finish();
        }

        // submit button listener
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titre = mTitre.getText().toString();
                final String dateLancement = mDateLancement.getText().toString();
                final String dureeRealisation = mDureeRealisation.getText().toString();
                final String dateEcheance = mDateEcheance.getText().toString();
                final String budget = mBudget.getText().toString();
                final String lieu = mlieu.getText().toString();
                final String description = mDescription.getText().toString();
                //final String avancement = mAvancement.getText().toString();
                final String avancement = null;

                // STORE IMAGE IN STORAGE
                String imgName=System.currentTimeMillis()+"."+getFileExtension(ImageUri);
                sr=storageReference.child(imgName);
                sr.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(addProject.this,"upload successful",Toast.LENGTH_LONG ).show();

                    }
                });

                final String image = null;
                final String imageURL = sr.getDownloadUrl().toString();


                //add data in firebase
                assoID = fAuth.getCurrentUser().getUid();
                Projet projet = new Projet(titre,dateLancement,dureeRealisation,dateEcheance,budget,lieu,avancement,description,image,imageURL,assoID);
                CollectionReference collectionReference=fStore.collection("projets");
                collectionReference.add(projet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "onSuccess: projet created for association"+assoID);
                        startActivity(new Intent(getApplicationContext(),liste_projets.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Failed to create project");
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

    // image stuff
    private void openFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,PICKFILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();
            //textFile.setText(FilePath);
            uploadTxt.setVisibility(View.VISIBLE);
            System.out.println(ImageUri);
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}