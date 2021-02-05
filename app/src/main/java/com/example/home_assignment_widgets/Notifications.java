package com.example.home_assignment_widgets;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notifications extends Application {

    public static final String CHANNEL_GENERAL  =  "channelGeneral";
    public void onCreate(){
        super.onCreate();
        createNotificationChannels();
    }
    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelGeneral = new NotificationChannel(
                    CHANNEL_GENERAL,"Crypto Value Info", NotificationManager.IMPORTANCE_HIGH);
            channelGeneral.setDescription("Crypto Notification channel");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channelGeneral);
        }
    }
}
