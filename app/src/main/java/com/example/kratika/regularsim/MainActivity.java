package com.example.kratika.regularsim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{

    String bssid = null;
    String ssid = null;
    TextView tv_bssid = null;
    TextView tv_ssid = null;
    TextView tv_location = null;
    TextView tv_encryption_key = null;
    GeoLocationData geoLocationData = null;

    String api_response = null;
//    Intent wifiDataIntent = null;

    private IntentFilter modifiedFilter = new IntentFilter("MODIFIED_WIFI_BROADCAST");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        tv_bssid = (TextView)findViewById(R.id.tv_bssid);
        tv_ssid = (TextView)findViewById(R.id.tv_ssid);
        tv_location = (TextView)findViewById(R.id.tv_location);
        tv_encryption_key = (TextView)findViewById(R.id.tv_encryption_key);

//        this.registerReceiver(defaultBroadcastReceiver, defaultFilter);
        this.registerReceiver(modifiedInfoReceiver, modifiedFilter);

        if (RegApplication.getBssid() != null)
            tv_bssid.setText("BSSID " + RegApplication.getBssid());

        if (RegApplication.getEncrypted_bssid() != null)
            tv_bssid.setText(tv_bssid.getText() + "\n(Encrypted: )" + RegApplication.getEncrypted_bssid());

        if (RegApplication.getSsid() != null)
            tv_ssid.setText("SSID " + RegApplication.getSsid());

        if (RegApplication.getEncrypted_ssid() != null)
            tv_ssid.setText(tv_ssid.getText() + "\n(Encrypted: )" + RegApplication.getEncrypted_ssid());

        if (RegApplication.getEncryption_key() != null)
            tv_encryption_key.setText("Encryption Key: " + RegApplication.getEncryption_key());

    }

    @Override
    public void onStop()
    {
        super.onStop();
//        this.unregisterReceiver(defaultBroadcastReceiver);
        this.unregisterReceiver(modifiedInfoReceiver);
    }

    public void GetLocation(View view)
    {
        // get location here and set textview
        try
        {
            Log.d("BSSSS", "Entering webpage");
            GetWebpage(RegApplication.getBssid());
        }
        catch (Exception e)
        {
            Log.d("HTML", e.toString());
        }
    }

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException
    {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response  response = client.newCall(request).execute();
        return response.body().string();
    }

    private void GetWebpage(String bssid) throws IOException
    {
//        bssid = "18:64:72:23:e1:32";
        Log.d("BSSSS", bssid);
        String baseUrl = "https://api.mylnikov.org/geolocation/wifi?v=1.1&data=closed&bssid=" + bssid;

        Request request = new Request.Builder()
                .url(baseUrl)
                .build();

        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                api_response = response.body().string();
                Log.d("html_response", api_response);
                final GeoLocationResponse geoLocationResponse = new Gson().fromJson(api_response, GeoLocationResponse.class);
                final GeoLocationData geoLocationData = geoLocationResponse.getData();

                MainActivity.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (geoLocationData == null)
                            tv_location.setVisibility(View.INVISIBLE);
                        else
                        {
//                            geoLocationData = new Gson().fromJson(geoData, GeoLocationData.class);
                            tv_location.setText("Latitude " + geoLocationData.getLatitude() + "\n" + "Longitude " + geoLocationData.getLongitude());
                        }

                    }
                });

            }
        });
    }


    private BroadcastReceiver defaultInfoReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

//            WifiInfo stuff = (WifiInfo) intent.getExtras().get("wifiInfo");
            if (intent.getAction().equals("DEFAULT_WIFI_BROADCAST"))
            {
                Log.d("INTENT", "intent milgaya");
                ssid = intent.getStringExtra("SSID");
                bssid = intent.getStringExtra("BSSID");
                tv_ssid.setText("SSID " + ssid);
                tv_bssid.setText("BSSID " + bssid);
            }
        }

    };

    private BroadcastReceiver modifiedInfoReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

//            WifiInfo stuff = (WifiInfo) intent.getExtras().get("wifiInfo");

            if (intent.getAction().equals("MODIFIED_WIFI_BROADCAST"))
            {

            }

            tv_bssid.setText(bssid);
            tv_ssid.setText(ssid);
        }
    };

}
