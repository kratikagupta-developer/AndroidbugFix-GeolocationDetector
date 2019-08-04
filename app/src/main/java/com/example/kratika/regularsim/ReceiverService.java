package com.example.kratika.regularsim;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class ReceiverService extends Service
{
    private  DefaultBroadcastReceiver defaultBroadcastReceiver = null;
    private  ModifiedBroadcastReceiver modifiedBroadcastReceiver = null;

    public ReceiverService()
    {
    }

    @Override
    public void onCreate(){
        super.onCreate();

        Log.d("Servissa", "start");

        IntentFilter defaultFilter = new IntentFilter("DEFAULT_WIFI_BROADCAST");
        defaultFilter.setPriority(100);
        defaultBroadcastReceiver = new DefaultBroadcastReceiver();
        registerReceiver(defaultBroadcastReceiver, defaultFilter);

        IntentFilter modifiedFilter = new IntentFilter("MODIFIED_WIFI_BROADCAST");
        modifiedFilter.setPriority(100);
        modifiedBroadcastReceiver = new ModifiedBroadcastReceiver();
        registerReceiver(modifiedBroadcastReceiver, modifiedFilter);


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        Log.d("Service", "destroyed");
        if (defaultBroadcastReceiver != null)
            unregisterReceiver(defaultBroadcastReceiver);

        if (modifiedBroadcastReceiver != null)
            unregisterReceiver(modifiedBroadcastReceiver);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
