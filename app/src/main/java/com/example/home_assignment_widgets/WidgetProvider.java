package com.example.home_assignment_widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private static RemoteViews views = null;
    private static void updatePreferences(){
        //load from shared preferences
        mob = sharedPreferences.getString(PREF_MOB,"0000000");
        bitcoin = Integer.parseInt(sharedPreferences.getString(PREF_BITCOIN, "0"));
        eth = Integer.parseInt(sharedPreferences.getString(PREF_ETH, "0"));
        cro = Integer.parseInt(sharedPreferences.getString(PREF_CRO, "0"));

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.crypto_widget);

        WidgetResize(newOptions, view);
    }

    private  void WidgetResize(Bundle newOptions, RemoteViews view) {
        int minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);

        if(maxWidth > 100){
            view.setViewVisibility(R.id.txtWidgetBitcoin, View.VISIBLE);
            view.setViewVisibility(R.id.txtWidgetEth, View.VISIBLE);
            view.setViewVisibility(R.id.txtWidgetCro, View.VISIBLE);
            view.setViewVisibility(R.id.textViewBitcoin, View.VISIBLE);
            view.setViewVisibility(R.id.textViewCro, View.VISIBLE);
            view.setViewVisibility(R.id.textViewEth, View.VISIBLE);
        }else{
            view.setViewVisibility(R.id.txtWidgetBitcoin, View.GONE);
            view.setViewVisibility(R.id.txtWidgetEth, View.GONE);
            view.setViewVisibility(R.id.txtWidgetCro, View.GONE);
            view.setViewVisibility(R.id.textViewBitcoin, View.GONE);
            view.setViewVisibility(R.id.textViewCro, View.GONE);
            view.setViewVisibility(R.id.textViewEth, View.GONE);
        }
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        //set intent
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        //get the crypto details
        String url = "https://api.crypto.com/v2/public/get-trades";
        new MainAsync().execute(url);
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.crypto_widget);
        //get bundle
        Bundle widgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        //resize Widget
        WidgetResize(widgetOptions, views);
        //get values
        String crypto =sharedPreferences.getString(VALUE_CRYPTO, "[]");
        List<CryptoObject> cryptoList = HelperClass.GetListFromJson(crypto);
        //update values in widget
        if(!cryptoList.isEmpty()){
            for (CryptoObject c: cryptoList )
            {
                if(c.getTitle().equals("BTC_USDT")){
                    views.setCharSequence(R.id.txtWidgetBitcoin,"setText",c.getPrice());
                    //check user's limit
                    if(bitcoin >= Integer.parseInt(c.getPrice())){
                        //show notification
                    }
                }else if (c.getTitle().equals("CRO_USDT")){
                    views.setCharSequence(R.id.txtWidgetCro,"setText",c.getPrice());
                    //check user's limit
                    if(cro >= Integer.parseInt(c.getPrice())){
                        //show notification
                    }
                }else if(c.getTitle().equals("ETH_USDT")){
                    views.setCharSequence(R.id.txtWidgetEth,"setText",c.getPrice());
                    //check user's limit
                    if(eth >= Integer.parseInt(c.getPrice())){
                        //show notification
                    }
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
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        //get the users preferences
        updatePreferences();

    }

    @Override
    public void onEnabled(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
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
            editor.apply();

        }
    }
}