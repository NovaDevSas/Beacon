Beacon2/EntelPOCBeacon2/cordova-plugin-beacon/src/android/BeaconPlugin.java
package com.example.beacon;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;

public class BeaconPlugin extends CordovaPlugin implements BeaconConsumer {
    private BeaconManager beaconManager;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("startScanning")) {
            this.startScanning(callbackContext);
            return true;
        }
        return false;
    }

    private void startScanning(CallbackContext callbackContext) {
        beaconManager = BeaconManager.getInstanceForApplication(this.cordova.getActivity().getApplicationContext());
        beaconManager.bind(this);
        callbackContext.success("Scanning started");
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier((beacons, region) -> {
            for (Beacon beacon : beacons) {
                // Handle detected beacons
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRegion", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}