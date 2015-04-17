package in.raveesh.atlas;

/**
 * Created by Raveesh on 17/04/15.
 */
public interface LocationListener extends android.location.LocationListener {
    public void timedOut();
    public void noProviderEnabled();
}
