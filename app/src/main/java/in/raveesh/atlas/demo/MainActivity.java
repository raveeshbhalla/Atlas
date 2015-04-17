package in.raveesh.atlas.demo;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import java.util.Locale;

import in.raveesh.atlas.Atlas;
import in.raveesh.atlas.LocationListener;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView label = (TextView) findViewById(R.id.label);
        Atlas.getSingleUpdate(this, 15000, new LocationListener() {
            @Override
            public void timedOut() {
                label.setText("Location retrieval timed out");
            }

            @Override
            public void noProviderEnabled() {
                label.setText("No Provider Enabled");
            }

            @Override
            public void onLocationChanged(Location location) {
                label.setText(String.format(Locale.ENGLISH, "Location retrieved: %f, %f, from %s", location.getLatitude(), location.getLongitude(), location.getProvider()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                label.setText("Provider changed: " + provider);
            }

            @Override
            public void onProviderEnabled(String provider) {
                label.setText("Provider enabled: " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                label.setText("Provider disabled: " + provider);
            }
        });
    }
}
