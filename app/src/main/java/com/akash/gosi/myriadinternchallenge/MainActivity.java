package com.akash.gosi.myriadinternchallenge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    static int backButtonCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences login = getSharedPreferences("userInfo",0);
        if(login != null){
            String name = login.getString("Name","");
            if(!name.isEmpty()){
                Intent showKingdomsIntent = new Intent(MainActivity.this, ShowKingdomsActivity.class);
                startActivity(showKingdomsIntent);
                finish();
                return;
            }
        }

        Toast.makeText(MainActivity.this,"Please Sign In",Toast.LENGTH_SHORT).show();
        Intent showSignUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(showSignUpIntent);
        finish();



    }





}
