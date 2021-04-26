package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donationapp.models.Projet;
import com.squareup.picasso.Picasso;

import java.io.Serializable;


public class Project_details extends AppCompatActivity implements Serializable {

    private ImageView mPrjImage;
    private TextView mPrjtitle;
    private TextView mPrjBudget;
    private TextView mPrjDL;
    private TextView mPrjDE;
    private TextView mPrjDescrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        // getting buttons ids
        mPrjImage=findViewById(R.id.PrjImage);
        mPrjtitle=findViewById(R.id.Prjtitle);
        mPrjBudget=findViewById(R.id.PrjBudget);
        mPrjDL=findViewById(R.id.PrjDL);
        mPrjDE=findViewById(R.id.PrjDE);
        mPrjDescrip=findViewById(R.id.PrjDescrip);

        // getting the selected project from calling intent
        Projet selectedProject=(Projet) (getIntent().getSerializableExtra("ProjetClass"));
        //setting the card view with the project infos
        mPrjtitle.setText(selectedProject.getTitre());
        mPrjBudget.setText("Budget: "+selectedProject.getBudget());
        mPrjDL.setText("Date lancement:"+selectedProject.getDateLancement());
        mPrjDE.setText("Date d'écheance:"+selectedProject.getDateEcheance());
        mPrjDescrip.setText(selectedProject.getDescription());
        Picasso.with(this).load(selectedProject.getImageUrl())
                .placeholder(R.mipmap.ic_launcher).into(mPrjImage);
    }

}
