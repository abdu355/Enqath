package enqath.alhussein.enqath;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Abdulwahab on 7/13/2016.
 */

public class GPSLoc {

    int accuracy;
   // Location latLng; // changed to location object .. i think it's better
    double Lat, Lng;
    Date date;
    long time;

    public GPSLoc()
    {

    }

    public double getLat(double latitude) {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng(double longitude) {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public GPSLoc(double lat, double lng, int accuracy, Date date, long time) {
        this.time=time;
        this.date=date;
        this.Lat=lat;
        this.Lng=lng;
        this.accuracy = accuracy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }





    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
