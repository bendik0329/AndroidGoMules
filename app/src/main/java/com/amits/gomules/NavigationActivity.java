package com.amits.gomules;

import android.support.v4.app.Fragment;
/*import android.app.FragmentManager;*/
import android.support.v4.app.FragmentManager; //changed it.

import android.support.v4.app.FragmentTransaction;
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

import com.amits.gomules.Utils.SharedPrefUtil;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPref;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPref = getSharedPreferences(SharedPrefUtil.Name,
                this.MODE_PRIVATE);

        // here to code for welcoming user.
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();

        Fragment fWelcome=new WelcomeFragment();
        fragmentTransaction.replace(R.id.fragment_dashboard,fWelcome);
        fragmentTransaction.commit();


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
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();

            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction= fragmentManager.beginTransaction();

            Fragment fWelcome=new WelcomeFragment();
            fragmentTransaction.replace(R.id.fragment_dashboard,fWelcome);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_logout) {

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();

            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
            finish();

        } else if (id == R.id.nav_currentlocation) {

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction= fragmentManager.beginTransaction();

            Fragment fPostARide= new LocationFragment();
            fragmentTransaction.replace(R.id.fragment_dashboard,fPostARide);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_ridepost) {

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction= fragmentManager.beginTransaction();

            Fragment fPostARide=new RideListFragment();
            fragmentTransaction.replace(R.id.fragment_dashboard,fPostARide);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_postaride) {

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction= fragmentManager.beginTransaction();

            Fragment fPostARide=new PostRideFragment();
            fragmentTransaction.replace(R.id.fragment_dashboard,fPostARide);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_ourculture) {

/*            Fragment fAboutUs=new AboutUsFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_dashboard,fAboutUs).commit();*/


            AboutUsFragment fragment = new AboutUsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment_dashboard, fragment)
                    .addToBackStack(null)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
