# Atlas
A quick and easy way to get location updates in your Android app, with an in-built timeout

In it's current form, Atlas works on top of locationManager.requestSingleUpdate, providing you with the ability to specify a time out and a callback if neither the GPS nor the Network operators are enabled.

To best illustrate it's usage, view the demo application. The code is pasted below.

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

# How to add Atlas to your project
Simply add the following lines to your build.gradle

    repositories {
        maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    }

    dependencies {
        compile 'in.raveesh:atlas:0.1.0-SNAPSHOT'
    }
