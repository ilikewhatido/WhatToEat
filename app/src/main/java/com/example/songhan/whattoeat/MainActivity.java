package com.example.songhan.whattoeat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.songhan.whattoeat.database.DatabaseAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String HOME_FRAGMENT_TAG = "home";
    private static final String RESTAURANT_FRAGMENT_TAG = "restaurant";
    private static final String CIRCLE_FRAGMENT_TAG = "circle";
    private static final String ADD_RESTAURANT_DIALOG_TAG = "add_restaurant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRestaurantDialog dialog = new AddRestaurantDialog();
                dialog.show(getFragmentManager(), ADD_RESTAURANT_DIALOG_TAG);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set Home page
        Fragment home = HomeFragment.newInstance(this);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, home).commit();
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
        FragmentManager manager = getSupportFragmentManager();

        while(manager.popBackStackImmediate());

        int id = item.getItemId();
        if(id == R.id.nav_home) {
            frag = HomeFragment.newInstance(this);
            tag = HOME_FRAGMENT_TAG;
        } else if(id == R.id.nav_restaurants) {
            frag =RestaurantFragment.newInstance(this);
            tag = RESTAURANT_FRAGMENT_TAG;
        } else if(id == R.id.nav_circles) {
            frag =CircleFragment.newInstance(this);
            tag = CIRCLE_FRAGMENT_TAG;
        }

        if(tag == HOME_FRAGMENT_TAG) {
            manager.beginTransaction().replace(R.id.content_frame, frag).commit();
        } else {
            manager.beginTransaction().addToBackStack(tag).replace(R.id.content_frame, frag).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawers();

        return true;
    }


}
