package com.example.donationapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.donationapp.models.Association;
import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Liste_associations  extends Fragment  implements assoListAdapter.OnItemClickListener, Serializable {
    private View AssociationsView;
    private RecyclerView myAssociationsList;

    private assoListAdapter assoListAdapter;

    private List<Association> mAssociation;
    private FirebaseFirestore fStore;
    private FirebaseStorage mStorage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AssociationsView =  inflater.inflate(R.layout.activity_liste_associations,container,false);
// get projects list from DB
        fStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        myAssociationsList= (RecyclerView) AssociationsView.findViewById(R.id.asso_list);
        myAssociationsList.setLayoutManager(new LinearLayoutManager(getContext()));
        //mProgressCircle = findViewById(R.id.progress_circle);
        mAssociation = new ArrayList<>();
        assoListAdapter = new assoListAdapter(getActivity(), mAssociation);
        myAssociationsList.setAdapter(assoListAdapter);
        assoListAdapter.setOnItemClickListener(Liste_associations.this);

        Task<QuerySnapshot> collectionReference=fStore.collection("associations").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Association a = document.toObject(Association.class);
                        mAssociation.add(a);
                        String itemId = document.getId();
                        a.setKey(itemId);
                        Log.d("TAG", itemId + " => " + document.getData());
                    }

                    assoListAdapter.notifyDataSetChanged();

                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });
        return AssociationsView;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onUpdateClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }
}