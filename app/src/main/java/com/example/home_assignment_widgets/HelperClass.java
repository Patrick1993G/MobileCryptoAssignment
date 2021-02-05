package com.example.home_assignment_widgets;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.SHARE_PREFS;

public class HelperClass {

    public static List GetListFromJson(String crypto) {
        List<CryptoObject> cryptoList= new ArrayList<CryptoObject>();
        JSONArray search = null;
        try {
            search = new JSONArray(crypto);

            for (int i = 0; i < search.length(); i++) {

                JSONObject o = search.getJSONObject(i);
                //get the title, price and id
                String title = o.getString("title");
                String price = o.getString("price");
                String id = o.getString("id");
                //create an object and add to list
                CryptoObject cryptoObject = new CryptoObject(title, price,id);
                cryptoList.add(cryptoObject);
            }}
        catch (JSONException e) {
            e.printStackTrace();
        }
        return cryptoList;
    }
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                             int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.crypto_widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    public static void generalNotification(String title, String desc, Context context, NotificationManagerCompat notificationManagerCompat,int widgetId){
        Intent activityIntent = new Intent(context,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,activityIntent,0);

        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        broadcastIntent.putExtra("Message",desc);
        broadcastIntent.putExtra("id",String.valueOf(widgetId));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,broadcastIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context,Notifications.CHANNEL_GENERAL)
                .setSmallIcon(R.drawable.ic_bitcoin_coin)
                .setContentTitle(title)
                .setContentText(desc)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)//to go back to app
                .addAction(R.drawable.ic_bitcoin_coin,"Message from Crypto",pendingIntent)
                .build();
        notificationManagerCompat.notify(1,notification);
    }

    public static void sendSms(String mob,String smsText){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(mob,null,smsText,null,null);
    }

}
