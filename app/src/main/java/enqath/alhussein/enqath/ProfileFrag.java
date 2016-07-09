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
import android.widget.Spinner;

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
public class ProfileFrag extends Fragment implements View.OnClickListener {
    View myView;
    static Button submit;
    protected AppCompatActivity mActivity;
    private myFragEventListerner listener;
    private EditText fname,lname,phone,nID,dob,nat;
    private Spinner blood;
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
        myView = inflater.inflate(R.layout.profilefrag,container,false);
        submit = (Button)myView.findViewById(R.id.btnSubmit);
        submit.setOnClickListener(this);


        //text fields
        fname = (EditText)myView.findViewById(R.id.txt_fname);
        lname = (EditText)myView.findViewById(R.id.txt_lname);
        phone = (EditText)myView.findViewById(R.id.txt_phone);
        nID = (EditText)myView.findViewById(R.id.txt_nID);
        dob = (EditText)myView.findViewById(R.id.txt_dob);
        nat = (EditText)myView.findViewById(R.id.txt_nat);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        listener.showProgress();
        myRef.child("profiles").child(firebaseUser.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get userProfile value
                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                        try {
                            updateUI(userProfile.getFname(),userProfile.getLname(),userProfile.getPhone(),userProfile.getDob(),userProfile.getnID(),userProfile.getNat());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        // ...
                        listener.hideProgress();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("getProfileDataFunction", "getUser:onCancelled", databaseError.toException());
                        listener.hideProgress();
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
            case R.id.btnSubmit:
                pushUserProfile(); //saves profile to Firebase
                break;
        }
    }
    public void pushUserProfile()
    {
        // This is how you call method of Activity from Fragment.
        listener.pushUserProfile(new UserProfile(fname.getText().toString(),
                lname.getText().toString(),phone.getText().toString(),
                dob.getText().toString(),nID.getText().toString(),nat.getText().toString()));
    }

    //------------------------------------------------------------------------------------

    public void updateUI(String dbfname,String dblname,String dbphone,String dbdob,String dbnID,String dbnat)
    {
        fname.setText(dbfname);
        lname.setText(dblname);
        phone.setText(dbphone);
        nID.setText(dbnID);
        dob.setText(dbdob);
        nat.setText(dbnat);
    }
}
