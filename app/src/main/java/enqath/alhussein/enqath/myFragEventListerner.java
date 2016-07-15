package enqath.alhussein.enqath;

import android.content.Intent;

/**
 * Created by Abdulwahab on 7/6/2016.
 */
public interface myFragEventListerner {
    //push methods
    public void pushUserProfile(UserProfile userProfile);
    public void pushMedicalID(MedID medID);
    // public void pushIncident(IncidentDetails gpsLoc);
    public void quickCall();
    public void getLocation();
    public void pushContacts(String phoneNo1,String phoneNo2,String phoneNo3,String phoneNo4,String phoneNo5);

    //other
    public void showProgress();
    public void hideProgress();
    public void quickEmergency(String message, Intent intent);
}
