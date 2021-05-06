package com.example.donationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.donationapp.R;
import com.example.donationapp.models.Association;
import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ProjectsList extends AppCompatActivity implements projectsListAdapter.OnItemClickListener,Serializable{
    DrawerLayout drawerLayout;
    private RecyclerView mRecyclerView;
    ImageButton AddBtn;

    private projectsListAdapter projectsListAdapter;
    private List<Projet> mProjects;
    private FirebaseFirestore fStore;
    private FirebaseStorage mStorage;
    private FirebaseAuth fAuth;
    String assoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);
        drawerLayout = findViewById(R.id.drawer_layout);
        //fAuth = FirebaseAuth.getInstance();
        //String userId = fAuth.getCurrentUser().getUid();

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
        assoID = intent.getStringExtra("assoID");

        // get projects list from DB
        fStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mProgressCircle = findViewById(R.id.progress_circle);
        mProjects = new ArrayList<>();
        projectsListAdapter = new projectsListAdapter(ProjectsList.this, mProjects);
        mRecyclerView.setAdapter(projectsListAdapter);
        projectsListAdapter.setOnItemClickListener(ProjectsList.this);


        Task<QuerySnapshot> collectionReference=fStore.collection("projets").
                whereEqualTo("idAsso",assoID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Projet p = document.toObject(Projet.class);
                        mProjects.add(p);
                        String itemId = document.getId();
                        p.setKey(itemId);
                        //Log.d("TAG", itemId + " => " + document.getData());
                    }


                    projectsListAdapter.notifyDataSetChanged();


                   // projectsListAdapter = new projectsListAdapter(ProjectsList.this, mProjects);
                    //mRecyclerView.setAdapter(projectsListAdapter);
                    //mProgressCircle.setVisibility(View.INVISIBLE);
                    //projectsListAdapter.setOnItemClickListener(ProjectsList.this);

/////////////////////////////////////////////
                    //methode 1 working
                    // Get the query snapshot from the task result
                    /*QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        // Get the projects list from the query snapshot
                        mProjects = querySnapshot.toObjects(Projet.class);
                        projectsListAdapter = new projectsListAdapter(ProjectsList.this, mProjects);
                        mRecyclerView.setAdapter(projectsListAdapter);
                        projectsListAdapter.setOnItemClickListener(ProjectsList.this);
                        //mProgressCircle.setVisibility(View.INVISIBLE);
                    }*/
                    /*QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        while (querySnapshot.iterator().hasNext()) {
                            Projet projet = querySnapshot.iterator().next().toObject(Projet.class);
                            mProjects.add(projet);
                            String itemId = querySnapshot.iterator().next().getId();
                            projet.setKey(itemId);
                        }
                    }*/

                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
                projectsListAdapter.notifyDataSetChanged();
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
        MenuNavigationActivity.redirectActivity(this, TestMenuActivity.class);
    }
    public void ClickDonators(View view){
        MenuNavigationActivity.redirectActivity(this,Liste_donateurs.class);
    }
    public void ClickLogout(View view){
        //MenuNavigationActivity.logout(this);
        MenuNavigationActivity.logout(this);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        Projet selectedItem = mProjects.get(position);
        Intent intent = new Intent(ProjectsList.this,Project_details.class);
        intent.putExtra("ProjetClass", (Serializable) selectedItem);
        startActivity(intent);
    }

    @Override
    public void onUpdateClick(int position) {
        Toast.makeText(this, "update at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Projet selectedItem = mProjects.get(position);
        final String selectedKey = selectedItem.getKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl().toString());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                fStore.collection("projets").document(selectedKey).delete();
                //projectsListAdapter.notifyDataSetChanged();
                projectsListAdapter.notifyItemChanged(position);
                Toast.makeText(ProjectsList.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

}