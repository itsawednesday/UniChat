package com.mapchat.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.mapchat.R;
import com.mapchat.contact.ContactActivity;
import com.mapchat.contact.RequestReceivedActivity;
import com.mapchat.contact.SearchContactActivity;
import com.mapchat.profile.ProfileActivity;
import com.mapchat.startup.LoginActivity;
import com.mapchat.storage.DataProvider;

public class DashboardActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Menu menu;
    TextView textView;
    private ActionBarDrawerToggle drawerToggle;

    private GoogleMap map;
    /** Sets up what the code will show on creation of the dashboard activity */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.layout);
        navigationView = findViewById(R.id.nvView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Activity act = this;

        /** Find our drawer view */
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close) {
            /** This will close the burger menu when it is activated. */
            public void onDrawerClosed(View view) {
                Toast.makeText(act, "onDrawerClosed", Toast.LENGTH_SHORT).show();

                Toast.makeText(getBaseContext(), "onDrawerClosed", Toast.LENGTH_SHORT).show();

                /**    getActionBar().setTitle(title);
                *   ((FragmentInterface)fragment).showMenuActions(); */


                invalidateOptionsMenu();
            }
            /** This will open the burger menu when it is activated. */
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(act, "onDrawerOpened", Toast.LENGTH_SHORT).show();

                Toast.makeText(getBaseContext(), "onDrawerOpened", Toast.LENGTH_SHORT).show();

                invalidateOptionsMenu();
            }
        };


        // Setup toggle to display hamburger icon

        /** Setup toggle to display hamburger icon w */
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        /** Tie DrawerLayout events to the ActionBarToggle */
        drawerLayout.addDrawerListener(drawerToggle);

        NavigationView navigationView = findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);

        menu = navigationView.getMenu();

        menu.findItem(R.id.nav_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            /** When the "search" button from the menu is pressed the code will switch the activity to the "search" function */
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(act, SearchContactActivity.class);
                act.startActivity(i);
                drawerLayout.closeDrawers();
                return false;
            }
        });

        menu.findItem(R.id.nav_requestreceived).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            /** When the "requests" button from the menu is pressed the code will switch the activity to the "requests" function */
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(act, RequestReceivedActivity.class);
                act.startActivity(i);
                drawerLayout.closeDrawers();
                return false;
            }
        });

        menu.findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            /** When the "logout" button from the menu is pressed the code will switch the activity to the "logout" function */
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int count = getContentResolver().delete(DataProvider.PROFILE_URI, null, null);
                Intent i = new Intent(act, LoginActivity.class);
                act.startActivity(i);
                act.finish();
                drawerLayout.closeDrawers();
                return false;
            }
        });


    }

    /** When the "back" button is pressed the code will return to the previous state   */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /** When the "back" button is pressed the code will return to the previous state   */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
        int id = Item.getItemId();

        switch (id) {
            case R.id.nav_contacts:

                Intent intent = new Intent(DashboardActivity.this, ContactActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_profile:
                Intent pr = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(pr);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    /** The method below navigates the map to the correct location specified by the program, in the final product it would centre on the users location */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng portsUni = new LatLng(50.795291, -1.093834);
        map.addMarker(new MarkerOptions()
                .position(portsUni)
                .title("University of Portsmouth"));
        map.moveCamera(CameraUpdateFactory.newLatLng(portsUni));
    }
}
