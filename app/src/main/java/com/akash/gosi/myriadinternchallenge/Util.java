package com.akash.gosi.myriadinternchallenge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public final class Util {


    public static String DEFAULT_GIVER_IMAGE = "http://upload.wikimedia.org/wikipedia/en/9/96/User_icon-cp.png";
    public static String  DEFAULT_QUEST_IMAGE = "https://bryantrice.files.wordpress.com/2013/08/compass-and-old-map.jpg";
    public static String DEFAULT_KINGDOM_IMAGE = "http://andybraner.typepad.com/.a/6a00e54ed0df528833014e8921fb90970d-pi";

    /**
     * Logout Function
     */
    public static void logOut(Context context){
        SharedPreferences login = context.getSharedPreferences("userInfo",0);
        SharedPreferences.Editor Ed=login.edit();
        Ed.putString("Name","" );
        Ed.putString("Email","");
        Ed.commit();

        //Removed the saved quests upon logout
        SharedPreferences prefs = context.getSharedPreferences(context.getResources().getString(R.string.sr_saved_items), Context.MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(context.getResources().getString(R.string.sr_saved_quests),"");
        e.remove(context.getResources().getString(R.string.sr_saved_quests));
        e.commit();

        Intent showKingdomsIntent = new Intent(context, MainActivity.class);
        showKingdomsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(showKingdomsIntent);
        Toast.makeText(context, "Logout Successful",Toast.LENGTH_SHORT).show();




    }
}


