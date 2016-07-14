package enqath.alhussein.enqath;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Preferences extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    TableRow R1, R2, R3, R4, R5;
    Spinner spinner;
    int RQS_PICK_CONTACT = 1;
    EditText txtP1, txtP2, txtP3, txtP4, txtP5;
    Button save;
    //String[] phoneNo ={"0","0","0","0","0"};
    int contactPointer = 0;
    EmergencyContacts emgPhone=new EmergencyContacts();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseUser firebaseUser;
    FirebaseFunctions firebaseFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(this);

        txtP1 = (EditText) findViewById(R.id.txtPhone1);
        txtP2 = (EditText) findViewById(R.id.txtPhone2);
        txtP3 = (EditText) findViewById(R.id.txtPhone3);
        txtP4 = (EditText) findViewById(R.id.txtPhone4);
        txtP5 = (EditText) findViewById(R.id.txtPhone5);

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
                pushContacts(emgPhone);

                break;
        }
    }
public void pushContacts(EmergencyContacts emgPhone){

    firebaseFunctions.pushContacts(emgPhone);
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


