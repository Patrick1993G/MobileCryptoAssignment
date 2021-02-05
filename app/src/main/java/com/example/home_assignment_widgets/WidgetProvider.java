package com.example.home_assignment_widgets;

import android.app.Notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
    public static float bitcoin,eth,cro;
    public static SharedPreferences sharedPreferences ;
    public static final String VALUE_CRYPTO= "crypto_details";
    NotificationManagerCompat notificationManagerCompat;
    private static void updatePreferences(int widgetId,Context context){
        //load from shared preferences
        sharedPreferences = context.getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
        mob = sharedPreferences.getString(PREF_MOB+widgetId,"0000000");
        bitcoin = Float.parseFloat(sharedPreferences.getString(PREF_BITCOIN+widgetId, "0"));
        eth = Float.parseFloat(sharedPreferences.getString(PREF_ETH+widgetId, "0"));
        cro = Float.parseFloat(sharedPreferences.getString(PREF_CRO+widgetId, "0"));

    }

//    @Override
//    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
//        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.crypto_widget);
//        //WidgetResize(newOptions, view);
//    }

//    private  void WidgetResize(Bundle newOptions, RemoteViews view) {
//        int minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
//        int maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
//        int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
//        int maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
//        //String ids [] = {"Bitcoin", "Eth","Cro",};
//        if(maxHeight > 200){
////            for (String id: ids
////                 ) {
////                view.setViewVisibility(R.id.txtWidget+id, View.VISIBLE);
////            }
//            view.setViewVisibility(R.id.txtWidgetBitcoin, View.VISIBLE);
//            view.setViewVisibility(R.id.txtWidgetEth, View.VISIBLE);
//            view.setViewVisibility(R.id.txtWidgetCro, View.VISIBLE);
//            view.setViewVisibility(R.id.textViewBitcoin, View.VISIBLE);
//            view.setViewVisibility(R.id.textViewCro, View.VISIBLE);
//            view.setViewVisibility(R.id.textViewEth, View.VISIBLE);
//            view.setViewVisibility(R.id.txtWidgetCountry, View.VISIBLE);
//            view.setViewVisibility(R.id.textWidgetCountry, View.VISIBLE);
//        }else{
//            view.setViewVisibility(R.id.txtWidgetBitcoin, View.GONE);
//            view.setViewVisibility(R.id.txtWidgetEth, View.GONE);
//            view.setViewVisibility(R.id.txtWidgetCro, View.GONE);
//            view.setViewVisibility(R.id.textViewBitcoin, View.GONE);
//            view.setViewVisibility(R.id.textViewCro, View.GONE);
//            view.setViewVisibility(R.id.textViewEth, View.GONE);
//            view.setViewVisibility(R.id.txtWidgetCountry, View.GONE);
//            view.setViewVisibility(R.id.textWidgetCountry, View.GONE);
//        }
//    }
//    public void generalNotification(String title,String desc,Context context){
//        Intent activityIntent = new Intent(context,MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(context,0,activityIntent,0);
//
//        Notification notification = new NotificationCompat.Builder(context,Notifications.CHANNEL_GENERAL)
//                .setSmallIcon(R.drawable.ic_bitcoin_coin)
//                .setContentTitle(title)
//                .setContentText(desc)
//                .setColor(Color.BLUE)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setContentIntent(contentIntent)//to go back to app
//                .build();
//        notificationManagerCompat.notify(1,notification);
//    }

    public void updateWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        //set intent
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.crypto_widget);
        //get the users preferences
        updatePreferences(appWidgetId,context);
