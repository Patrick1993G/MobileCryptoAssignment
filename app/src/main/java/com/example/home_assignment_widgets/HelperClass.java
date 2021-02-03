package com.example.home_assignment_widgets;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
}
