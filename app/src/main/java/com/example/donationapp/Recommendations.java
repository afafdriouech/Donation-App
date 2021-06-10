package com.example.donationapp;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.donationapp.models.Association;
import com.example.donationapp.models.Projet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kotlin.Result;
import kotlin.jvm.Synchronized;
import kotlinx.coroutines.Dispatchers;

import static kotlinx.coroutines.BuildersKt.withContext;

public class Recommendations  extends Fragment {

    private FirebaseFirestore fStore;
    private List<Association> mAsso;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //fStore = FirebaseFirestore.getInstance();
        //mAsso = new ArrayList<>();
        //downloadModel();
        return inflater.inflate(R.layout.activity_recommendations,container,false);
    }

    /*public void downloadModel()
    {
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("recommendations", DownloadType.LOCAL_MODEL, conditions)
                .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
                    @Override
                    public void onSuccess(CustomModel model) {
                        File modelFile = model.getFile();
                        if (modelFile != null) {
                            Toast.makeText(getContext(),"Failed to get model file.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"Downloaded remote model", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

    /*private List<Association> loadCandidateList() {
        Task<QuerySnapshot> collectionReference = fStore.collection("associations").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Association p = document.toObject(Association.class);
                                mAsso.add(p);
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
                for (Association item:mAsso) {
                    mAsso.set(Integer.parseInt(item.getKey()),item);
                }
        return mAsso;
    }

    @Synchronized
    private String[] preprocess(List<Association> selectedAssociations, int inputLength) {
            String  inputContext[] = new String[inputLength];
            for (int i=0;i<inputLength;i++){
                if (i < selectedAssociations.size()) {
                    String id = selectedAssociations.get(i).getKey();
                    inputContext[i] = id;
                }
        }
        return inputContext;
    }

    @Synchronized
    private List<Results> postprocess(Integer[]
            outputIds, Float[] confidences, List<Association> selectedAssociations) {
            List<Results> results = new ArrayList<Results>();
            preprocess(selectedAssociations,outputIds.length);
            // Add recommendation results. Filter null or contained items.
            for (int i=0;i<outputIds.length;i++) {
                int id = outputIds[i];
                List<Association> candidates= loadCandidateList();
                Association item = candidates.get(id);
                if (selectedAssociations.contains(item)) {
                    Log.v("tag", String.format("Inference output[%d]. Id: %s is contained", i, id));
                    continue;
                }
                Results result= new Results(id, item, confidences);
                results.add(result);
                Log.v("tag", String.format("Inference output[%d]. Result: %s", i, result));
            }
            return results;
        }*/
}
































/*class Results{

    public Results(int id, Association a, Float[] as)
        {

        }
}*/