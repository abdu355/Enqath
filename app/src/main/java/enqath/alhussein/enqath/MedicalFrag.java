package enqath.alhussein.enqath;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MedicalFrag extends Fragment implements View.OnClickListener {
    View myView;
    static Button submit;
    protected AppCompatActivity mActivity;
    private myFragEventListerner listener;
    public static Spinner blood;
    private static EditText allergies;
    private static EditText currentCondition;
    private static EditText extraInfo;
    private static EditText medications;
    private static EditText primcontact;


    FirebaseUser firebaseUser;

    DatabaseReference myRef;

    Context context;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof myFragEventListerner) {
            listener = (myFragEventListerner) activity;
            context = getActivity();
        } else {
            // Throw an error!
            Log.d("FragmentEvent","Activity not attached to fragment");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.medicalfragv2,container,false);
        submit = (Button)myView.findViewById(R.id.btnsubmitmed);
        submit.setOnClickListener(this);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("first_time", false);
        editor.commit();

        blood=(Spinner)myView.findViewById(R.id.spinner_blood);
        allergies=(EditText)myView.findViewById(R.id.txt_allerg);
        currentCondition=(EditText)myView.findViewById(R.id.txt_currcond);
        extraInfo=(EditText)myView.findViewById(R.id.txt_extra);
        medications=(EditText)myView.findViewById(R.id.txt_med);
        primcontact=(EditText)myView.findViewById(R.id.primcontact);

        String bloodTypes[] = {"A+","A-","AB+","AB-","O+","O-","B+","B-"};

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, bloodTypes);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        blood.setAdapter(spinnerArrayAdapter);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        try {
            updateData();
        } catch (NullPointerException e) {
            Toast.makeText(getContext(),"User Error . Are you logged In ?",Toast.LENGTH_SHORT).show();
            listener.hideProgress();
        }

        blood.setEnabled(false);
        return myView;
    }
    public static void showEditButtons()
    {
        blood.setEnabled(true);
        allergies.setEnabled(true);
        currentCondition.setEnabled(true);
        extraInfo.setEnabled(true);
        medications.setEnabled(true);
        primcontact.setEnabled(true);



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
        new SweetAlertDialog(myView.getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Update Medical ID")
                .setConfirmText("Yes !")
                .setCancelText("Cancel")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog
                                .setTitleText("Done!")
                                .setContentText("Your Medical ID Has Been Updated!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .showCancelButton(false)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        // This is how you call method of Activity from Fragment.
                        listener.pushMedicalID(new MedID(blood.getSelectedItem().toString(),
                                allergies.getText().toString(),currentCondition.getText().toString(),
                                extraInfo.getText().toString(),medications.getText().toString()));
                        submit.setVisibility(View.GONE);
                    }
                })
                .show();
    }
    public void updateUI(String dbblood,String dballergies,String dbcurrentcondition,String dbextraInfo,String dbmedications)
    {
        blood.setSelection(getIndex(blood, dbblood));
        allergies.setText(dballergies);
        currentCondition.setText(dbcurrentcondition);
        extraInfo.setText(dbextraInfo);
        medications.setText(dbmedications);
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void updateData()
    {
        listener.showProgress();
        myRef.child("medID").child(firebaseUser.getUid()).addListenerForSingleValueEvent(
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
                        listener.hideProgress();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("getProfielDataFunction", "getUser:onCancelled", databaseError.toException());
                        listener.hideProgress();
                    }
                });
    }
}
