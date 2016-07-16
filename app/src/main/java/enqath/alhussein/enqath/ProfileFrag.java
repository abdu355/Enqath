package enqath.alhussein.enqath;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.Locale;
import android.view.LayoutInflater;
import android.view.Menu;

import java.util.Collections;
import android.view.View;
import java.util.AbstractList;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import 	java.util.ArrayList;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.droidparts.adapter.widget.ArrayAdapter;

import java.util.Calendar;

public class ProfileFrag extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    View myView;
    final String default_country="Saudi Arabia";

    ArrayList<String> countries = new ArrayList<String>();

    private EditText txtDob;
    static Button submit;
    SimpleDateFormat dateFormat;
    protected AppCompatActivity mActivity;
    private myFragEventListerner listener;
    private EditText fname, lname, phone, nID, dob, nat;
    private Spinner spinner;
    FirebaseUser firebaseUser;
    private Calendar calendar;
    Calendar c = Calendar.getInstance();

    int spinnerPosition=0;
    private DatePickerDialog datePickerDialog;
    DatabaseReference myRef;


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof myFragEventListerner) {
            listener = (myFragEventListerner) activity;

        } else {
            // Throw an error!
            Log.d("FragmentEvent", "Activity not attached to fragment");
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profilefrag, container, false);
        submit = (Button) myView.findViewById(R.id.btnSubmit);
        submit.setOnClickListener(this);
/////////
   /*     spinner = (Spinner) myView.findViewById(R.id.country);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countries);
        spinner.setAdapter(adapter);
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
*/


        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        fname = (EditText) myView.findViewById(R.id.txt_fname);
        lname = (EditText) myView.findViewById(R.id.txt_lname);
        phone = (EditText) myView.findViewById(R.id.txt_phone);
        nID = (EditText) myView.findViewById(R.id.txt_nID);
        txtDob=(EditText)myView.findViewById(R.id.txtDob);
        nat=(EditText)myView.findViewById(R.id.txtNat);
        txtDob.setOnClickListener(this);
        //////////////////////////////////////////
    /*    Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        //spinner.setOnItemClickListener(myView);*/


        ///////


        ///////////////
        setDateTimeField();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        try {
            updateData();
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "User Error . Are you logged In ?", Toast.LENGTH_SHORT).show();
            listener.hideProgress();
        }

        return myView;
    }


    public static void showEditButtons() {
        submit.setVisibility(View.VISIBLE);
    }
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtDob.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnSubmit:
                        pushUserProfile(); //saves profile to Firebase
                        break;
                    case R.id.txtDob:
                     datePickerDialog.show();
                        break;
                }

            }

            public void pushUserProfile() {
                // This is how you call method of Activity from Fragment.
                listener.pushUserProfile(new UserProfile(
                        fname.getText().toString(),
                        lname.getText().toString(),
                        phone.getText().toString(),
                        txtDob.getText().toString(),
                        nID.getText().toString(),
             //           spinner.getSelectedItem().toString()));

                       nat.getText().toString()));
            }

            //------------------------------------------------------------------------------------

            public void updateUI(String dbfname, String dblname, String dbphone, String dbdob, String dbnID, String dbnat) {
                fname.setText(dbfname);
                lname.setText(dblname);
                phone.setText(dbphone);
                nID.setText(dbnID);
                txtDob.setText(dbdob);

             //   //spinner
         //       int item_postion= numofcont-1;// item which you want to click
         //       spinner.setSelection(item_postion, true);
         //       View item_view = (View)spinner.getChildAt(item_postion);
         //       long item_id = spinner.getAdapter().getItemId(item_postion);
         //       spinner.performItemClick(item_view, item_postion, item_id);


                nat.setText(dbnat);
            }






            private void updateData() {
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
                                    updateUI(userProfile.getFname(), userProfile.getLname(), userProfile.getPhone(), userProfile.getDob(), userProfile.getnID(), userProfile.getNat());//change this
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    //    spinner.setSelection(i);
      //  spinnerPosition=i;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

