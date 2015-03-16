package com.akash.gosi.myriadinternchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class QuestKingdomSlidePageFragment extends Fragment {

    String kingdomName = "No Title Found";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_kingdom_slide, container, false);
        setHasOptionsMenu(true);


        //Get the information from the bundle
        String kingdomId = getArguments().getString("kingdomId");
        kingdomName = getArguments().getString("kingdomName");
        String kingdomClimate = getArguments().getString("kingdomClimate");
        String kingdomPopulation = getArguments().getString("kingdomPopulation");
        String kingdomImage = getArguments().getString("kingdomImageUrl");


        //Get the views in fragment
        TextView mKingdomName = (TextView) rootView.findViewById(R.id.txt_kingdom_name);
        TextView mKingdomClimate = (TextView) rootView.findViewById(R.id.txt_climate);
        TextView mKingdomPopulation = (TextView) rootView.findViewById(R.id.txt_population);
        final ImageView mKingdomImage = (ImageView) rootView.findViewById(R.id.img_kingdom_image);

        //Check if anything is not provide and set defaults if required
        kingdomName = kingdomName.isEmpty()?"Kingdom Name Not Provided":kingdomName;
        kingdomClimate = kingdomClimate.isEmpty()?"Kingdom Climate Not Provided":kingdomClimate;
        kingdomPopulation = kingdomPopulation.isEmpty()?"Kingdom Population Not Provided":kingdomPopulation;
        kingdomImage = kingdomImage.isEmpty()?Util.DEFAULT_KINGDOM_IMAGE:kingdomImage;

        mKingdomName.setText(kingdomName);
        mKingdomClimate.setText(kingdomClimate);
        mKingdomPopulation.setText(kingdomPopulation);
        Picasso.with(mKingdomImage.getContext()).load(kingdomImage).into(mKingdomImage);


        //Make the image popup
        final String finalKingdomImage = kingdomImage;
        mKingdomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the image to the image dialog activity
                Intent dialogIntent = new Intent(mKingdomImage.getContext(), ImageDialogActivity.class);
                dialogIntent.putExtra("dialogPicture", finalKingdomImage);
                startActivity(dialogIntent);
            }
        });


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
        kingdomFragmentInfo.putString("kingdomImageUrl",kingdom.getImage());
        //TODO: and image

        fragment.setArguments(kingdomFragmentInfo);
        return fragment;
    }



}