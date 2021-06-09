package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.donationapp.models.Donation;
import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class DonationCalled extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    DrawerLayout drawerLayout;
    private doncalledAdapter doncalledAdapter ;
    private List<Donation> mDonations;
    private FirebaseFirestore fStore;
    private FirebaseStorage mStorage;
    private FirebaseAuth fAuth;
    String assoID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_called);
        drawerLayout = findViewById(R.id.drawer_layout);
        //get asso id
        Intent intent = getIntent();
        assoID = intent.getStringExtra("assoID");
        // get donations list from DB
        fStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mRecyclerView = findViewById(R.id.donCalled_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mProgressCircle = findViewById(R.id.progress_circle);
        mDonations = new ArrayList<>();
        doncalledAdapter= new doncalledAdapter(DonationCalled.this, mDonations);
        mRecyclerView.setAdapter(doncalledAdapter);
        Task<QuerySnapshot> collectionReference=fStore.collection("Donations").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Donation d = document.toObject(Donation.class);
                        mDonations.add(d);
                        String itemId = document.getId();
                        d.setKey(itemId);
                        //Log.d("TAG", itemId + " => " + document.getData());
                    }


                    doncalledAdapter.notifyDataSetChanged();




                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
                doncalledAdapter.notifyDataSetChanged();
            }
        });
    }
    public void ClickMenu(View view) {
        MenuNavigationActivity.openDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        Intent intent = new Intent(getApplicationContext(), Associations.class);

        startActivity(intent);
    }
    public void ClickProjet(View view) {
        //MenuNavigationActivity.redirectActivity(this, ProjectsList.class);
        Intent intent = new Intent(getApplicationContext(),ProjectsList.class);
        intent.putExtra("assoID",assoID);
        startActivity(intent);
    }
    public void ClickDonationCall(View view) {
        MenuNavigationActivity.redirectActivity(this, Liste_appeldon.class);
    }
    public void ClickDonCalled(View view) {

        Intent intent = new Intent(getApplicationContext(),DonationCalled.class);
        intent.putExtra("assoID",assoID);
        startActivity(intent);
    }
    public void ClickDonators(View view){
        MenuNavigationActivity.redirectActivity(this,Liste_donateurs.class);
    }
    public void ClickLogout(View view){
        //MenuNavigationActivity.logout(this);
        MenuNavigationActivity.logout(this);
    }
}