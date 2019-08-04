package com.example.kratika.regularsim;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class RegApplication extends Application
{
    private static String bssid = null;
    private static String ssid = null;
    private static String encrypted_bssid = null;
    private static String encrypted_ssid = null;
    private static String encryption_key = null;

    public static String getEncrypted_bssid()
    {
        return encrypted_bssid;
    }

    public static void setEncrypted_bssid(String encrypted_bssid)
    {
        RegApplication.encrypted_bssid = encrypted_bssid;
    }

    public static String getEncrypted_ssid()
    {
        return encrypted_ssid;
    }

    public static void setEncrypted_ssid(String encrypted_ssid)
    {
        RegApplication.encrypted_ssid = encrypted_ssid;
    }

    public static String getEncryption_key()
    {
        return encryption_key;
    }

    public static void setEncryption_key(String encryption_key)
    {
        RegApplication.encryption_key = encryption_key;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.d("Servissa", "starting service");
        startService(new Intent(this, ReceiverService.class));
    }

    public static String getSsid()
    {
        return ssid;
    }

    public static void setSsid(String ssid)
    {
        RegApplication.ssid = ssid;
    }

    public static String getBssid()
    {
        return bssid;
    }

    public static void setBssid(String bssid)
    {
        RegApplication.bssid = bssid;
    }
}
