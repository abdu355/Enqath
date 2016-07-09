package enqath.alhussein.enqath;

import android.content.Intent;

/**
 * Created by Abdulwahab on 7/6/2016.
 */
public interface myFragEventListerner {
    //push methods
    public void pushUserProfile(UserProfile userProfile);
    public void pushMedicalID(MedID medID);
    public void quickCall();

    //other
    public void showProgress();
    public void hideProgress();
    public void quickEmergency(String message, Intent intent);
}
