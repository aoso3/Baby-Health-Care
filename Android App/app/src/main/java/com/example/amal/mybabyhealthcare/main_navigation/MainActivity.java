package com.example.amal.mybabyhealthcare.main_navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amal.mybabyhealthcare.R;
import com.example.amal.mybabyhealthcare.account.LoginActivity;
import com.example.amal.mybabyhealthcare.baby.NewBabyActivity;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import Helpers.ImagesManager;
import gcm.RegisterationService;
import pref_managers.UserSessionManager;
import server_side.AppConfig;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    Fragment fragment;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewBabyActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username_nav_bar);
        TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email_nav_bar);
        ImageView image = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_user_img);
        userSessionManager = new UserSessionManager(this);
        if(userSessionManager.getUserDetails().pic != null && !userSessionManager.getUserDetails().pic.isEmpty())
            ImagesManager.loadNormalImageView(AppConfig.URL_USER_AVATAR + userSessionManager.getUserDetails().pic, image);

        email.setText(userSessionManager.getUserDetails().getEmail());
        name.setText(userSessionManager.getUserDetails().getFirst_name() + " " + userSessionManager.getUserDetails().getLast_name());
        ///
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new MainProfile());
        ft.commit();

        ///
        Intent ii = new Intent(getApplicationContext(), RegisterationService.class);
        startService(ii);
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

    ////image crop if fragment is parent profile
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if(fragment instanceof ParentProfileFragment) {
            ParentProfileFragment fragment2 = (ParentProfileFragment) fragment;
            if (resultCode == RESULT_OK) {
                fragment2.image_uri = Crop.getOutput(result);
                fragment2.image.setImageURI(fragment2.image_uri);
                fragment2.image_changed = true;
            } else if (resultCode == Crop.RESULT_ERROR) {
                Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    /////

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            fragment = new ParentProfileFragment();
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_home) {
            fragment = new MainProfile();
            fab.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_logout) {
            UserSessionManager session = new UserSessionManager(MainActivity.this);
            session.logoutUser();

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_password) {
            fragment = new ChangePasswordFragment();
            fab.setVisibility(View.GONE);
        }
//        }else if (id == R.id.nav_adv) {
//            fragment = new VaccinationsAdvNotificationsFragment();
//            fab.setVisibility(View.GONE);
//        }else if (id == R.id.nav_req) {
//            fragment = new VaccinationConfirmationReqNotificationsFragment();
//            fab.setVisibility(View.GONE);
//        }


        if(fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
