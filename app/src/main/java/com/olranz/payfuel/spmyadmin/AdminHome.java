package com.olranz.payfuel.spmyadmin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
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

import config.ClientData;
import fragments.Dipping;
import fragments.ReportMoney;
import fragments.ReportNozzleIndex;
import fragments.SetIndex;
import fragments.Tanking;
import simplebean.loginbean.User;
import simplebean.reportmoneybean.ReportMoneyRequest;
import utilities.SimplePopUp;

public class AdminHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Dipping.OnDippingInteraction, Tanking.OnTankingInteraction,
        SetIndex.OnSetIndexInteraction,
        ReportMoney.OnFragmentReportMoney, ReportNozzleIndex.ReportNozzleIndexInteraction {

    private static final String ARG_SESSION = "sessionData";
    private User user;
    private TextView titleBar;
    private Typeface font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        font = Typeface.createFromAsset(getAssets(), "font/ubuntu.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        titleBar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleBar.setTypeface(font, Typeface.BOLD);
        titleBar.setText("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact Us Under Construction...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.fragment_container) != null){
            if (savedInstanceState != null) {
                Bundle bundle=new Bundle();
                bundle=savedInstanceState.getBundle(ARG_SESSION);
                user=(User) new ClientData().jsonObject(User.class, bundle.getString(ARG_SESSION));
                return;
            }
            Bundle bundle=new Bundle();
            bundle=getIntent().getBundleExtra(ARG_SESSION);//.getBundle(ARG_SESSION);
            user=(User) new ClientData().jsonObject(User.class, bundle.getString(ARG_SESSION));
            if(user == null)
                end();
            // default action is DIPPING_URL
            dippingFrag();
        }
    }

    public void setActivityTitle(String title){
        if (title == null || title.isEmpty()){
            titleBar.setText("Admin Portal");
            return;
        }
        try {
            titleBar.setText(title);
        }catch (Exception e){
            e.printStackTrace();
            titleBar.setText("Admin Portal");
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current state
        savedInstanceState.putParcelable(ARG_SESSION, user);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        user = savedInstanceState.getParcelable(ARG_SESSION);
    }

    @Override
    public void onBackPressed() {
        /*
        Fragment Backstack change listener
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                public void onBackStackChanged() {
                    Log.i("AdminHome", "back stack changed ");
                    int backCount = getSupportFragmentManager().getBackStackEntryCount();
                    if (backCount == 0){
                        // block where back has been pressed. since backstack is zero.
                        end();
                    }
                }
            });
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {

            if (getSupportFragmentManager().getBackStackEntryCount() == 1){
                end();
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_positive, so long
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

        if (id == R.id.tanking) {
            // Handle the taking actions
            tankingFrag();
        } else if (id == R.id.dipping) {
            // Handle the DIPPING_URL actions
            dippingFrag();
        }
//        else if (id == R.id.settings) {
//            // Handle the Settings actions
//        }
        else if (id == R.id.logout) {
            // Handle the logout action
            end();
        }else if (id == R.id.setIndex) {
            // Handle the Settings Index
            setIndexFrag();
        }
//        else if (id == R.id.report) {
//            // Handle the report
//            moneyReport();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDippingInteraction(Object object, int statusCode, String Message) {

    }

    @Override
    public void onDippingChangeTitle(String title) {
        setActivityTitle(title);
    }

    private void end(){
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(IntentCompat.FLAG_ACTIVITY_TASK_ON_HOME | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void dippingFrag(){
        manageFrag(Dipping.newInstance(user.getUserId(), user.getBranchId()), R.id.fragment_container);
    }

    private void setIndexFrag(){
        manageFrag(SetIndex.newInstance(user.getUserId(), user.getBranchId()), R.id.fragment_container);
    }

    private void tankingFrag(){
        manageFrag(Tanking.newInstance(user.getUserId(), user.getBranchId()), R.id.fragment_container);
    }

    private void moneyReport(){
        manageFrag(ReportMoney.newInstance(user.getUserId(), user.getBranchId()), R.id.fragment_container);
    }

    private void nozzleReport(int pumpAgentId, String pumpAgentName, String moneyReport){
        manageFrag(ReportNozzleIndex.newInstance(user.getUserId(), pumpAgentId, pumpAgentName, moneyReport), R.id.fragment_container);
    }

    private void manageFrag(Object object, int fragId){
        Fragment fragment=(Fragment) object;
        String backStateName =  fragment.getClass().getSimpleName();
        String fragmentTag = backStateName;

        boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && getSupportFragmentManager().findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(fragId, fragment, fragmentTag);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void onTankingInteraction(Object object, int statusCode, String message) {

    }

    @Override
    public void onTankingChangeTitle(String title) {
        setActivityTitle(title);
    }

    @Override
    public void onTankingGoToHome(boolean isGoToHome) {
        dippingFrag();
    }

    @Override
    public void onSetIndex(Object object, int statusCode, String message) {
        if(statusCode == 0){
            if(object.getClass().getSimpleName().equals(Dipping.class.getSimpleName()))
                manageFrag(object, R.id.fragment_container);
        }
    }

    @Override
    public void onSetIndexChangeTitle(String title) {
        setActivityTitle(title);
    }

    @Override
    public void onFragmentReportInteraction(Object object, int statusCode, String message) {
        if(statusCode == 0){
            if(object.getClass().getSimpleName().equals(Dipping.class.getSimpleName()))
                manageFrag(object, R.id.fragment_container);
        }
    }

    @Override
    public void onRequestUserNozzles(ReportMoneyRequest reportMoneyRequest, int pumpAgentId, String pumpAgentName, int statusCode, String message) {
        if(reportMoneyRequest.getMoneyReportBeenList().isEmpty()){
            SimplePopUp simplePopUp = new SimplePopUp(AdminHome.this, "Money report is empty. Consider to revise the report");
            simplePopUp.show();
            return;
        }
        //redirect to nozzle report
        nozzleReport(pumpAgentId, pumpAgentName, new ClientData().mapping(reportMoneyRequest));
    }

    @Override
    public void onReportNozzleInteraction(int statusCode, String message, Object object) {

    }

    @Override
    public void onReportNozzleInteractionRequestHome(boolean isRequestedHome) {

    }
}
