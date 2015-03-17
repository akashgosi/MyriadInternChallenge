package com.akash.gosi.myriadinternchallenge;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class KingdomAdapter extends RecyclerView.Adapter<KingdomAdapter.KingdomViewHolder> {
    private List<Kingdoms> kingdomList;

    @Override
    public int getItemCount() {
        return kingdomList.size();
    }

    public KingdomAdapter(List<Kingdoms> kingdomsList){
        this.kingdomList = kingdomsList;
    }

    @Override
    public void onBindViewHolder(KingdomViewHolder holder, int position) {
        Kingdoms kd = kingdomList.get(position);
        holder.bindKingdomViewHolder(kd);
    }

    @Override
    public KingdomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_text_view, parent, false);

        return new KingdomViewHolder(itemView, new KingdomAdapter.KingdomViewHolder.KingdomClickInterface(){
            @Override
            public void getQuestsList(View view) {

                TextView mKingdomId = (TextView) view.findViewById(R.id.txt_kingdom_id);
                TextView mKingdomImageUrl = (TextView) view.findViewById(R.id.txt_image_url);
                CardView mCardView = (CardView) view.findViewById(R.id.card_view);
                ImageView mKingdomImage = (ImageView) view.findViewById(R.id.img_kingdom_image);

                //Pass the kingdom Id to get the quests of that kingdom
                Intent showQuestsIntent = new Intent(mCardView.getContext(), ShowQuestsActivity.class);
                showQuestsIntent.putExtra("kingdomId",mKingdomId.getText());
                showQuestsIntent.putExtra("kingdomImage",mKingdomImageUrl.getText());
                mCardView.getContext().startActivity(showQuestsIntent);

            }
        });

    }

    public static class KingdomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView vKingdomName;
        protected TextView vKingdomId;
        protected ImageView vKingdomImage;
        protected CardView vKingdomCard;
        protected KingdomClickInterface vListener;
        protected TextView vKingdomImageUrl;
        public KingdomViewHolder(View v, KingdomClickInterface listener){

            super(v);
            //Set the references
            vListener = listener;
            vKingdomName = (TextView) v.findViewById(R.id.txt_kingdom_name);
            vKingdomId = (TextView) v.findViewById(R.id.txt_kingdom_id);
            vKingdomImage = (ImageView) v.findViewById(R.id.img_kingdom_image);
            vKingdomCard = (CardView) v.findViewById(R.id.card_view);
            vKingdomImageUrl = (TextView) v.findViewById(R.id.txt_image_url);

            //Set the listeners
            vKingdomName.setOnClickListener(this);
            vKingdomCard.setOnClickListener(this);
            vKingdomImage.setOnClickListener(this);

        }

        public void bindKingdomViewHolder(Kingdoms kingdom){
            this.vKingdomId.setText(kingdom.getId());
            this.vKingdomName.setText(kingdom.getName());
            this.vKingdomImageUrl.setText(kingdom.getImage());
            Picasso.with(this.vKingdomImage.getContext()).load(kingdom.getImage()).into(vKingdomImage);



        }

        @Override
        public void onClick(View v) {
            vListener.getQuestsList(vKingdomCard);

        }

        public static interface KingdomClickInterface {
            public void getQuestsList(View view);

        }
    }
}
