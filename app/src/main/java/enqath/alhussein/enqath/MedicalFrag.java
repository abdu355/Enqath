package enqath.alhussein.enqath;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Abdulwahab on 7/3/2016.
 */
public class MedicalFrag extends Fragment implements View.OnClickListener {
    View myView;
    static Button submit;
    protected AppCompatActivity mActivity;
    private myFragEventListerner listener;

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
        myView = inflater.inflate(R.layout.medicalfrag,container,false);
        submit = (Button)myView.findViewById(R.id.btnsubmitmed);
        submit.setOnClickListener(this);


        return myView;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnsubmitmed:
                updateMedicalID(); //saves profile to Firebase
                break;
        }
    }
    public void updateMedicalID()
    {
        // This is how you call method of Activity from Fragment.
        listener.updateMedicalID();
    }
    public static void showEditButtons()
    {
        submit.setVisibility(View.VISIBLE);
    }
}
