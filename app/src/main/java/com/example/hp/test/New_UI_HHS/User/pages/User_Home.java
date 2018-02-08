package com.example.hp.test.New_UI_HHS.User.pages;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.About;
import com.example.hp.test.New_UI_HHS.Login.New_login;
import com.example.hp.test.New_UI_HHS.User.fragments.Fragment1;
import com.example.hp.test.New_UI_HHS.User.fragments.Fragment2;
import com.example.hp.test.New_UI_HHS.User.fragments.Fragment3;
import com.example.hp.test.New_UI_HHS.User.menu.DrawerAdapter;
import com.example.hp.test.New_UI_HHS.User.menu.DrawerItem;
import com.example.hp.test.New_UI_HHS.User.menu.SimpleItem;
import com.example.hp.test.New_UI_HHS.User.menu.SpaceItem;
import com.example.hp.test.R;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

/**
 * Created by Saravanan Saru on 9/18/2017.
 */

public class User_Home extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{
    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_EVENTS = 2;


    private String[] screenTitles;
    private Drawable[] screenIcons;

    private TextView toolbar_text;

    private SlidingRootNav slidingRootNav;
    ProgressDialog progressDialog;
    TextView zz1,zz2,zz3;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);

        zz1 = (TextView) findViewById(R.id.zz1);
        zz2 = (TextView) findViewById(R.id.zz2);
        zz3 = (TextView) findViewById(R.id.zz3);
        getSupportActionBar().setTitle("");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        toolbar_text = (TextView) findViewById(R.id.toolbar_text);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.aaa_menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_EVENTS),
                new SpaceItem(48)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                progressDialog.setMessage("Logging out...");
                final iOSDialog iOSDialog = new iOSDialog(User_Home.this);
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
                        Toast.makeText(User_Home.this,"Logged Out!",Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
                        SharedPreferences.Editor sharedEdit=sharedPreferences.edit();
                        sharedEdit.clear();
                        sharedEdit.commit();;

                        Intent i = new Intent(User_Home.this, New_login.class);
                        startActivity(i);
                        progressDialog.dismiss();
                        User_Home.this.finish();
                        iOSDialog.dismiss();
                    }
                });
                iOSDialog.show();
                return true;

            case R.id.admin:
                    startActivity(new Intent(User_Home.this,About.class));
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(int position) {
        Fragment selectedScreen = null;
        if(position == POS_DASHBOARD)
        {
            toolbar_text.setText(R.string.dashboard);
            selectedScreen = new Fragment1();
        }
        else if(position == POS_ACCOUNT)
        {
            toolbar_text.setText(R.string.myprofile);
            selectedScreen = new Fragment2();
        }
        else if(position == POS_EVENTS)
        {
            toolbar_text.setText(R.string.events);
            selectedScreen = new Fragment3();
        }

        slidingRootNav.closeMenu();
        //Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
        showFragment(selectedScreen);
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    /*private void changeFragment(Fragment targetFragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }*/


    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.colorPrimary))
                .withTextTint(color(R.color.colorPrimary))
                .withSelectedIconTint(color(R.color.colorPrimary))
                .withSelectedTextTint(color(R.color.colorPrimary));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


}
