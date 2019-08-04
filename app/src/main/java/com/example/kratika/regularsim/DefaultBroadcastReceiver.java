package com.example.kratika.regularsim;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class DefaultBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("Servissa", "received broadcast");
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        if (intent.getAction().equals("DEFAULT_WIFI_BROADCAST"))
//        {
        String ssid = intent.getStringExtra("SSID");
        String bssid = intent.getStringExtra("BSSID");


//        Intent wifiDataIntent = new Intent(context, MainActivity.class);
//        wifiDataIntent.putExtra("SSID", ssid);
//        wifiDataIntent.putExtra("BSSID", bssid);

        RegApplication.setBssid(bssid);
        RegApplication.setSsid(ssid);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        String CHANNEL_ID = "default_broadcast";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);

            Log.d("Notttss", "Chane");
        }

        PendingIntent launchIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nBuilder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.small_wifi)
                .setChannelId(CHANNEL_ID)
                .setContentTitle("Default Broadcast Received")
                .setContentText("SSID: " + ssid + ", BSSID: " + bssid);
        nBuilder.setContentIntent(launchIntent);
        nBuilder.setDefaults(Notification.DEFAULT_SOUND);
        nBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, nBuilder.build());


//        }
    }
}
