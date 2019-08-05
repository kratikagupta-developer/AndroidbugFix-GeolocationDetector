package com.example.kratika.systemsim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStateReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
//        if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION))
//        {
//            String bssid = intent.getStringExtra("BSSID");
//            String ssid = intent.getStringExtra("ssid");
//            String mac = intent.getStringExtra("mac");
//
//            String info = intent.getExtras().toString();
//
//            if (info != null)
//                Log.d("infooooo", info);
//
//            if (bssid != null)
//                Log.d("Kun-BSSID", bssid);
//            if (ssid != null)
//                Log.d("Kun-SSID", ssid);
//            if (mac != null)
//                Log.d("Kun-mac", mac);
////            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
////            Log.d("NETWORKAKSKDKFSDFSDF", Integer.toString(wifiState));
//        }
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
