package com.example.home_assignment_widgets;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import androidx.annotation.NonNull;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification() != null){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetId = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetProvider.class));

            RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.crypto_widget);
          //  ComponentName name = new ComponentName("com.example.home_assignment_widgets","com.example.home_assignment_widgets.WidgetProvider");
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
//            appWidgetManager.updateAppWidget(name,remoteViews);
        }
    }
}
