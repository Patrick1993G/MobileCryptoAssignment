package com.example.home_assignment_widgets;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public boolean handleIntentOnMainThread(Intent intent) {
        intent = new Intent(this, WidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIdArr = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetProvider.class));

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIdArr);
        Boolean test = false; // for testing set to true
        if (test) {
            Handler handler = new Handler();
            Intent finalIntent = intent;
            for (int i = 0; i<5;i++) {
                handler.postDelayed(new Runnable() {
                    public void run() {
                        sendBroadcast(finalIntent);
                    }
                }, 10000); //wait 10 sec
            }
        } else {
            sendBroadcast(intent);
        }
        return true;
    }
}
