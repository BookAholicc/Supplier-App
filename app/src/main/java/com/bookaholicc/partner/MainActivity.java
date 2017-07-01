package com.bookaholicc.partner;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bookaholicc.partner.Fragments.BookFragement;
import com.bookaholicc.partner.Fragments.CheckOutFragment;
import com.bookaholicc.partner.Fragments.EarningFragment;
import com.bookaholicc.partner.Fragments.HomeFragement;
import com.bookaholicc.partner.Fragments.NotificationFragment;
import com.bookaholicc.partner.Fragments.ProfileFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnTabSelectListener, OnTabReselectListener, HomeFragement.homeFragmentCallbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showMainFrag();
        setUpBottomar();


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


    private void setUpBottomar() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(this);
        bottomBar.setOnTabReselectListener(this);
        bottomBar.setLongPressHintsEnabled(true);


    }



    private void showMainFrag() {
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.frag_holder_main,new HomeFragement())
                .commit();
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        Fragment mFragment = null;
        String transactionString = "none";
        switch (tabId) {
            case  R.id.home_tab:
                mFragment = new HomeFragement();
                break;
            case R.id.profile_tab:
                mFragment = new ProfileFragment();
                break;
            case R.id.noti_tab :
                mFragment = new NotificationFragment();
                break;
            case R.id.book_tab :
                mFragment = new BookFragement();
                break;
            case R.id.checkout_tab :
                mFragment = new CheckOutFragment();
                break;
        }

        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.frag_holder_main,mFragment,transactionString)
                .addToBackStack(transactionString)
                .commit();


    }

    @Override
    public void onTabReSelected(@IdRes int tabId) {

    }

    @Override
    public void showEarningsPage() {
        EarningFragment mFragment  = new EarningFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder_main,mFragment,"earning")
                .commit();
    }

    @Override
    public void showBooksPage() {
        BookFragement mFragement = new BookFragement();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_holder_main,mFragement,"earning")
                .commit();

    }

    @Override
    public void showTransactions() {

    }
}
