package com.akash.gosi.myriadinternchallenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class QuestSlidePageFragment extends Fragment {


    Kingdoms.Quests quest = new Kingdoms.Quests();
    Kingdoms.Giver giver = new Kingdoms.Giver();
    CheckBox mSaveQuest;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onPause() {
        super.onPause();
        boolean save = mSaveQuest.isChecked();
        if(save){
            if(ShowQuestsActivity.savedQuests!=null&&!ShowQuestsActivity.savedQuests.contains(quest))
                ShowQuestsActivity.savedQuests.add(quest);
        }else{
            if(ShowQuestsActivity.savedQuests!=null&&ShowQuestsActivity.savedQuests.contains(quest))
                ShowQuestsActivity.savedQuests.remove(quest);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //null if there are no quests saved
        if(ShowQuestsActivity.savedQuests!=null&&ShowQuestsActivity.savedQuests!=null&&ShowQuestsActivity.savedQuests.contains(quest)){
            mSaveQuest.setChecked(true);
        }
        else{
            mSaveQuest.setChecked(false);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_quest_slide_page,container,false);

        //Get the information from the bundle



        //Quest Information
        quest.id = getArguments().getString("questId");
        quest.name = getArguments().getString("questName");
        quest.description = getArguments().getString("questDesc");
        quest.image = getArguments().getString("questImage");

        //Giver Information
        giver.id = getArguments().getString("questGiverId");
        giver.name = getArguments().getString("questGiverName");
        giver.image = getArguments().getString("questGiverImage");

        //Get the views in fragment
        TextView mQuestName = (TextView) rootView.findViewById(R.id.txt_quest_name);
        TextView mQuestDescription = (TextView) rootView.findViewById(R.id.txt_quest_desc);
        final ImageView mQuestImage = (ImageView) rootView.findViewById(R.id.img_quest_image);
        TextView mQuestGiver = (TextView) rootView.findViewById(R.id.txt_quest_giver);
        final ImageView mQuestGiverImage = (ImageView) rootView.findViewById(R.id.img_quest_giver_image);
        mSaveQuest = (CheckBox) rootView.findViewById(R.id.save_check);

        //Check if something is not available
        quest.name = quest.name.isEmpty()?"Quest Name Not Provided":quest.name;
        quest.description = quest.description.isEmpty()?"Quest Description Not Provided":quest.description;
        giver.name = giver.name.isEmpty()?"Quest Giver Not Provided":giver.name;

        //Set Default Image if nothing is provided
        quest.image = quest.image.isEmpty()?Util.DEFAULT_QUEST_IMAGE:quest.image;
        giver.image = giver.image.isEmpty()?Util.DEFAULT_GIVER_IMAGE:giver.image;

        mQuestName.setText(quest.name);
        mQuestDescription.setText(quest.description);
        mQuestGiver.setText(giver.name);
        Picasso.with(mQuestImage.getContext()).load(quest.image).into(mQuestImage);
        Picasso.with(mQuestGiverImage.getContext()).load(giver.image).into(mQuestGiverImage);


        final String finalQuestImage = quest.image;
        mQuestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the image to the image dialog activity
                Intent dialogIntent = new Intent(mQuestImage.getContext(), ImageDialogActivity.class);
                dialogIntent.putExtra("dialogPicture", finalQuestImage);
                startActivity(dialogIntent);
            }
        });

        final String finalQuestGiverImage = giver.image;
        mQuestGiverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the image to the image dialog activity
                Intent dialogIntent = new Intent(mQuestGiverImage.getContext(), ImageDialogActivity.class);
                dialogIntent.putExtra("dialogPicture", finalQuestGiverImage);
                startActivity(dialogIntent);
            }
        });

        final Kingdoms.Quests finalQuest = new Kingdoms.Quests(quest);
        /*
        mSaveQuest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean save = mSaveQuest.isChecked();
                if(save){
                    ShowQuestsActivity.savedQuests.add(finalQuest);
                }else{
                    ShowQuestsActivity.savedQuests.remove(finalQuest);
                }

            }
        });
        */

        return rootView;
    }


    /**
     * Store the quest information of each fragment in a bundle
    */
    public static QuestSlidePageFragment newInstanceQuest(Kingdoms.Quests quest) {
        QuestSlidePageFragment fragment = new QuestSlidePageFragment();
        Bundle kingdomFragmentInfo = new Bundle(1);

        //Quest Information
        kingdomFragmentInfo.putString("questId",quest.id);
        kingdomFragmentInfo.putString("questName",quest.name);
        kingdomFragmentInfo.putString("questDesc",quest.description);
        kingdomFragmentInfo.putString("questImage",quest.image);

        //Giver Information
        kingdomFragmentInfo.putString("questGiverId",quest.giver.id);
        kingdomFragmentInfo.putString("questGiverName",quest.giver.name);
        kingdomFragmentInfo.putString("questGiverImage",quest.giver.image);

        fragment.setArguments(kingdomFragmentInfo);

        return fragment;
    }





}
