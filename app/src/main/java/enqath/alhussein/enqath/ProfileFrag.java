package enqath.alhussein.enqath;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ProfileFrag extends Fragment implements View.OnClickListener {
    View myView;
    Context context;
    static Button submit;
    protected AppCompatActivity mActivity;
    private myFragEventListerner listener;
    private EditText fname,lname,phone,nID,dob,nat;
    private Spinner blood;
    FirebaseUser firebaseUser;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    DatabaseReference myRef;

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
        dateView = (TextView) myView.findViewById(R.id.DOB);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        datePicker=(DatePicker)myView.findViewById(R.id.datePicker);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        //setCurrentDateOnView(); //set current date
        //text fields
        fname = (EditText)myView.findViewById(R.id.txt_fname);
        lname = (EditText)myView.findViewById(R.id.txt_lname);
        phone = (EditText)myView.findViewById(R.id.txt_phone);
        nID = (EditText)myView.findViewById(R.id.txt_nID);
        //dob = (EditText)myView.findViewById(R.id.txt_dob);
        nat = (EditText)myView.findViewById(R.id.txt_nat);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        try {
            updateData();
        } catch (NullPointerException e) {
            Toast.makeText(getContext(),"User Error . Are you logged In ?",Toast.LENGTH_SHORT).show();
            listener.hideProgress();
        }

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

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        //showDialog(999);
        Toast.makeText(context, "ca", Toast.LENGTH_SHORT)
                .show();
    }


    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(context, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private void updateData()
    {
        //---------------------------------------------------- Update from Firebase

        listener.showProgress();//change child name
        myRef.child("profiles").child(firebaseUser.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get userProfile value
                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class); //change this
                        try {
                            //What to do with the new data
                            updateUI(userProfile.getFname(),userProfile.getLname(),userProfile.getPhone(),userProfile.getDob(),userProfile.getnID(),userProfile.getNat());//change this
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        // ...
                        listener.hideProgress();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("getProfileDataFunction", "getUser:onCancelled", databaseError.toException());
                        //What to do on error
                        listener.hideProgress();
                    }
                });
        //---------------------------------------------------- Update from Firebase
    }


}
