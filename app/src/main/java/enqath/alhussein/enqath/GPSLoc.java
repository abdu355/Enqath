package enqath.alhussein.enqath;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Abdulwahab on 7/13/2016.
 */

public class GPSLoc {

    int accuracy;
    LatLng latLng;
    Date date;
    long time;

    public GPSLoc()
    {

    }

    public GPSLoc(int lat, int lng, int accuracy,Date date,long time) {
        this.time=time;
        this.date=date;
        latLng = new LatLng(lat,lng);
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

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
