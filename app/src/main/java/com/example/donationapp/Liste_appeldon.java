package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Liste_appeldon extends AppCompatActivity {

    DrawerLayout drawerLayout;
    String assoID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_appeldon);

        drawerLayout = findViewById(R.id.drawer_layout);

        Intent intent = getIntent();
        assoID = intent.getStringExtra("assoID");
    }
    public void ClickMenu(View view){
        MenuNavigationActivity.openDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MenuNavigationActivity.redirectActivity(this,Associations.class);
    }
    public void ClickProjet(View view){
        Intent intent = new Intent(getApplicationContext(),ProjectsList.class);
        intent.putExtra("assoID",assoID);
        startActivity(intent);
    }

    public void ClickDonationCall(View view){
        MenuNavigationActivity.redirectActivity(this,Liste_appeldon.class);
    }
    public void ClickDonCalled(View view){
        MenuNavigationActivity.redirectActivity(this,addProject.class);
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