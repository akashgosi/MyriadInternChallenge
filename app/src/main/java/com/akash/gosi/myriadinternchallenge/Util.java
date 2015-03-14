package com.akash.gosi.myriadinternchallenge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public final class Util {


    /**
     * Logout Function
     */
    public static void logOut(Context context){
        SharedPreferences login = context.getSharedPreferences("userInfo",0);
        SharedPreferences.Editor Ed=login.edit();
        Ed.putString("Name","" );
        Ed.putString("Email","");
        Ed.commit();
        Toast.makeText(context, "Logout Successful",Toast.LENGTH_SHORT).show();
        Intent showKingdomsIntent = new Intent(context, MainActivity.class);
        showKingdomsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(showKingdomsIntent);




    }
}


