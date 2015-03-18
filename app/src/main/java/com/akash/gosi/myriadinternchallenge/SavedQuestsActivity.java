package com.akash.gosi.myriadinternchallenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SavedQuestsActivity extends ActionBarActivity {

    // UI references
    Toolbar toolbar;

    //Kingdom
    Kingdoms kingdom = null;
    ArrayList<Kingdoms.Quests> savedQuests = null;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private SlidingTabLayout mSlidingTabLayout;
    private TextView mNoQuests;
    List<Fragment> fragments = new ArrayList<Fragment>();
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_quests);

        toolbar = (Toolbar) findViewById(R.id.toolbar);



        //Get the saved quests
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.sr_saved_items), Context.MODE_PRIVATE);
        String quests = preferences.getString(getResources().getString(R.string.sr_saved_quests),null);

        Type listType = new TypeToken<ArrayList<Kingdoms.Quests>>() {}.getType();

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        savedQuests = gson.fromJson(quests, listType);



        //Get the fragments for the quests
        //Null when visting this activity without visiting the showquests activity
        if(savedQuests!=null&&savedQuests.size()>0) {
            for (Kingdoms.Quests quest : savedQuests) {
                fragments.add(QuestSlidePageFragment.newInstanceQuest(quest));
            }
            toolbar.setTitle("Saved Quests");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //Update the static saved quests so it can be updated
            ShowQuestsActivity.savedQuests = (ArrayList<Kingdoms.Quests>)savedQuests.clone();
            ShowQuestsActivity.hasQuests = true;

        }else{
            toolbar.setTitle("No Saved Quests");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mNoQuests = (TextView) findViewById(R.id.txt_no_quests);
            mNoQuests.setVisibility(View.VISIBLE);
        }

        mPager = (ViewPager) findViewById(R.id.saved_quest_pager);
        mPagerAdapter = new SavedScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);


        mSlidingTabLayout=(SlidingTabLayout) findViewById(R.id.saved_quest_sliding_tabs);
        mSlidingTabLayout.setViewPager(mPager);

        //Set tab colors
        mSlidingTabLayout.setSelectedIndicatorColors(R.color.tab_selected, R.color.white);


    }

    @Override
    protected void onPause() {
        super.onPause();
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        //Update the quests
        String value = gson.toJson(ShowQuestsActivity.savedQuests);
        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sr_saved_items), Context.MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(getResources().getString(R.string.sr_saved_quests), value);
        e.commit();
        ShowQuestsActivity.savedQuests = new ArrayList<Kingdoms.Quests>();
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
                Util.logOut(SavedQuestsActivity.this);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class SavedScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public SavedScreenSlidePagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position){
            Fragment fragment = fragments.get(position);
            return savedQuests.get(position).name;


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
}
