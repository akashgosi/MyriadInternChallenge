package com.akash.gosi.myriadinternchallenge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;


import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit.RestAdapter;


public class ShowKingdomsActivity extends ActionBarActivity {


    // UI references.
    private RecyclerView mRecyclerView;
    private View mProgressView;
    private View mKingdomsView;

    //Kingdoms List
    List<Kingdoms> kingdoms = null;

    //Recycler view references for navigation bar
    RecyclerView mSlidingRecyclerView;
    RecyclerView.Adapter mSlidingAdapter;
    RecyclerView.LayoutManager mSlidingLayoutManager;
    ActionBarDrawerToggle mDrawerToggle;




    //User Info variables
    String userEmail;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences login = getSharedPreferences("userInfo",0);

        //Titles and icons for the navigation bar
        String TITLES[] = {getResources().getText(R.string.saved_quests_label).toString()};
        int ICONS[] = {R.mipmap.ic_saved};

        userEmail = login.getString("Email","");
        userName = login.getString("Name","Name not available");
        setContentView(R.layout.activity_show_kingdoms);

        //Set the toolbar as the Action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(userEmail);

        //Set the Recycler View to show the kingdoms
        mRecyclerView = (RecyclerView) findViewById(R.id.kingdom_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //To make the spining loader before loading the kingdoms
        mKingdomsView = mRecyclerView;
        mProgressView = findViewById(R.id.kingdom_progress);

        //Start the Async task to get kingdoms
        showProgress(true);
        ShowKingdomTask mKingdomTask = new ShowKingdomTask();
        mKingdomTask.execute((Void) null);



        //Create a menu item object
        MenuItems menuItems = new MenuItems(TITLES,ICONS,userName,userEmail);
        //Sliding Drawer
        //Set the Recycler View to show the list of rows on the navigation drawer
        mSlidingRecyclerView = (RecyclerView) findViewById(R.id.sliding_recycler_view);
        mSlidingRecyclerView.setHasFixedSize(true);
        mSlidingAdapter = new SlidingAdapter(menuItems);
        mSlidingRecyclerView.setAdapter(mSlidingAdapter);
        mSlidingLayoutManager = new LinearLayoutManager(this);
        mSlidingRecyclerView.setLayoutManager(mSlidingLayoutManager);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.sliding_bar);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };
        // Drawer Toggle Object Made
        mDrawerLayout.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                Util.logOut(ShowKingdomsActivity.this);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /*
     * Represents an asynchronous task to get the list of kingdoms
     */
    public class ShowKingdomTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            //load the kingdoms from the api

            try {
                //Get the subscription from Myriad web api
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("https://challenge2015.myriadapps.com")
                        .build();

                MyriadService service = restAdapter.create(MyriadService.class);
                kingdoms = service.getKingdoms();
                if(kingdoms.isEmpty()){
                    throw new InterruptedException() ;
                }
            } catch (Exception e) {

                System.out.println("error");
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            showProgress(false);

            if (success) {

                // specify an adapter (see also next example)
                RecyclerView.Adapter mAdapter = new KingdomAdapter(kingdoms);
                mRecyclerView.setAdapter(mAdapter);

            } else {
                // Creating alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ShowKingdomsActivity.this, R.style.Platform_AppCompat_Light_Dialog));
                builder.setMessage(R.string.dialog_no_internet_message)
                        .setTitle(R.string.dialog_error_title)
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                //Maybe load cache content??
                                //Exit the app...
                                Toast.makeText(ShowKingdomsActivity.this,"You can check your saved quests though",Toast.LENGTH_LONG).show();
                                Intent savedQuestsIntent = new Intent(ShowKingdomsActivity.this,SavedQuestsActivity.class);
                                startActivity(savedQuestsIntent);
                            }
                        });;
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        }

        @Override
        protected void onCancelled() {

            showProgress(false);
        }


    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mKingdomsView.setVisibility(show ? View.GONE : View.VISIBLE);
            mKingdomsView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mKingdomsView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mKingdomsView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
