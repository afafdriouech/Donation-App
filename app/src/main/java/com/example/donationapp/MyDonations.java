package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.donationapp.models.Mydonation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyDonations extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore fStore;
    private List<Mydonation> mDonations;
    private FirebaseAuth fAuth;
    MyDonationsAdapter myDonationsAdapter;
    String donaterId;
    String donationAmount;
    String currency;
    String projectId;
    String titre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);
        drawerLayout = findViewById(R.id.drawer_layout_MyDonations);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        donaterId = fAuth.getCurrentUser().getUid();

        mRecyclerView = findViewById(R.id.recycler_view_MyDonations);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDonations = new ArrayList<>();

        myDonationsAdapter = new MyDonationsAdapter(MyDonations.this, mDonations);
        mRecyclerView.setAdapter(myDonationsAdapter);

        // donation amount and currency from donations
        Task<QuerySnapshot> collectionReference = fStore.collection("Donations").whereEqualTo("donaterId",donaterId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Mydonation donation= new Mydonation();

                        donationAmount = document.get("donationAmount").toString();
                        currency = document.get("currency").toString();
                        projectId = document.get("projectId").toString();

                        donation.setAmount(donationAmount);
                        donation.setCurrency(currency);

                        // project title from projets
                        Task<DocumentSnapshot> subCollection = fStore.collection("projets").document(projectId).get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        String assoId= task.getResult().get("idAsso").toString();
                                        titre = task.getResult().get("titre").toString();
                                        donation.setProject(titre);
                                        // association name from association
                                        Task<DocumentSnapshot> subCollection = fStore.collection("associations").document(assoId).get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        String assoName = task.getResult().get("assoName").toString();
                                                        donation.setAssociation(assoName);
                                                        Log.d("TAG", donation.getProject()+ " "+donation.getAmount()+ " "+donation.getAssociation());
                                                        mDonations.add(donation);
                                                        myDonationsAdapter.notifyDataSetChanged();
                                                    }
                                                });
                                    }
                                });
                        //Log.d("TAG", donation.getProject()+ " "+donation.getAmount()+ " "+donation.getAssociation());
                        //Log.d("TAG", donation.getAmount() + " " + donation.getProject());
                        //myDonationsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
               // myDonationsAdapter.notifyDataSetChanged();
            }
        });
    }

    public void ClickMenu(View view) {
        MenuNavigationActivity.openDrawer(drawerLayout);
    }
    public void ClickAssociations(View view){
        MenuNavigationActivity.redirectActivity(this,Associations.class);
    }
    public void clickMyDonations(View view)
    {
        MenuNavigationActivity.redirectActivity(this,MyDonations.class);
    }
    public void ClickLogout(View view){
        MenuNavigationActivity.logout(this);
    }
}