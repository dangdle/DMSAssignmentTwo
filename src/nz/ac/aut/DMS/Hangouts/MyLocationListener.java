package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ashellan Edmonds on 8/05/15.
 */
class MyLocationListener implements LocationListener {
    public static Address address;
    public static Location lastKnownLocation;
    public final Geocoder geocoder;

    MyLocationListener(Activity activity) {
        geocoder = new Geocoder(activity, Locale.ENGLISH);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
            lastKnownLocation = location;
        try {

            List<Address> fromLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (Geocoder.isPresent()) {
                address = fromLocation.get(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
