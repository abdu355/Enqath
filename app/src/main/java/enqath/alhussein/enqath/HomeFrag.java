package enqath.alhussein.enqath;


import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Abdulwahab on 7/3/2016.
 */
public class HomeFrag extends Fragment implements View.OnClickListener, LocationListener{
    View myView;
    GPSLoc GPSLocation;

    protected AppCompatActivity mActivity;
    private myFragEventListerner listener;
    private ImageButton btnTheft,btnCar,btnDrawn,btnFire;
    private Button btnEnqath;

    Animation animScale;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof myFragEventListerner) {
            listener = (myFragEventListerner) activity;
        } else {
            // Throw an error!
            Log.d("FragmentEvent","Activity not attached to fragment");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.homefrag,container,false);

        btnEnqath = (Button)myView.findViewById(R.id.btnEnqath);
        btnCar=(ImageButton)myView.findViewById(R.id.imageButton_crash);
        btnTheft=(ImageButton)myView.findViewById(R.id.imageButton_theft);
        btnDrawn=(ImageButton)myView.findViewById(R.id.imageButton_drown);
        btnFire=(ImageButton)myView.findViewById(R.id.imageButton_fire);

        animScale = AnimationUtils.loadAnimation(getContext(), R.anim.scale);


        /*TODO :: put all main buttons here, HomeFrag will now attach to Main.java*/
        btnEnqath.setOnClickListener(this);
        btnCar.setOnClickListener(this);
        btnTheft.setOnClickListener(this);
        btnDrawn.setOnClickListener(this);
        btnFire.setOnClickListener(this);


        return myView;
    }



    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnEnqath:
                quickcall(); //saves profile to Firebase

                break;
            case R.id.imageButton_fire:
                view.startAnimation(animScale);
                quickEmergency("Report Fire",new Intent(getContext(),Fire.class));
                break;
            case R.id.imageButton_theft:
                view.startAnimation(animScale);
                quickEmergency("Report Theft",new Intent(getContext(),Theft.class));
                break;
            case R.id.imageButton_crash:
                view.startAnimation(animScale);
                quickEmergency("Report Car Crash",new Intent(getContext(),Car.class));
                break;
            case R.id.imageButton_drown:
                view.startAnimation(animScale);
                quickEmergency("Report Drowning",new Intent(getContext(),Drowning.class));
                break;
        }
    }
    public void quickcall()
    {
        // This is how you call method of Activity from Fragment.
        listener.quickCall();
    }
    public void quickEmergency(String message, Intent intent)
    {
        listener.quickEmergency(message,intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        GPSLocation.setLat(location.getLatitude());
        GPSLocation.setLng(location.getLongitude());
      //  GPSLocation.setDate(System.get);

    }
/*****************************************************************
    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }
*/////////////////////////////////////*/


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