//get bundle
            //Bundle widgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            //resize Widget
           // WidgetResize(widgetOptions, views);
            //get values
            sharedPreferences = context.getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
            String crypto =sharedPreferences.getString(VALUE_CRYPTO, "[]");
            if(!crypto.isEmpty()){
                List<CryptoObject> cryptoList = HelperClass.GetListFromJson(crypto);
                //update values in widget & check user limit
                if(!cryptoList.isEmpty()){
                    //set an instance for sms manager
                   // SmsManager smsManager = SmsManager.getDefault();
                    String sms = "";
                    for (CryptoObject c: cryptoList )
                    {

                        if(c.getTitle().equals("BTC_USDT")){
                            views.setCharSequence(R.id.txtWidgetBitcoin,"setText",c.getPrice());
                            //check user's limit
                            if(bitcoin!=0.0000 && bitcoin <=  Float.parseFloat(c.getPrice())){
                               HelperClass.generalNotification("bitcoin status","Bitcoin is at:$ "+c.getPrice(),context,notificationManagerCompat,appWidgetId);
                            }
                            sms += ",Bitcoin is at:$ "+c.getPrice();
                        }else if (c.getTitle().equals("CRO_USDT")){
                            views.setCharSequence(R.id.txtWidgetCro,"setText",c.getPrice());
                            //check user's limit
                            if(cro!=0.00000 && cro <= Float.parseFloat(c.getPrice())){
                                HelperClass.generalNotification("CRO status","CRO is at:$ "+c.getPrice(),context,notificationManagerCompat,appWidgetId);
                            }
                            sms += ",CRO is at:$ "+c.getPrice();
                        }else if(c.getTitle().equals("ETH_USDT")){
                            views.setCharSequence(R.id.txtWidgetEth,"setText",c.getPrice());
                            //check user's limit
                            if(eth!=0.0000 && eth <=  Float.parseFloat(c.getPrice())){
                                HelperClass.generalNotification("Etherium status","Etherium is at:$ "+c.getPrice(),context,notificationManagerCompat,appWidgetId);
                            }
                            sms += ",Etherium is at:$ "+c.getPrice();
                        }
                    }
                   // mob = "+1-555-521-5554";
                    if(sms != ""){
                        HelperClass.sendSms(mob,sms);
                    }
                }
            }
            //call intent when widget is clicked
            views.setOnClickPendingIntent(R.id.imageCryptoBtn,pendingIntent);
            // Instruct the widget manager to update the widget
           appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //get the crypto details
        notificationManagerCompat = NotificationManagerCompat.from(context);
        String url = "https://api.crypto.com/v2/public/get-trades";
        String status= String.valueOf(new MainAsync().execute(url).getStatus());
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    protected class MainAsync extends AsyncTask<String, Void, String> {
        /*ArrayList containing strings written in JSON format*/
        ArrayList<CryptoObject> cryptoList = new ArrayList<>();
        ArrayList<CryptoObject> myCrypto = new ArrayList<>();

        @Override
        /*This method retrieves the data from the internet*/
        protected String doInBackground(String... url) {
            try {
                /*Getting data from the internet*/
                String json = Connection.getData(url[0]);
                JSONObject object = new JSONObject(json);
                JSONObject result = object.getJSONObject("result");
                JSONArray search = (JSONArray) result.toJSONArray(result.names()).get(0);
                int count =0;
                for (int i = 0; i < search.length(); i++) {

                    JSONObject o = search.getJSONObject(i);
                    //get the title, price and id
                    String title = o.getString("i");
                    String price = o.getString("p");
                    String id = o.getString("d");
                    //create an object and add to list
                    CryptoObject crypto = new CryptoObject(title, price,id);
                    this.cryptoList.add(crypto);
                }
                //filter results
                for (int i = 0; i < cryptoList.size(); i++) {
                    if (count < 3) {
                        String title = cryptoList.get(i).getTitle();
                        if (title.equals("BTC_USDT") || title.equals("CRO_USDT") || title.equals("ETH_USDT")) {
                            myCrypto.add(cryptoList.get(i));
                            count++;
                        }
                    }
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //save the values to shared preferences
            String cryptoDetails = myCrypto.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(VALUE_CRYPTO,cryptoDetails);
            editor.apply();
        }
    }
}