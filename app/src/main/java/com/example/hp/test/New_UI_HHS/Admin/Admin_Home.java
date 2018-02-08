package com.example.hp.test.New_UI_HHS.Admin;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.About;
import com.example.hp.test.New_UI_HHS.Login.New_login;
import com.example.hp.test.New_UI_HHS.User.pages.User_Home;
import com.example.hp.test.R;
import com.gdacciaro.iOSDialog.iOSDialog;

/**
 * Created by Saravanan Saru on 9/18/2017.
 */

public class Admin_Home extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    public FragmentManager fragmentManager=getFragmentManager();
    ProgressDialog progressDialog;
    LinearLayout content_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_admin_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        content_layout = (LinearLayout) findViewById(R.id.content_layout);

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
        getMenuInflater().inflate(R.menu.admin_nav, menu);
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
        if(id == R.id.action_developer)
        {
            startActivity(new Intent(Admin_Home.this,About.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.createtest)
        {
            content_layout.setVisibility(View.GONE);
            fragmentManager.beginTransaction().replace(R.id.frame,new Admin_duration()).commit();
            toolbar.setTitle("Create test");
        }
        if(id==R.id.results)
        {
            content_layout.setVisibility(View.GONE);
            fragmentManager.beginTransaction().replace(R.id.frame,new Admin_results()).commit();
            toolbar.setTitle("Test Results");
        }
        if(id==R.id.viewdb)
        {
            content_layout.setVisibility(View.GONE);
            fragmentManager.beginTransaction().replace(R.id.frame,new Admin_DB()).commit();
            toolbar.setTitle("Firebase Database");
        }

        if(id==R.id.logout){
            content_layout.setVisibility(View.GONE);
            progressDialog.setMessage("Logging out...");
            final iOSDialog iOSDialog = new iOSDialog(Admin_Home.this);
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
                    Toast.makeText(Admin_Home.this,"Logged Out!",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                    SharedPreferences.Editor sharedEdit=sharedPreferences.edit();
                    sharedEdit.clear();
                    sharedEdit.commit();

                    Intent i = new Intent(Admin_Home.this, New_login.class);
                    startActivity(i);
                    progressDialog.dismiss();
                    Admin_Home.this.finish();
                    iOSDialog.dismiss();
                }
            });
            iOSDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
