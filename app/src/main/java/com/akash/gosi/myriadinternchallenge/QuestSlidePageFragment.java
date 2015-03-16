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
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.ActionBarActivity;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class QuestSlidePageFragment extends Fragment {

    String questName = "No Title Found";

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_quest_slide_page,container,false);

        //Get the information from the bundle

        //Quest Information
        String questId = getArguments().getString("questId");
        questName = getArguments().getString("questName");
        String questDesc = getArguments().getString("questDesc");
        String questImage = getArguments().getString("questImage");

        //Giver Information
        String questGiverId = getArguments().getString("questGiverId");
        String questGiverName = getArguments().getString("questGiverName");
        String questGiverImage = getArguments().getString("questGiverImage");

        //Get the views in fragment
        TextView mQuestName = (TextView) rootView.findViewById(R.id.txt_quest_name);
        TextView mQuestDescription = (TextView) rootView.findViewById(R.id.txt_quest_desc);
        final ImageView mQuestImage = (ImageView) rootView.findViewById(R.id.img_quest_image);
        TextView mQuestGiver = (TextView) rootView.findViewById(R.id.txt_quest_giver);
        final ImageView mQuestGiverImage = (ImageView) rootView.findViewById(R.id.img_quest_giver_image);

        //Check if something is not available
        questName = questName.isEmpty()?"Quest Name Not Provided":questName;
        questDesc = questDesc.isEmpty()?"Quest Description Not Provided":questDesc;
        questGiverName = questGiverName.isEmpty()?"Quest Giver Not Provided":questGiverName;

        //Set Default Image if nothing is provided
        questImage = questImage.isEmpty()?Util.DEFAULT_QUEST_IMAGE:questImage;
        questGiverImage = questGiverImage.isEmpty()?Util.DEFAULT_GIVER_IMAGE:questGiverImage;

        mQuestName.setText(questName);
        mQuestDescription.setText(questDesc);
        mQuestGiver.setText(questGiverName);
        Picasso.with(mQuestImage.getContext()).load(questImage).into(mQuestImage);
        Picasso.with(mQuestGiverImage.getContext()).load(questGiverImage).into(mQuestGiverImage);


        final String finalQuestImage = questImage;
        mQuestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the image to the image dialog activity
                Intent dialogIntent = new Intent(mQuestImage.getContext(), ImageDialogActivity.class);
                dialogIntent.putExtra("dialogPicture", finalQuestImage);
                startActivity(dialogIntent);
            }
        });

        final String finalQuestGiverImage = questGiverImage;
        mQuestGiverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the image to the image dialog activity
                Intent dialogIntent = new Intent(mQuestGiverImage.getContext(), ImageDialogActivity.class);
                dialogIntent.putExtra("dialogPicture", finalQuestGiverImage);
                startActivity(dialogIntent);
            }
        });


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
