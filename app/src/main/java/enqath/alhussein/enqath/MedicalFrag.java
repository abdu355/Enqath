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
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Abdulwahab on 7/3/2016.
 */
public class MedicalFrag extends Fragment implements View.OnClickListener {
    View myView;
    static Button submit;
    protected AppCompatActivity mActivity;
    private myFragEventListerner listener;
    public EditText blood;
    public EditText allergies;
    public EditText currentCondition;
    public EditText extraInfo;
    public EditText medications;
    FirebaseUser firebaseUser;

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("profiles").child(firebaseUser.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get userProfile value
                        MedID usermedID = dataSnapshot.getValue(MedID.class);

                        try {
                            updateUI(usermedID.getBlood(),usermedID.getAllergies(),usermedID.getCurrentCondition(),usermedID.getExtraInfo(),usermedID.getMedications());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("getProfielDataFunction", "getUser:onCancelled", databaseError.toException());
                    }
                });

        return myView;
    }
    public static void showEditButtons()
    {
        submit.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnsubmitmed:
                pushMedicalID(); //saves profile to Firebase
                break;
        }
    }
    public void pushMedicalID()
    {
        // This is how you call method of Activity from Fragment.
        listener.pushMedicalID();
    }
    public void updateUI(String dbblood,String dballergies,String dbcurrentcondition,String dbextraInfo,String dbmedications)
    {
        blood.setText(dbblood);
        allergies.setText(dballergies);
        currentCondition.setText(dbcurrentcondition);
        extraInfo.setText(dbextraInfo);
        medications.setText(dbmedications);

    }
}
