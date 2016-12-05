package enqath.alhussein.enqath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Preferences extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    TableRow R1, R2, R3, R4, R5;
    Spinner spinner;
    int RQS_PICK_CONTACT = 1;
    org.droidparts.widget.ClearableEditText txtP1, txtP2, txtP3, txtP4, txtP5;
    Button save;
    ProgressBar progressBar;
    //String[] phoneNo ={"0","0","0","0","0"};
    int contactPointer = 0;
    EmergencyContacts emgPhone=new EmergencyContacts();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseUser firebaseUser;
    FirebaseFunctions firebaseFunctions;

    DatabaseReference myRef;
    int numofcontacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("first_time", false);
        editor.commit();

        progressBar = (ProgressBar) findViewById(R.id.progress_spinner);

        save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(this);

        txtP1 = (org.droidparts.widget.ClearableEditText) findViewById(R.id.txtPhone1);
        txtP2 = (org.droidparts.widget.ClearableEditText) findViewById(R.id.txtPhone2);
        txtP3 = (org.droidparts.widget.ClearableEditText) findViewById(R.id.txtPhone3);
        txtP4 = (org.droidparts.widget.ClearableEditText) findViewById(R.id.txtPhone4);
        txtP5 = (org.droidparts.widget.ClearableEditText) findViewById(R.id.txtPhone5);

        txtP1.setOnClickListener(this);
        txtP2.setOnClickListener(this);
        txtP3.setOnClickListener(this);
        txtP4.setOnClickListener(this);
        txtP5.setOnClickListener(this);


        R1 = (TableRow) findViewById(R.id.R1);
        R2 = (TableRow) findViewById(R.id.R2);
        R3 = (TableRow) findViewById(R.id.R3);
        R4 = (TableRow) findViewById(R.id.R4);
        R5 = (TableRow) findViewById(R.id.R5);
        spinner = (Spinner) findViewById(R.id.spinner);
        R1.setVisibility(View.VISIBLE);
        R2.setVisibility(View.GONE);
        R3.setVisibility(View.GONE);
        R4.setVisibility(View.GONE);
        R5.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.Contacts,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        firebaseFunctions = new FirebaseFunctions();
        Firebase.setAndroidContext(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            // User is signed in

        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        updateData();//get numbers from Firebase

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numofcontacts=position+1;
        if (spinner.getSelectedItemPosition() == 0) {
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.GONE);
            R3.setVisibility(View.GONE);
            R4.setVisibility(View.GONE);
            R5.setVisibility(View.GONE);

        }
        if (spinner.getSelectedItemPosition() == 1) {
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.VISIBLE);
            R3.setVisibility(View.GONE);
            R4.setVisibility(View.GONE);
            R5.setVisibility(View.GONE);

        }
        if (spinner.getSelectedItemPosition() == 2) {
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.VISIBLE);
            R3.setVisibility(View.VISIBLE);
            R4.setVisibility(View.GONE);
            R5.setVisibility(View.GONE);

        }
        if (spinner.getSelectedItemPosition() == 3) {
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.VISIBLE);
            R3.setVisibility(View.VISIBLE);
            R4.setVisibility(View.VISIBLE);
            R5.setVisibility(View.GONE);

        }
        if (spinner.getSelectedItemPosition() == 4) {
            R1.setVisibility(View.VISIBLE);
            R2.setVisibility(View.VISIBLE);
            R3.setVisibility(View.VISIBLE);
            R4.setVisibility(View.VISIBLE);
            R5.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        R1.setVisibility(View.VISIBLE);
        R2.setVisibility(View.GONE);
        R3.setVisibility(View.GONE);
        R4.setVisibility(View.GONE);
        R5.setVisibility(View.GONE);


    }

    public void MyMagicContact() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtPhone1:
                contactPointer = 0;

                emgPhone.setNum1(txtP1.getText().toString());
                MyMagicContact();
                break;

            case R.id.txtPhone2:
                contactPointer = 1;
                emgPhone.setNum2(txtP1.getText().toString());
                MyMagicContact();


                break;

            case R.id.txtPhone3:
                contactPointer = 2;
                emgPhone.setNum3(txtP1.getText().toString());
                MyMagicContact();


                break;

            case R.id.txtPhone4:
                contactPointer = 3;


                emgPhone.setNum4(txtP1.getText().toString());
                MyMagicContact();

                break;

            case R.id.txtPhone5:
                contactPointer = 4;
                emgPhone.setNum5(txtP1.getText().toString());
                MyMagicContact();


                break;
            case R.id.btnSave:
                emgPhone.setNum1(txtP1.getText().toString());
                emgPhone.setNum2(txtP2.getText().toString());
                emgPhone.setNum3(txtP3.getText().toString());
                emgPhone.setNum4(txtP4.getText().toString());
                emgPhone.setNum5(txtP5.getText().toString());
                emgPhone.setNumofcontacts(numofcontacts);
                pushContacts(emgPhone);

                View parentLayout = findViewById(R.id.rootpref);
                Snackbar snackbar = Snackbar.make(parentLayout, "Your contacts has been saved", Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;
        }
    }
    public void pushContacts(EmergencyContacts emgPhone){

        firebaseFunctions.pushContacts(emgPhone, firebaseUser.getUid());
    }

    private void updateData()
    {
        progressBar.setVisibility(View.VISIBLE);
        myRef.child("emergencyContacts").child(firebaseUser.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get userProfile value
                        EmergencyContacts emergencyContacts = dataSnapshot.getValue(EmergencyContacts.class);

                        try {
                            updateUI(emergencyContacts.getNum1(),emergencyContacts.getNum2(),emergencyContacts.getNum3(),emergencyContacts.getNum4(),emergencyContacts.getNum5(),emergencyContacts.getNumofcontacts());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        // ...

                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("getProfielDataFunction", "getUser:onCancelled", databaseError.toException());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    public void updateUI(String dbtxtP1,String dbtxtP2,String dbtxtP3,String dbtxtP4,String dbtxtP5,int numofcont)
    {
        txtP1.setText(dbtxtP1);
        txtP2.setText(dbtxtP2);
        txtP3.setText(dbtxtP3);
        txtP4.setText(dbtxtP4);
        txtP5.setText(dbtxtP5);

        //spinner
        int item_postion= numofcont-1;// item which you want to click
        spinner.setSelection(item_postion, true);
        View item_view = (View)spinner.getChildAt(item_postion);
        long item_id = spinner.getAdapter().getItemId(item_postion);
        spinner.performItemClick(item_view, item_postion, item_id);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RQS_PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //contactName.setText(name);
                if (contactPointer == 0) {
                    emgPhone.setNum1(number);
                    txtP1.setText(number);

                } else if (contactPointer == 1) {
                    emgPhone.setNum2(number);
                    txtP2.setText(number);

                } else if (contactPointer == 2) {
                    emgPhone.setNum3(number);
                    txtP3.setText(number);
                }

                else if (contactPointer == 3) {
                    emgPhone.setNum4(number);
                    txtP4.setText(number);
                }
                else if (contactPointer == 4) {
                    emgPhone.setNum5(number);
                    txtP5.setText(number);
                    //contactEmail.setText(email);}
                }
            }

        }
    }
}


