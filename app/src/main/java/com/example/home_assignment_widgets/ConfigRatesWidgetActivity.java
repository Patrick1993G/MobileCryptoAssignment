package com.example.home_assignment_widgets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

public class ConfigRatesWidgetActivity extends AppCompatActivity {

    public static final String SHARE_PREFS = "widget_config";
    public static final String PREF_BITCOIN = "bitcoin_config";
    public static final String PREF_CRO = "cro_config";
    public static final String PREF_ETH = "etherium_config";
    public static final String PREF_MOB = "mob_config";
    private EditText txtMob,txtBitcoin,txtCRO,txtEtherium;
    private int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates_widget);

        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();

        if(extras != null){
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);
        setResult(RESULT_CANCELED, result);

        if(widgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
        }

        txtBitcoin = findViewById(R.id.editTextBitcoin);
        txtCRO = findViewById(R.id.editTextCRO);
        txtEtherium = findViewById(R.id.editTextEtherium);
        txtMob = findViewById(R.id.editTextMob);
    }
    public void saveWidgetConfig(View v){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        String bitcoin =String.valueOf(txtBitcoin.getText());
        String cro =String.valueOf(txtCRO.getText());
        String eth =String.valueOf(txtEtherium.getText());
        String mob =String.valueOf(txtMob.getText());

        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.crypto_widget);
        remoteViews.setOnClickPendingIntent(R.id.btnConfig, pendingIntent);

        appWidgetManager.updateAppWidget(widgetId,remoteViews);

        SharedPreferences preferences = getSharedPreferences(SHARE_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_BITCOIN+widgetId,bitcoin);
        editor.putString(PREF_CRO+widgetId, cro);
        editor.putString(PREF_ETH+widgetId,eth);
        editor.putString(PREF_MOB+widgetId, mob);
        editor.apply();

        Intent result = new Intent( );
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);
        setResult(RESULT_OK, result);
        finish();
    }
}