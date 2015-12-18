package com.mrs.rainbow7music;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String PREF_FILE_NAME="pref_file_name";
    private final String KEY_USER_LEARNED_DRAWER="key_user_learned_drawer";
    private boolean mUserLearnedDrawer;
    private final  String SELECTED_ID="selectedId";
    private int mSelectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if(!didUserSawDrawer()){
            drawer.openDrawer(GravityCompat.START);
            markDrawerSeen();
        }
        else{
            drawer.closeDrawer(GravityCompat.START);
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SetFragment(R.id.framelayout, new AllSongsFragment());
        mSelectedId= savedInstanceState==null? R.id.nav_camera: savedInstanceState.getInt(SELECTED_ID);
        navigate(mSelectedId);
    }

    private void navigate(int id) {


        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
    }


    private void SetFragment(int fragId,Fragment fragment) {
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(fragId,fragment).commit();
    }


    public boolean didUserSawDrawer(){

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mUserLearnedDrawer= sharedPreferences.getBoolean(KEY_USER_LEARNED_DRAWER,false);
        return mUserLearnedDrawer;
    }
    private void markDrawerSeen() {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        mUserLearnedDrawer=true;
        sharedPreferences.edit().putBoolean(KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer).apply();
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
        getMenuInflater().inflate(R.menu.main, menu);
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


        mSelectedId=item.getItemId();
        navigate(mSelectedId);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(SELECTED_ID,mSelectedId);
    }
}
