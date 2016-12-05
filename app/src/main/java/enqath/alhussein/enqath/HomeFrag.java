package enqath.alhussein.enqath;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import tourguide.tourguide.ChainTourGuide;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Sequence;
import tourguide.tourguide.ToolTip;


public class HomeFrag extends Fragment implements View.OnClickListener{
    View myView;
    //IncidentDetails GPSLocation;

    protected AppCompatActivity mActivity;
    private myFragEventListerner listener;
    private ImageButton btnTheft,btnCar,btnDrawn,btnFire;
    private Button btnEnqath;
    private Animation mEnterAnimation, mExitAnimation;
    Animation animScale;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof myFragEventListerner) {
            listener = (myFragEventListerner) activity;
        } else {
            Log.d("FragmentEvent","Activity not attached to fragment");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.homefrag,container,false);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());


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

        mEnterAnimation = new AlphaAnimation(0f, 1f);
        mEnterAnimation.setDuration(600);
        mEnterAnimation.setFillAfter(true);

        mExitAnimation = new AlphaAnimation(1f, 0f);
        mExitAnimation.setDuration(600);
        mExitAnimation.setFillAfter(true);


        if(settings.getBoolean("first_time",false)) {
            runOverlay_ContinueMethod();
        }

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
    private void runOverlay_ContinueMethod(){
        // the return handler is used to manipulate the cleanup of all the tutorial elements
        ChainTourGuide tourGuide1 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("SOS")
                        .setDescription("Only use this for quick response, this button will alert the police, your emergency contacts, and send ur GPS location to all parties")
                        .setGravity(Gravity.BOTTOM)
                        .setBackgroundColor(Color.parseColor("#c0392b"))
                )
                .setOverlay(new Overlay()
                        .setBackgroundColor(Color.parseColor("#EE2c3e50"))
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                )
                // note that there is no Overlay here, so the default one will be used
                .playLater(btnEnqath);

        ChainTourGuide tourGuide2 = ChainTourGuide.init(getActivity())
                .setToolTip(new ToolTip()
                        .setTitle("Incident Report")
                        .setDescription("These buttons will allow you to report different incidents and an estimated injury level to the appropriate authorities")
                        .setGravity(Gravity.TOP | Gravity.RIGHT)
                )
                .setOverlay(new Overlay()
                        .setBackgroundColor(Color.parseColor("#EE2c3e50"))
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                )
                .playLater(btnFire);

        Sequence sequence = new Sequence.SequenceBuilder()
                .add(tourGuide1, tourGuide2)
                .setDefaultOverlay(new Overlay()
                        .setEnterAnimation(mEnterAnimation)
                        .setExitAnimation(mExitAnimation)
                )
                .setDefaultPointer(null)
                .setContinueMethod(Sequence.ContinueMethod.Overlay)
                .build();


        ChainTourGuide.init(getActivity()).playInSequence(sequence);
    }

}
