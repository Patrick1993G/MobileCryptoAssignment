package com.example.home_assignment_widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.PREF_BITCOIN;
import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.PREF_CRO;
import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.PREF_ETH;
import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.PREF_MOB;
import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.SHARE_PREFS;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetProvider extends AppWidgetProvider {

    public static String mob;
    public static int bitcoin,eth,cro;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //set intent
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        //load from shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
        mob = sharedPreferences.getString(PREF_MOB+appWidgetId,"0000000");
        bitcoin = Integer.parseInt(sharedPreferences.getString(PREF_BITCOIN+appWidgetId, "0"));
        eth = Integer.parseInt(sharedPreferences.getString(PREF_ETH+appWidgetId, "0"));
        cro = Integer.parseInt(sharedPreferences.getString(PREF_CRO+appWidgetId, "0"));
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.crypto_widget);
        //call intent when widget is clicked
        views.setOnClickPendingIntent(R.id.imageCrypto,pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}