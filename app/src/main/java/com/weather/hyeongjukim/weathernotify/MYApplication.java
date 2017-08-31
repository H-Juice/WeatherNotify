package com.weather.hyeongjukim.weathernotify;

import android.app.Application;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

/**
 * Created by hyeongjukim on 2017. 9. 1..
 */

public class MYApplication extends Application {
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){

            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitoring region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 40259, 11605));


            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                Log.d("beacon","get in region"+ list.get(0).getRssi());
                if(list.get(0).getRssi() > -70){
                    beaconManager.stopMonitoring(region);
                }
            }

            @Override
            public void onExitedRegion(Region region) {
                Log.d("beacon", "get out region");
            }
        });


    }
}
