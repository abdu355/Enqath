package enqath.alhussein.enqath;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class UserRegistration extends AppCompatActivity implements  View.OnClickListener, View.OnFocusChangeListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = "Firebase Authentication";
    Button email_sign_up_button;
    EditText emailreg, passreg;
    ProgressDialog progress;



    //profile
    ArrayList<String> countries;
    private static EditText txtDob;
    SimpleDateFormat dateFormat;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spinner;
    private DatePickerDialog datePickerDialog;
    private static EditText fname;
    private static EditText lname;
    private static EditText phone;
    private static EditText nID;

    //medical id
    public Spinner blood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailreg =(EditText)findViewById(R.id.emailreg);
        passreg=(EditText)findViewById(R.id.passwordreg);


        //---------------------------profile
        fname = (EditText) findViewById(R.id.txt_fname);
        lname = (EditText) findViewById(R.id.txt_lname);
        phone = (EditText) findViewById(R.id.txt_phone);
        nID = (EditText) findViewById(R.id.txt_nID);
        txtDob=(EditText)findViewById(R.id.txtDob);
        txtDob.setOnClickListener(this);
        txtDob.setOnFocusChangeListener(this);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        spinner = (com.toptoche.searchablespinnerlibrary.SearchableSpinner)findViewById(R.id.country);
        Locale[] locale = Locale.getAvailableLocales();
        countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        org.droidparts.adapter.widget.ArrayAdapter<String> adapter = new org.droidparts.adapter.widget.ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countries);
        spinner.setAdapter(adapter);
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        setDateTimeField();

        //---------------------------profile

        //---------------------------medID

        String bloodTypes[] = {"A+","A-","AB+","AB-","O+","O-","B+","B-"};

        // Application of the Array to the Spinner
        blood=(Spinner)findViewById(R.id.spinner_blood);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, bloodTypes);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        blood.setAdapter(spinnerArrayAdapter);

        //---------------------------medID

        email_sign_up_button = (Button)findViewById(R.id.email_sign_up_button);
        email_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Usersignup(emailreg.getText().toString(),passreg.getText().toString());
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(UserRegistration.this,Main.class));
                    //TODO hand rest of information here (medical and profile info)
                    //profile



                    //medID


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        // ...
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void Usersignup(String email, String password)
    {
        //showProgressDialog();
        progress = ProgressDialog.show(this, "Registering",
                "Almost Done...", true);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the firebaseUser. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in firebaseUser can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.d(TAG, "createUserWithEmail:onComplete:" + task.toString());
                            Toast.makeText(UserRegistration.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //hideProgressDialog();
                        progress.dismiss();
                        // ...
                    }
                });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(UserRegistration.this, new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtDob.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.txtDob:
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch(view.getId())
        {
            case R.id.txtDob:
                if(hasFocus)
                    datePickerDialog.show();
                break;
        }
    }
}
