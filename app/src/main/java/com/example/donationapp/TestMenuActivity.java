package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class TestMenuActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_associations);

        drawerLayout = findViewById(R.id.drawer_layout);
    }
    public void ClickMenu(View view){
        MenuNavigationActivity.openDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickProjet(View view){
        MenuNavigationActivity.redirectActivity(this,ProjectsList.class);
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