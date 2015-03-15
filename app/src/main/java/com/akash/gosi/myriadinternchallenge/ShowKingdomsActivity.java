package com.akash.gosi.myriadinternchallenge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import java.util.List;

import retrofit.RestAdapter;


public class ShowKingdomsActivity extends ActionBarActivity {

    // UI references.
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mProgressView;
    private View mKingdomsView;

    private ShowKingdomTask mKingdomTask = null;

    //Kingdoms List
    List<Kingdoms> kingdoms = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences login = getSharedPreferences("userInfo",0);
        String userEmail = login.getString("Email","");
        getSupportActionBar().setTitle(userEmail);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        setContentView(R.layout.activity_show_kingdoms);

        mRecyclerView = (RecyclerView) findViewById(R.id.kingdom_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mKingdomsView = mRecyclerView;
        mProgressView = findViewById(R.id.kingdom_progress);

        //Start the Async task to get kingdoms
        showProgress(true);
        mKingdomTask = new ShowKingdomTask();
        mKingdomTask.execute((Void) null);

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
            } catch (InterruptedException e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            showProgress(false);

            if (success) {


                // specify an adapter (see also next example)
                mAdapter = new KingdomAdapter(kingdoms);
                mRecyclerView.setAdapter(mAdapter);

            } else {

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
