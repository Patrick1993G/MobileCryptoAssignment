package com.example.home_assignment_widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.SHARE_PREFS;
import static com.example.home_assignment_widgets.WidgetProvider.VALUE_CRYPTO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
        String crypto =sharedPreferences.getString(VALUE_CRYPTO, "[]");
        List<CryptoObject> cryptoList=HelperClass.GetListFromJson(crypto);

        if(!cryptoList.isEmpty()){
            for (CryptoObject c: cryptoList )
            {
                if(c.getTitle().equals("BTC_USDT")){
                    TextView bitVal= findViewById(R.id.txtBitcoin);
                    bitVal.setText(c.getPrice());
                }else if (c.getTitle().equals("CRO_USDT")){
                    TextView croVal= findViewById(R.id.txtCro);
                    croVal.setText(c.getPrice());
                }else if(c.getTitle().equals("ETH_USDT")){
                    TextView ethVal= findViewById(R.id.txtEth);
                    ethVal.setText(c.getPrice());
                }
            }
        }
    }



}