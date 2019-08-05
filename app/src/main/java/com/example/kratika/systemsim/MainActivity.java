package com.example.kratika.systemsim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private Bundle info = null;
    private WifiInfo wifiInfo = null;
    String bssid = null;
    String ssid = null;
    String mac = null;


    private BroadcastReceiver ReceiveWifi = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION))
            {

                info = intent.getExtras();
                wifiInfo = (WifiInfo) info.get("wifiInfo");
                bssid = wifiInfo.getBSSID();
                ssid = wifiInfo.getSSID();
//                for (String key : info.keySet())
//                {
//                    Log.d(key + " Test", info.get(key).getClass().getName());
//                }
//                if (info != null)
//                    Log.d("infooooo", info);
//
//                if (bssid != null)
//                    Log.d("Kun-BSSID", bssid);
//                if (ssid != null)
//                    Log.d("Kun-SSID", ssid);
//                if (mac != null)
//                    Log.d("Kun-mac", mac);

//                Intent i = new Intent("android.intent.action.SmsReceiver").putExtra("wifi_info", info);
//                context.sendBroadcast(i);


//            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
//            Log.d("NETWORKAKSKDKFSDFSDF", Integer.toString(wifiState));
            }
            // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        }
    };


//    private BroadcastReceiver receiver = new NetworkStateReceiver();
    private IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);

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
        this.registerReceiver(ReceiveWifi, filter);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        this.unregisterReceiver(ReceiveWifi);
    }

    public void SendDefaultBroadcast(View view)
    {
        String critical = "SSID: " + ssid + "\nBSSID: " + bssid;
        TextView info_box = (TextView)findViewById(R.id.tb_info_box);
        info_box.setText(critical);

        Intent defaultBroadcast = new Intent("DEFAULT_WIFI_BROADCAST");
        defaultBroadcast.putExtra("SSID", ssid);
        defaultBroadcast.putExtra("BSSID", bssid);
        sendBroadcast(defaultBroadcast);
    }

    public void SendModifiedBroadcast(View view)
    {
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV

        String critical = "SSID: " + ssid + "\nBSSID: " + bssid + "\nEncryption Key: " + key;
        TextView info_box = (TextView)findViewById(R.id.tb_info_box);
        info_box.setText(critical);

        Intent modifiedBroadcast = new Intent("MODIFIED_WIFI_BROADCAST");
        String encrypted_bssid = null;
        String encrypted_ssid = null;


        try
        {
            byte[] by = initVector.getBytes("UTF-8");
            Log.d("Byteswa", Integer.toString(by.length));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        try
        {
            Log.d("encrypted", "on bssid");
            encrypted_bssid = Encryption.encrypt(key, initVector, bssid);
            Log.d("encrypted", "on ssid");
            encrypted_ssid = Encryption.encrypt(key, initVector, ssid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Log.d("Encr", encrypted_bssid);
        Log.d("Encr", encrypted_ssid);

        modifiedBroadcast.putExtra("BSSID", encrypted_bssid);
        modifiedBroadcast.putExtra("SSID", encrypted_ssid);
        modifiedBroadcast.putExtra("SECRET", key);

        sendBroadcast(modifiedBroadcast);

    }

}
