package com.example.home_assignment_widgets;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.home_assignment_widgets.ConfigRatesWidgetActivity.SHARE_PREFS;
import static com.example.home_assignment_widgets.WidgetProvider.VALUE_CRYPTO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //add permissions for sms
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},1);
        //add permissions for sms
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_PREFS, Context.MODE_PRIVATE);
        String crypto =sharedPreferences.getString(VALUE_CRYPTO, "[]");
        List<CryptoObject> cryptoList=HelperClass.GetListFromJson(crypto);

        if(!cryptoList.isEmpty()){
            for (CryptoObject c: cryptoList )
            {
                if(c.getTitle().equals("BTC_USDT")){
                    TextView bitVal= findViewById(R.id.txtBitcoin);
                    TextView bitId= findViewById(R.id.txtIdBitcoin);
                    bitVal.setText(c.getPrice());
                    bitId.setText(c.getId());
                }else if (c.getTitle().equals("CRO_USDT")){
                    TextView croVal= findViewById(R.id.txtCro);
                    TextView croId= findViewById(R.id.txtIdCro);
                    croVal.setText(c.getPrice());
                    croId.setText(c.getId());
                }else if(c.getTitle().equals("ETH_USDT")){
                    TextView ethVal= findViewById(R.id.txtEth);
                    TextView ethId= findViewById(R.id.txtIdEth);
                    ethVal.setText(c.getPrice());
                    ethId.setText(c.getId());
                }
            }
        }
    }



}