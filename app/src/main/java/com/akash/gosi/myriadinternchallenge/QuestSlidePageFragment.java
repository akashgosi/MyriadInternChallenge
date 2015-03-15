package com.akash.gosi.myriadinternchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuestSlidePageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_quest_slide_page,container,false);

        //Get the information from the bundle

        //Quest Information
        String questId = getArguments().getString("questId");
        String questName = getArguments().getString("questName");
        String questDesc = getArguments().getString("questDesc");
        String questImage = getArguments().getString("questImage");

        //Giver Information
        String questGiverId = getArguments().getString("questGiverId");
        String questGiverName = getArguments().getString("questGiverName");
        String questGiverImage = getArguments().getString("questGiverImage");

        //Get the views in fragment
        TextView mQuestName = (TextView) rootView.findViewById(R.id.txt_quest_name);
        TextView mQuestDescription = (TextView) rootView.findViewById(R.id.txt_quest_desc);
        TextView mQuestImage = (TextView) rootView.findViewById(R.id.txt_quest_image);
        TextView mQuestGiver = (TextView) rootView.findViewById(R.id.txt_quest_giver);
        TextView mQuestGiverImage = (TextView) rootView.findViewById(R.id.txt_quest_giver_image);

        mQuestName.setText(questName);
        mQuestDescription.setText(questDesc);
        mQuestGiver.setText(questGiverName);
        mQuestGiverImage.setText(questGiverImage);
        mQuestImage.setText(questImage);



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
