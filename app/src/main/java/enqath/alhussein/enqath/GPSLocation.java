package enqath.alhussein.enqath;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammad on 7/11/2016.
 */


public class GPSLocation extends Activity
                implements GoogleApiClient.ConnectionCallbacks,
                GoogleApiClient.OnConnectionFailedListener,LocationListener {

private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gps);

    /********** get Gps location service LocationManager object ***********/
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                /* CAL METHOD requestLocationUpdates */

    // Parameters :
    //   First(provider)    :  the name of the provider with which to register
    //   Second(minTime)    :  the minimum time interval for notifications,
    //                         in milliseconds. This field is only used as a hint
    //                         to conserve power, and actual time between location
    //                         updates may be greater or lesser than this value.
    //   Third(minDistance) :  the minimum distance interval for notifications, in meters
    //   Fourth(listener)   :  a {#link LocationListener} whose onLocationChanged(Location)
    //                         method will be called for each location update


    locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,3000,10, this);

    /********* After registration onLocationChanged method  ********/
    /********* called periodically after each 3 sec ***********/
}

    /************* Called after each 3 sec **********/
    @Override
    public void onLocationChanged(Location location) {

        String str = "Latitude: "+location.getLatitude()+"Longitude: "+location.getLongitude();

        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

        /******** Called when User off Gps *********/

        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        /******** Called when User on Gps  *********/

        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
