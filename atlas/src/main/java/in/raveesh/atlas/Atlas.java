package in.raveesh.atlas;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by Raveesh on 17/04/15.
 */
public class Atlas {
    private static boolean locationUpdateReceived = false;
    private static boolean timedOut = false;

    public static void getSingleUpdate(Context context, long timeOut, final LocationListener listener) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        boolean gps_enabled = false, network_enabled = false;
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (!gps_enabled && !network_enabled) {
            /**
             * No provider is enabled
             */
            listener.noProviderEnabled();
        } else {
            String provider = LocationManager.GPS_PROVIDER;
            if (network_enabled) {
                provider = LocationManager.NETWORK_PROVIDER;
            }
            /**
             * Starting to search for location
             */
            locationUpdateReceived = false;
            timedOut = false;

            final android.location.LocationListener standardListener = new android.location.LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    if (!timedOut) {
                        listener.onLocationChanged(location);
                        locationUpdateReceived = true;
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    if (!timedOut) {
                        listener.onStatusChanged(provider, status, extras);
                    }
                }

                @Override
                public void onProviderEnabled(String provider) {
                    if (!timedOut){
                        listener.onProviderEnabled(provider);
                    }

                }

                @Override
                public void onProviderDisabled(String provider) {
                    if (!timedOut){
                        listener.onProviderDisabled(provider);
                    }

                }
            };

            final android.os.Handler timeoutHandler = new android.os.Handler();
            Runnable timeoutRunnable = new Runnable() {
                @Override
                public void run() {
                    if (!locationUpdateReceived) {
                        /**
                         * Timed out
                         */
                        timedOut = true;
                        listener.timedOut();
                        locationManager.removeUpdates(standardListener);
                    } else {
                        /**
                         * Successfully retrieved within timeout
                         */
                    }
                }
            };
            timeoutHandler.removeCallbacks(timeoutRunnable);
            timeoutHandler.postDelayed(timeoutRunnable, timeOut);
            locationManager.requestSingleUpdate(provider, standardListener, null);
        }
    }
}
