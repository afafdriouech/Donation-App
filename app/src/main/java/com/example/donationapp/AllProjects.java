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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllProjects extends AppCompatActivity implements AllProjectsAdapter.OnItemClickListenerD,Serializable {

    DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore fStore;
    private List<Projet> mProjects;
    private AllProjectsAdapter allProjectsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_projects);
        drawerLayout = findViewById(R.id.drawer_layout_AllP);

        fStore = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.recycler_view_allProjects);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mProgressCircle = findViewById(R.id.progress_circle);
        mProjects = new ArrayList<>();
        allProjectsAdapter = new AllProjectsAdapter(AllProjects.this, mProjects);
        mRecyclerView.setAdapter(allProjectsAdapter);

        Task<QuerySnapshot> collectionReference = fStore.collection("projets").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Projet p = document.toObject(Projet.class);
                                mProjects.add(p);
                                String itemId = document.getId();
                                p.setKey(itemId);
                                Log.d("TAG", itemId + " => " + document.getData());
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                        allProjectsAdapter = new AllProjectsAdapter(AllProjects.this, mProjects);
                        mRecyclerView.setAdapter(allProjectsAdapter);
                        allProjectsAdapter.notifyDataSetChanged();
                        allProjectsAdapter.setOnItemClickListener(AllProjects.this);
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        Projet selectedItem = mProjects.get(position);
        Intent intent = new Intent(AllProjects.this,Project_details.class);
        intent.putExtra("ProjetClass", (Serializable) selectedItem);
        startActivity(intent);
    }

    @Override
    public void donationClick(int position) {
        Projet selectedItem = mProjects.get(position);
        Intent intent = new Intent(AllProjects.this,DonationForm.class);
        intent.putExtra("ProjectID", selectedItem.getKey());
        startActivity(intent);
    }

    public void ClickMenu(View view) {
        MenuNavigationActivity.openDrawer(drawerLayout);
    }

    public void ClickProjet(View view){
        MenuNavigationActivity.redirectActivity(this,AllProjects.class);
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