package com.smartschiphol;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.*;
import android.util.Log;
import android.widget.Toast;

import com.smartschiphol.utils.AppSettings;

import java.util.List;

/**
 * Created by ppatthar on 20/09/14.
 */
public class AutoWifi extends BroadcastReceiver {

    public AutoWifi() {
        super();
    }

    private WifiManager wifi;

    public void onReceive(Context ctx, Intent workIntent) {
        wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        try {
            if (wifi.isWifiEnabled()) {
                String curSSID = wifi.getConnectionInfo().getSSID();
                curSSID = curSSID.replace("\"", "");
                if((!AppSettings.SCHIPHOL_WIFI_SSID.equals(curSSID))) {
                    wifi.startScan();
                    List<ScanResult> wifiList = wifi.getScanResults();
                    for (ScanResult s : wifiList) {
                        if (AppSettings.SCHIPHOL_WIFI_SSID.equals(s.SSID)) {

                            WifiConfiguration found = findNetworkInExistingConfig(s.SSID);
                            wifi.disconnect();
                            if (found == null) {
                                Log.e("AutoWifi", "Did not find any known networks");
                            } else {
                                Log.d("AutoWifi", "Removing network " + found.networkId);
                                wifi.removeNetwork(found.networkId);
                                wifi.saveConfiguration();
                            }
                            WifiConfiguration config = prepareWifiConfig();
                            int netID = wifi.addNetwork(config);
                            if(wifi.enableNetwork(netID, true)) {
                                Toast.makeText(ctx.getApplicationContext(), "Welcome to Schiphol", Toast.LENGTH_LONG).show();
                            }

                            wifi.setWifiEnabled(true);


                        }
                    }
                }
            }
        }
        catch(Exception e){
            Log.e("Error", e.getMessage());

        }
    }

    private WifiConfiguration prepareWifiConfig() {
        WifiConfiguration wc = new WifiConfiguration();

        wc.allowedAuthAlgorithms.clear();
        wc.allowedGroupCiphers.clear();
        wc.allowedKeyManagement.clear();
        wc.allowedPairwiseCiphers.clear();
        wc.allowedProtocols.clear();


        //wc.status = WifiConfiguration.Status.ENABLED;
        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wc.SSID = "\"" + AppSettings.SCHIPHOL_WIFI_SSID + "\"";
        wc.preSharedKey = "\"" + AppSettings.SCHIPHOL_WIFI_PASSWORD + "\"";
        wc.hiddenSSID = true;
        wc.priority = 1;

        return wc;
    }

    private WifiConfiguration findNetworkInExistingConfig(String ssid) {
        List<WifiConfiguration> existingConfigs = wifi.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals(ssid)) {
                return existingConfig;
            }
        }
        return null;
    }

}
