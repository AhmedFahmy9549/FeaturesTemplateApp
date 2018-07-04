package com.example.sweelam.featurestemplateapp;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Locale;


/**
 * this activity is created to be extended from the activities than need back button .
 */

public class BaseActivity extends AppCompatActivity {

    private int idLANG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        localization(); // method to keep the app in english position even if the mobile was arabic language preset
        super.onCreate(savedInstanceState);

        //the language sharedprefs
        SharedPreferences prefs = getSharedPreferences("LangKey", MODE_PRIVATE);

            idLANG = prefs.getInt("languageNum", 1); //0 is the default value.

        //display backbutton of action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void localization(){
        String languageToLoad  = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public int getIdLANG() {
        return idLANG;
    }
    //back button for all the activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
