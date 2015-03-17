package com.akash.gosi.myriadinternchallenge;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class SlidingAdapter extends RecyclerView.Adapter<SlidingAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private int mIcons[];

    private String mName;
    private String mEmail;

    SlidingAdapter(MenuItems menuItems){
        mNavTitles = menuItems.getTitles();
        mIcons = menuItems.getIcons();
        mName = menuItems.getName();
        mEmail = menuItems.getEmail();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int vHolderId;

        TextView vTextView;
        ImageView vImageView;
        SlidingClickInterface vListener;
        TextView vName;
        TextView vEmail;


        public ViewHolder(View itemView,int ViewType, SlidingClickInterface listener) {
            super(itemView);
            //Set the references
            vListener = listener;


            // Set the appropriate view in accordance with the the view type as passed when the holder object is created
            if(ViewType == TYPE_ITEM) {
                vTextView = (TextView) itemView.findViewById(R.id.row_text);
                vImageView = (ImageView) itemView.findViewById(R.id.row_icon);

                //Set the listeners
                vTextView.setOnClickListener(this);
                vImageView.setOnClickListener(this);
                vHolderId = 1;
            }
            else{
                vName = (TextView) itemView.findViewById(R.id.user_name);
                vEmail = (TextView) itemView.findViewById(R.id.user_email);
                vHolderId = 0;
            }




        }

        @Override
        public void onClick(View v) {
            vListener.getMenuItem(vTextView);
        }
    }

    @Override
    public SlidingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //If its a menu row
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_row,parent,false);

            ViewHolder vhItem = new ViewHolder(v,viewType, new SlidingClickInterface() {
                @Override
                public void getMenuItem(View view) {

                    TextView mRow = (TextView) view.findViewById(R.id.row_text);
                    CharSequence rowText = mRow.getText();
                    //If its the saved quests item
                    if(rowText.equals(mRow.getResources().getString(R.string.saved_quests_label))){
                        Intent savedQuestsIntent = new Intent(mRow.getContext(), SavedQuestsActivity.class);
                        mRow.getContext().startActivity(savedQuestsIntent);


                    }
                    else if(rowText.equals(mRow.getResources().getString(R.string.home_label))){
                        Intent showQuestsIntent = new Intent(mRow.getContext(), ShowKingdomsActivity.class);
                        mRow.getContext().startActivity(showQuestsIntent);

                        showQuestsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    }
                }
            });


            return vhItem;

        }
        //If its the header
        else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header,parent,false);

            ViewHolder vhHeader = new ViewHolder(v,viewType, new SlidingClickInterface() {
                @Override
                public void getMenuItem(View view) {
                    //May be go to user info when header is clicked
                }
            });

            return vhHeader;
        }
        return null;

    }


    @Override
    public void onBindViewHolder(SlidingAdapter.ViewHolder holder, int position) {
        if(holder.vHolderId ==1) {
            //Set the title and icon
            holder.vTextView.setText(mNavTitles[position - 1]);
            holder.vImageView.setImageResource(mIcons[position -1]);
        }
        else{
            //Set the name and icon
            holder.vName.setText(mName);
            holder.vEmail.setText(mEmail);
        }
    }


    @Override
    public int getItemCount() {
        //Plus 1 because of the header
        return mNavTitles.length+1;
    }

    //To implement on click
    public static interface SlidingClickInterface {
        public void getMenuItem(View view);

    }

    // To check which type of item
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }





}
