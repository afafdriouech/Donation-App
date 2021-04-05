package com.example.donationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MenuNavigationActivity extends AppCompatActivity {

    // Initialisation des variables
    DrawerLayout drawerLayout;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_navigation);
        // Instanciation des variables
        drawerLayout = findViewById(R.id.drawer_layout);
    }
    public void ClickMenu(View view){
        // Ouvrir le drawer du menu
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        // Open drawer Layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        // Fermer le drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        // Fermer le drawer layout
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        // Reecreer l'activite
        recreate();
    }
    public void ClickProjet(View view){

        redirectActivity(this,liste_projets.class);
    }
    public void ClickDonationCall(View view){

        redirectActivity(this,Liste_appeldon.class);
    }
    public void ClickDonCalled(View view){

        redirectActivity(this,TestMenuActivity.class);
    }

    public void ClickDonators(View view){

        //redirectActivity(this,Liste_donateurs.class);
    }
    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                activity.finishAffinity();
                redirectActivity(activity,LoginAsso.class);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity source, Class destination) {
        Intent intent = new Intent(source,destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        source.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Fermer le drawer
        closeDrawer(drawerLayout);
    }
}