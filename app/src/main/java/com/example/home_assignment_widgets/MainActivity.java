package com.example.home_assignment_widgets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.PREF_BITCOIN;
import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.SHARE_PREFS;
import static com.example.home_assignment_widgets.WidgetProvider.VALUE_CRYPTO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
        String crypto =sharedPreferences.getString(VALUE_CRYPTO, "[]");
        List<CryptoObject> cryptoList= new ArrayList<CryptoObject>();

    }

}