package com.example.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.models.Association;
import com.example.donationapp.models.Favorite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Favorites extends Fragment  implements favListAdapter.OnItemClickListener, Serializable {
    private View FavView;
    private RecyclerView myFavList;
    private favListAdapter favListAdapter;
    private List<Favorite> mFavorite;
    private FirebaseFirestore fStore;
    private FirebaseStorage mStorage;
    FirebaseAuth fAuth;
    TextView Name;

    String donID;
    public TextView assoName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FavView = inflater.inflate(R.layout.activity_favorites, container, false);
        //get donater id
        fAuth = FirebaseAuth.getInstance();
        donID = fAuth.getCurrentUser().getUid();
        // get associations list from DB
        fStore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        myFavList = (RecyclerView) FavView.findViewById(R.id.fav_list);
        myFavList.setLayoutManager(new LinearLayoutManager(getContext()));

        mFavorite = new ArrayList<>();
        favListAdapter = new favListAdapter(getActivity(), mFavorite);
        myFavList.setAdapter(favListAdapter);
        favListAdapter.setOnItemClickListener(Favorites.this);

        Task<QuerySnapshot> collectionReference = fStore.collection("favorites").whereEqualTo("idDonater",donID).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Favorite f  = document.toObject(Favorite.class);
                        mFavorite.add(f);
                        String itemId = document.getId();
                        f.setKey(itemId);
                        Log.d("TAG", itemId + " => " + document.getData());
                    }

                    favListAdapter.notifyDataSetChanged();
                    favListAdapter.setOnItemClickListener(Favorites.this);

                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });

        return FavView;
    }

    @Override
    public void onItemClick(int position) {
        

    }


}
