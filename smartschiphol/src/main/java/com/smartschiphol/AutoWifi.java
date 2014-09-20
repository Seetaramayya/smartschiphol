package com.smartschiphol;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.smartschiphol.utils.AppSettings;

import java.util.List;

/**
 * Created by ppatthar on 20/09/14.
 */
public class AutoWifi extends IntentService {

    public AutoWifi() {
        super("AutoWifi");
    }

    protected void onHandleIntent(Intent workIntent) {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() && wifi.startScan()) {
            List<ScanResult> wifiList = wifi.getScanResults();
            for(ScanResult s : wifiList) {
                if(s.SSID.toString() == AppSettings.SCHIPHOL_WIFI_SSID) {
                    WifiConfiguration config = prepareWifiConfig();
                    int netID = wifi.addNetwork(config);
                    wifi.enableNetwork(netID, true);
                    wifi.setWifiEnabled(true);

                }
            }
        }
    }

    private WifiConfiguration prepareWifiConfig() {
        WifiConfiguration wc = new WifiConfiguration();
        wc.SSID = AppSettings.SCHIPHOL_WIFI_SSID;
        wc.preSharedKey = AppSettings.SCHIPHOL_WIFI_PASSWORD;
        wc.status = WifiConfiguration.Status.ENABLED;
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

        return wc;
    }
}
