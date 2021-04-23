package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;



public class Project_details extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        drawerLayout = findViewById(R.id.drawer_layout);
    }
    public void ClickMenu(View view){
        MenuNavigationActivity.openDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MenuNavigationActivity.redirectActivity(this,Associations.class);
    }
    public void ClickProjet(View view){
        MenuNavigationActivity.redirectActivity(this,liste_projets.class);
    }

    public void ClickDonationCall(View view){
        MenuNavigationActivity.redirectActivity(this,Liste_appeldon.class);
    }
    public void ClickDonCalled(View view){
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickDonators(View view){
        MenuNavigationActivity.redirectActivity(this,Liste_donateurs.class);
    }
    public void ClickLogout(View view){
        MenuNavigationActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MenuNavigationActivity.closeDrawer(drawerLayout);
    }

}
