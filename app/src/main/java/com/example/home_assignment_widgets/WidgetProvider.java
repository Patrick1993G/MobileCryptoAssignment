package com.example.home_assignment_widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public static SharedPreferences sharedPreferences ;
    public static final String VALUE_CRYPTO= "crypto_details";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //set intent
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        mob = sharedPreferences.getString(PREF_MOB+appWidgetId,"0000000");
        bitcoin = Integer.parseInt(sharedPreferences.getString(PREF_BITCOIN+appWidgetId, "0"));
        eth = Integer.parseInt(sharedPreferences.getString(PREF_ETH+appWidgetId, "0"));
        cro = Integer.parseInt(sharedPreferences.getString(PREF_CRO+appWidgetId, "0"));
        //get the crypto details
        String url = "https://api.crypto.com/v2/public/get-trades";
        new MainAsync().execute(url);

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
        //load from shared preferences
        sharedPreferences = context.getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    protected static class MainAsync extends AsyncTask<String, Void, String> {
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
        }
    }
}