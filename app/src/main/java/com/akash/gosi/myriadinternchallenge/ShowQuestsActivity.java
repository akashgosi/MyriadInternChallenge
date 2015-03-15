package com.akash.gosi.myriadinternchallenge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;


public class ShowQuestsActivity extends FragmentActivity {

    // UI references.
    private RecyclerView.LayoutManager mLayoutManager;
    private View mProgressView;
    private View mQuestsView;


    private ShowQuestsTask mQuestTask = null;

    //Kingdom
    Kingdoms kingdom = null;
    String kingdomId = null;


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    List<Fragment> fragments = null;



    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_quests);

        kingdomId = getIntent().getExtras().getString("kingdomId");
        System.out.println("KINGDOM ID ="+kingdomId);

        //Call the async task
        mQuestTask = new ShowQuestsTask();
        mQuestTask.execute((Void) null);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }



    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        //First add the kingdom information
        fList.add(QuestKingdomSlidePageFragment.newInstanceKingdom(kingdom));

        for(Kingdoms.Quests quest: kingdom.getQuests()){
            fList.add(QuestSlidePageFragment.newInstanceQuest(quest));

        }
        return fList;
    }


    /*
 * Represents an asynchronous task to get the list of kingdoms
 */
    public class ShowQuestsTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            //load the quests in the kingdom from the api

            try {
                //Get the subscription from Myriad web api
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("https://challenge2015.myriadapps.com")
                        .build();

                MyriadService service = restAdapter.create(MyriadService.class);
                kingdom = service.getQuests(kingdomId);
                if(kingdom==null){
                    throw new InterruptedException() ;
                }
            } catch (InterruptedException e) {
                return false;
            }
            //Get the different fragments for the page viewer
            fragments = getFragments();

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            //showProgress(false);

            if (success) {

                // specify an adapter
                System.out.println("Climate " + kingdom.getClimate().toString());
                //TODO: Set the adapter
                // Instantiate a ViewPager and a PagerAdapter.
                mPager = (ViewPager) findViewById(R.id.quest_pager);
                mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                mPager.setAdapter(mPagerAdapter);


            } else {

            }
        }

        @Override
        protected void onCancelled() {

            //showProgress(false);
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

            mQuestsView.setVisibility(show ? View.GONE : View.VISIBLE);
            mQuestsView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mQuestsView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mQuestsView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_quests, menu);
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
}
