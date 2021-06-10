package com.example.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Donater;
import com.example.donationapp.models.Favorite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.donationapp.models.Association;
import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.analytics.FirebaseAnalytics;


public class Liste_associations  extends Fragment  implements assoListAdapter.OnItemClickListener, Serializable {

    private FirebaseAnalytics mFirebaseAnalytics;

    private View AssociationsView;
    private RecyclerView myAssociationsList;
    private assoListAdapter assoListAdapter;
    private List<Association> mAssociation;
    private FirebaseFirestore fStore;
    private FirebaseStorage mStorage;
    FirebaseAuth fAuth;
    TextView Name;
    Button favAddBtn;
    String idDonator;
    public TextView assoName;
    ImageButton fav;

    Donater donater;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity().getApplicationContext());

        AssociationsView =  inflater.inflate(R.layout.activity_liste_associations,container,false);
        // get associations list from DB
        fStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        myAssociationsList= (RecyclerView) AssociationsView.findViewById(R.id.asso_list);
        myAssociationsList.setLayoutManager(new LinearLayoutManager(getContext()));

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
                    assoListAdapter.setOnItemClickListener(Liste_associations.this);

                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });

        return AssociationsView;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
        Association selectedItem = mAssociation.get(position);
        Intent intent = new Intent(getActivity(),Association_details.class);
        intent.putExtra("AssociationClass", (Serializable) selectedItem);
        startActivity(intent);

    }

    @Override
    public void addFavClick(int position) {
        Association selectedItem = mAssociation.get(position);
        final String assoTitle= selectedItem.getAssoName();
        final String selectedKey = selectedItem.getKey();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //handle the already connected user
        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getActivity().getApplicationContext(),LoginDonater.class));
            //finish();
        }

        logAnalyticsEvent(selectedKey,assoTitle);
        //add data in firebase
                idDonator = fAuth.getCurrentUser().getUid();
                Favorite favorite = new Favorite(assoTitle, idDonator, selectedKey);
                CollectionReference collectionReference = fStore.collection("favorites");
                collectionReference.add(favorite).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "onSuccess: asso added favorites"+ assoTitle + idDonator);

                        Intent intent = new Intent(getActivity().getApplicationContext(),Associations.class);
                        intent.putExtra("idDonator", idDonator);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Failed to create project");
                        Toast.makeText(getActivity(), "Favorite added successful"+assoTitle, Toast.LENGTH_LONG).show();



                    }
                });
                //Toast.makeText(addFavorite.this, "Favorite added successful", Toast.LENGTH_LONG).show();

            }


    ///test duplication
   /* FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    Task<QuerySnapshot> taskRef  = fStore.collection("favorites").whereEqualTo("idDonater", idDonator).
            get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    if (document.exists()) {
                        Log.d("TAG", "name already exists");
                        Toast.makeText(getActivity(), "name already existssssssssssss", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("TAG", "newwwww name");
                        Toast.makeText(getActivity(), "newwwww name", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        }
    });*/
    public void logAnalyticsEvent(String assoId, String assoName) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, assoId);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, assoName);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle);
    }



    @Override
    public void onDeleteClick(int position) {

    }
}