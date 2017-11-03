package com.example.gamze.loginformdeneme01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawer;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView usernameTw;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        uname = preferences.getString("username", "");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.getHeaderView(0);
        View header = navigationView.getHeaderView(0);
        usernameTw = (TextView) header.findViewById(R.id.usernamee);
        usernameTw.setText(uname);
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_post) {
            Intent i1 = new Intent(NavigationDrawerActivity.this, IlanEkleActivity.class);
            startActivity(i1);
        } else if (id == R.id.show_posts) {
            Intent i2 = new Intent(NavigationDrawerActivity.this, ShowPostsActivity.class);
            startActivity(i2);
        } else if(id == R.id.incoming_orders){
            Intent i4 = new Intent(NavigationDrawerActivity.this, ComingOrdersActivity.class);
            startActivity(i4);
        } else if(id == R.id.show_my_posts){
            Intent i4 = new Intent(NavigationDrawerActivity.this, ShowMyPostsActivity.class);
            startActivity(i4);
        }
//        } else if(id == R.id.my_notifications){
//            Intent i5 = new Intent(NavigationDrawerActivity.this, ShowMyNotificationsActivity.class);
//            startActivity(i5);
//        } else if (id == R.id.profile) {
        else if (id == R.id.profile) {
            Intent i3 = new Intent(NavigationDrawerActivity.this, ProfileActivity.class);
            startActivity(i3);
        } else if(id == R.id.logout){

            preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = preferences.edit();
            editor.putBoolean("login", false);
            editor.clear();
            editor.commit();
            finish();
//
//            int pid = android.os.Process.myPid();
//            android.os.Process.killProcess(pid);
//            System.exit(0);
            uname = preferences.getString("username", "");

            Intent intent1 = new Intent(NavigationDrawerActivity.this, Login2Activity.class);
            startActivity(intent1);
            finish();
        }
//        else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
