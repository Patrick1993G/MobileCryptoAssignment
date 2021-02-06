package com.example.home_assignment_widgets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.PREF_MOB;
import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.SHARE_PREFS;

public class NotificationReceiver extends BroadcastReceiver {
    public static SharedPreferences sharedPreferences ;
    @Override
    public void onReceive(Context context, Intent intent) {
        //get string message
        String message = intent.getStringExtra("Message");
        //get id
        String id= intent.getStringExtra("id");

        sharedPreferences = context.getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
        String mob = sharedPreferences.getString(PREF_MOB+id,"0000000");
       // mob = "+1-555-521-5554";
        if(message != ""){
            HelperClass.sendSms(mob,message);
        }
    }

}
