package enqath.alhussein.enqath;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


public class incidentDetails {


    private float accuracy;
    private double Lat, Lng;
    private String date;
    private String incidentType;
    private String severity;

    public incidentDetails() {}

    public incidentDetails(double lat, double lng, float accuracy, String date, String incidentType, String severity) {
        this.date=date;
        this.Lat=lat;
        this.Lng=lng;
        this.accuracy = accuracy;

        this.severity=severity;
        this.incidentType=incidentType;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "Incident["+
                "accuracy:" + accuracy +
                ", Lat:" + Lat +
                ", Lng:" + Lng +
                ", date:" + date +
                ", incidentType:" + incidentType+
                ", severity:" + severity +"]";
    }
}
