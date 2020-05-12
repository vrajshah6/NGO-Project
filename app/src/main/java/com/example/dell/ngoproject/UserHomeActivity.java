package com.example.dell.ngoproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    Fragment fragment=null;
    Toolbar toolbar;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Custom Title");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragment=new FundRaisingFragment();

        commit();

        navigationView.setCheckedItem(R.id.fund_raising);
        toolbar.setTitle("Fund Raising Wall");



    }

    private void commit() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.flDonorHome,fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.fund_raising)
        {
            fragment=new FundRaisingFragment();
            toolbar.setTitle("Fund Raising Wall");
        } 
        else if (id == R.id.nav_directory)
        {
            fragment=new CatagoriesFragment();
            toolbar.setTitle("Catagories fragment");
        }
        else if (id == R.id.nav_report)
        {
            fragment=new NGOReportFragment();
            toolbar.setTitle("Reports");
        } 
        else if (id == R.id.nav_eventwall)
        {
            fragment=new EventWallFragment();
            toolbar.setTitle("Event Wall");
        }
        else if (id == R.id.nav_profile)
        {
            fragment= new DonorProfileFragment();
            toolbar.setTitle("User Profile");
        }
        else if (id == R.id.nav_settings)
        {
            Toast.makeText(getApplicationContext(),"Settings Clicked",Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_logout)
        {
            Session session= new Session(this);
            session.clearData();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        if(fragment!=null)
        {
            commit();
        }

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
