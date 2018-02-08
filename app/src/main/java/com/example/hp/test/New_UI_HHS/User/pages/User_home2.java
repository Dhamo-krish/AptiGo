package com.example.hp.test.New_UI_HHS.User.pages;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.Admin.Admin_duration;
import com.example.hp.test.New_UI_HHS.Admin.Admin_results;
import com.example.hp.test.New_UI_HHS.Login.New_login;
import com.example.hp.test.New_UI_HHS.User.fragments.U_Fragment1;
import com.example.hp.test.New_UI_HHS.User.fragments.U_Fragment2;
import com.example.hp.test.New_UI_HHS.User.fragments.U_Fragment3;
import com.example.hp.test.R;
import com.gdacciaro.iOSDialog.iOSDialog;

/**
 * Created by Saravanan Saru on 9/18/2017.
 */

public class User_home2 extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    public FragmentManager fragmentManager=getFragmentManager();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_user_home2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.dashboard)
        {
            fragmentManager.beginTransaction().replace(R.id.frame,new U_Fragment1()).commit();
            toolbar.setTitle("Dashboard");
        }
        if(id==R.id.profile)
        {
            fragmentManager.beginTransaction().replace(R.id.frame,new U_Fragment2()).commit();
            toolbar.setTitle("My Profile");
        }
        if(id==R.id.events)
        {
            fragmentManager.beginTransaction().replace(R.id.frame,new U_Fragment3()).commit();
            toolbar.setTitle("Events");
        }
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        if(id==R.id.logout){
            progressDialog.setMessage("Logging out...");
            final iOSDialog iOSDialog = new iOSDialog(User_home2.this);
            iOSDialog.setTitle( "Logout");
            iOSDialog.setSubtitle("Are you sure you want to logout?");
            iOSDialog.setNegativeLabel("No");
            iOSDialog.setPositiveLabel("Yes");
            iOSDialog.setBoldPositiveLabel(true);
            iOSDialog.setNegativeListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOSDialog.dismiss();
                }
            });
            iOSDialog.setPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    Toast.makeText(User_home2.this,"Logged Out!",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                    SharedPreferences.Editor sharedEdit=sharedPreferences.edit();
                    sharedEdit.clear();
                    sharedEdit.commit();

                    Intent i = new Intent(User_home2.this, New_login.class);
                    startActivity(i);
                    progressDialog.dismiss();
                    User_home2.this.finish();
                    iOSDialog.dismiss();
                }
            });
            iOSDialog.show();
//            startActivity(new Intent(User_home2.this,New_login.class));
//            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
