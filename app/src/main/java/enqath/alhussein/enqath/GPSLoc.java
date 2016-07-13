package enqath.alhussein.enqath;

/**
 * Created by Abdulwahab on 7/13/2016.
 */
public class GPSLoc {

    int Lat;
    int Lng;
    int accuracy;

    public GPSLoc()
    {

    }

    public GPSLoc(int lat, int lng, int accuracy) {
        Lat = lat;
        Lng = lng;
        this.accuracy = accuracy;
    }


    public int getLat() {
        return Lat;
    }

    public void setLat(int lat) {
        Lat = lat;
    }

    public int getLng() {
        return Lng;
    }

    public void setLng(int lng) {
        Lng = lng;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
