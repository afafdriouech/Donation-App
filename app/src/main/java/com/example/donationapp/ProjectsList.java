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
import android.widget.ImageButton;

import com.example.donationapp.R;
import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProjectsList extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    ImageButton AddBtn;

    private projectsListAdapter projectsListAdapter;
    private List<Projet> mProjects;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);
        drawerLayout = findViewById(R.id.drawer_layout);

        //handle add button
        AddBtn = findViewById(R.id.add);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addProject.class));
            }
        });

        //get asso id
        Intent intent = getIntent();
        String assoID = intent.getStringExtra("assoID");

        // get projects list from DB
        fStore = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mProgressCircle = findViewById(R.id.progress_circle);
        mProjects = new ArrayList<>();
        Task<QuerySnapshot> collectionReference=fStore.collection("projets").
                whereEqualTo("idAsso",assoID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Get the query snapshot from the task result
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot != null) {

                        // Get the projects list from the query snapshot
                        mProjects = querySnapshot.toObjects(Projet.class);
                        projectsListAdapter = new projectsListAdapter(ProjectsList.this, mProjects);
                        mRecyclerView.setAdapter(projectsListAdapter);
                        //mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    public void ClickMenu(View view) {
        MenuNavigationActivity.openDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MenuNavigationActivity.redirectActivity(this,Associations.class);
    }

    public void ClickProjet(View view) {
        MenuNavigationActivity.redirectActivity(this, liste_projets.class);
    }

    public void ClickDonationCall(View view) {
        MenuNavigationActivity.redirectActivity(this, Liste_appeldon.class);
    }

    public void ClickDonCalled(View view) {
        MenuNavigationActivity.redirectActivity(this, TestMenuActivity.class);
    }
}