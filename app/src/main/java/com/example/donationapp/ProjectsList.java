package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

    private RecyclerView mRecyclerView;
    private projectsListAdapter projectsListAdapter;
    //private CollectionReference mCollectionRef;
    private List<Projet> mProjects;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);

        Intent intent = getIntent();
        String assoID = intent.getStringExtra("assoID");

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
}