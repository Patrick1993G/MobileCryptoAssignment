package com.example.home_assignment_widgets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://api.crypto.com/v2/public/get-trades";
        new MainAsync().execute(url);
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

                for (int i = 0; i < search.length(); i++) {

                    JSONObject o = search.getJSONObject(i);
                    //get the title and price
                    String title = o.getString("i");
                    String price = o.getString("p");
                    //create an object and add to list
                    CryptoObject crypto = new CryptoObject(title, price);
                    this.cryptoList.add(crypto);
                }
                //filter results
                for (int i = 0; i < cryptoList.size(); i++) {
                    if (i < 3) {
                        String title = cryptoList.get(i).getTitle();
                        if (title.equals("BTC_USDT") || title.equals("CRO_USDT") || title.equals("ETH_USDT")) {
                            myCrypto.add(cryptoList.get(i));
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
            /*Required data retrieved from doInBackground are displayed in their respective
             * text views*/
        }
    }
}