package com.akash.gosi.myriadinternchallenge;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;


public class ShowQuestsActivity extends ActionBarActivity {


    // UI references.
    private RecyclerView.LayoutManager mLayoutManager;
    private View mProgressView;
    private View mQuestsView;
    private ShowQuestsTask mQuestTask = null;
    Toolbar toolbar;

    //Kingdom
    Kingdoms kingdom = null;
    List<Kingdoms.Quests> quests = null;
    String kingdomId = null;
    String kingdomUrl = null;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private SlidingTabLayout mSlidingTabLayout;
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
        kingdomUrl = getIntent().getExtras().getString("kingdomImage");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Loading");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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


    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position){
            Fragment fragment = fragments.get(position);

            //If its the kingdomSummary page
            if(fragment instanceof QuestKingdomSlidePageFragment){
                return kingdom.getName()+" Info";

            }
            if(fragment instanceof  QuestSlidePageFragment) {
                return quests.get(position-1).name;
            }

            return "No Title Found";

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

        for(Kingdoms.Quests quest: quests){
            fList.add(QuestSlidePageFragment.newInstanceQuest(quest));

        }
        return fList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                Util.logOut(ShowQuestsActivity.this);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /*
 * Represents an asynchronous task to get the list of kingdoms
 */
    public class ShowQuestsTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            //load the quests in the kingdom from the api

            try {
                //Get the quests from the kingdom
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("https://challenge2015.myriadapps.com")
                        .build();

                MyriadService service = restAdapter.create(MyriadService.class);
                kingdom = service.getQuests(kingdomId);
                quests = kingdom.getQuests();
                kingdom.setImage(kingdomUrl);
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

                toolbar.setTitle(kingdom.getName());

                mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
                mSlidingTabLayout.setViewPager(mPager);
                //Set tab colors
                mSlidingTabLayout.setSelectedIndicatorColors(R.attr.colorPrimaryDark);
                mSlidingTabLayout.setDividerColors(R.attr.colorAccent);


            } else {

            }
        }

        @Override
        protected void onCancelled() {

            //showProgress(false);
        }


    }



}
