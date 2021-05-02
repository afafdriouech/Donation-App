package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.donationapp.models.Donation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DonationForm extends AppCompatActivity {

    DrawerLayout drawerLayout;

    EditText mAmount,mCurrency,mCardNum,mCardDate,mCVC,mCardName;
    Button mDonate;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String projectId, donaterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_form);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mAmount = findViewById(R.id.txtAmount);
        mCurrency = findViewById(R.id.txtCurrency);
        mCardNum = findViewById(R.id.txtCardNum);
        mCardDate = findViewById(R.id.txtCardDate);
        mCVC = findViewById(R.id.txtCVC);
        mCardName = findViewById(R.id.txtCardName);
        mDonate = findViewById(R.id.btnDonate);

        //handle the already connected user
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),ProfilChoice.class));
            finish();
        }

        //get data from the form

        mDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String amount = mAmount.getText().toString();
                final String currency = mCurrency.getText().toString();
                final String cardNum = mCardNum.getText().toString();
                final String cardDate = mCardDate.getText().toString();
                final String cvc = mCVC.getText().toString();
                final String cardName = mCardName.getText().toString();
                //get IDs
                donaterId = fAuth.getCurrentUser().getUid();
                Intent intent = getIntent();
                projectId = intent.getStringExtra("ProjectID");

                // upload data to firestore
                Donation donation = new Donation(projectId,donaterId,amount,currency,cardNum,cardDate,cvc,cardName);
                CollectionReference collectionReference=fStore.collection("Donations");
                collectionReference.add(donation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "Donation for "+projectId+" from: "+donaterId);
                        //retrieveProjects(assoID);
                        Intent intent = new Intent(getApplicationContext(),ThanksPage.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Failed to create donation to DB");
                    }
                });

            }
        });
    }

    public void ClickMenu(View view) {
        MenuNavigationActivity.openDrawer(drawerLayout);
    }
    public void ClickAssociations(View view){
        MenuNavigationActivity.redirectActivity(this,Associations.class);
    }
    public void ClickLogout(View view){
        MenuNavigationActivity.logout(this);
    }

}