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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;


public class SavedQuestsActivity extends ActionBarActivity {

    // UI references
    Toolbar toolbar;

    //Kingdom
    Kingdoms kingdom = null;
    Kingdoms.Quests[] savedQuests = null;

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
        setContentView(R.layout.activity_saved_quests);

        //Get the saved quest ids and their kingdom ids
        SharedPreferences preferences = getSharedPreferences("savedItems", Context.MODE_PRIVATE);
        String quests = preferences.getString("quests",null);

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        savedQuests = gson.fromJson(quests, Kingdoms.Quests[].class);

        //Get the fragments for the quests
        for(Kingdoms.Quests quest:savedQuests){
            fragments.add(QuestSlidePageFragment.newInstanceQuest(quest));
        }

        mPager = (ViewPager) findViewById(R.id.saved_quest_pager);
        mPagerAdapter = new SavedScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        toolbar.setTitle("Saved Quests");

        mSlidingTabLayout=(SlidingTabLayout) findViewById(R.id.saved_quest_sliding_tabs);
        mSlidingTabLayout.setViewPager(mPager);

        //Set tab colors
        mSlidingTabLayout.setSelectedIndicatorColors(R.attr.colorPrimaryDark);
        mSlidingTabLayout.setDividerColors(R.attr.colorAccent);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_quests, menu);
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

    private class SavedScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public SavedScreenSlidePagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int position){
            Fragment fragment = fragments.get(position);
            return savedQuests[position].name;


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
