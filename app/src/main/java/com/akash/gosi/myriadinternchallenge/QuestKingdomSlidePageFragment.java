package com.akash.gosi.myriadinternchallenge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuestKingdomSlidePageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_kingdom_slide, container, false);

        //Get the information from the bundle
        String kingdomId = getArguments().getString("kingdomId");
        String kingdomName = getArguments().getString("kingdomName");
        String kingdomClimate = getArguments().getString("kingdomClimate");
        String kingdomPopulation = getArguments().getString("kingdomPopulation");
        String kingdomImage = getArguments().getString("kingdomImage");


        //Get the views in fragment
        TextView mKingdomName = (TextView) rootView.findViewById(R.id.txt_kingdom_name);
        TextView mKingdomClimate = (TextView) rootView.findViewById(R.id.txt_climate);
        TextView mKingdomPopulation = (TextView) rootView.findViewById(R.id.txt_population);
        TextView mKingdomImage = (TextView) rootView.findViewById(R.id.txt_kingdom_image);


        mKingdomName.setText(kingdomName);
        mKingdomClimate.setText(kingdomClimate);
        mKingdomPopulation.setText(kingdomPopulation);
        mKingdomImage.setText(kingdomImage);


        return rootView;
    }

    /**
     * Store the kingdom information of each fragment in a bundle
     */
    public static QuestKingdomSlidePageFragment newInstanceKingdom(Kingdoms kingdom) {
        QuestKingdomSlidePageFragment fragment = new QuestKingdomSlidePageFragment();
        Bundle kingdomFragmentInfo = new Bundle(1);
        kingdomFragmentInfo.putString("kingdomId", kingdom.getId());
        kingdomFragmentInfo.putString("kingdomName", kingdom.getName());
        kingdomFragmentInfo.putString("kingdomClimate", kingdom.getClimate());
        kingdomFragmentInfo.putString("kingdomLanguage", kingdom.getImage());
        kingdomFragmentInfo.putString("kingdomPopulation", kingdom.getPopulation().toString());

        //TODO: Language and image

        fragment.setArguments(kingdomFragmentInfo);
        return fragment;
    }
}