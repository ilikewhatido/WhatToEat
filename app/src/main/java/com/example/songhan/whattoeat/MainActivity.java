package com.example.songhan.whattoeat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        // Floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRestaurantDialog dialog = new AddRestaurantDialog();
                dialog.show(getSupportFragmentManager(), AddRestaurantDialog.TAG);
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set Home page
        if (savedInstanceState == null) {
            Fragment home = HomeFragment.newInstance(this);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_frame, home, HomeFragment.TAG).commit();
        }
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

        Fragment frag = null;
        String tag = null;
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();
        while (manager.popBackStackImmediate()) ;
        if (id == R.id.nav_home) {
            tag = HomeFragment.TAG;
            frag = manager.findFragmentByTag(tag);
            if (frag == null)
                frag = HomeFragment.newInstance(this);
        } else if (id == R.id.nav_restaurants) {
            tag = RestaurantFragment.TAG;
            frag = manager.findFragmentByTag(tag);
            if (frag == null)
                frag = RestaurantFragment.newInstance(this);
        } else if (id == R.id.nav_circles) {
            tag = GroupsFragment.TAG;
            frag = manager.findFragmentByTag(tag);
            if (frag == null)
                frag = GroupsFragment.newInstance(this);
        } else {
            return true;
        }

        if (tag == HomeFragment.TAG) {
            manager.beginTransaction().replace(R.id.content_frame, frag, tag).commit();
        } else {
            manager.beginTransaction().addToBackStack(tag).replace(R.id.content_frame, frag, tag).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawers();
        return true;
    }
}
