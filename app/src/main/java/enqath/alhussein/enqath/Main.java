package enqath.alhussein.enqath;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Locale;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,myFragEventListerner, LocationListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView useremail_nav,username_nav;
    private ImageView userdp_nav;
    String userdisplayname;
    FirebaseUser firebaseUser;
    FirebaseFunctions firebaseFunctions;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ProgressBar progressBar;

    int Lat,Lng,accurcy;


    FrameLayout layout;
    final private int REQUEST_CODE_CALL = 124;
    DrawerLayout drawer;
    FloatingActionButton fab;
    android.support.v4.app.Fragment fragment_obj;
    AppBarLayout appBarLayout;



    String [] phones;
    String [] msg={"Car Accident Level Minor. No Human Injuries","Car Accident Level Medium. Simple Human Injuries","Car Accident Level Serious. Major Human Injuries"};

    final String number = "tel:000";
    //android.app.FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progress_spinner);



        fab = (FloatingActionButton) findViewById(R.id.fab);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        appBarLayout =(AppBarLayout)findViewById(R.id.app_bar);
        fab.setImageResource(R.drawable.ic_phone_white_48dp); //call FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Open Dialer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(Intent.ACTION_DIAL); //open dialer
                intent.setData(Uri.parse("tel:000"));
                startActivity(intent);
            }
        });



        /*TODO :: moved to HomeFrag.java*/
//        btnCar.setOnClickListener(this);
//        btnTheft.setOnClickListener(this);
//        btnDrawn.setOnClickListener(this);
//        btnFire.setOnClickListener(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //noinspection deprecation
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //change orientation when changing language
        if(Locale.getDefault().getLanguage().equals("en")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);
        useremail_nav = (TextView) navHeaderView.findViewById(R.id.useremail_nav);
        username_nav=(TextView)navHeaderView.findViewById(R.id.username_nav);
        //--------------------------

        //btnEnqath = (Button) findViewById(R.id.btnEnqath);
        layout = (FrameLayout)findViewById(R.id.container);
        userdp_nav=(ImageView)findViewById(R.id.userdp_nav);


        firebaseFunctions = new FirebaseFunctions();
        Firebase.setAndroidContext(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            // User is signed in
            Snackbar snackbar = Snackbar.make(drawer, "WELCOME " + firebaseUser.getDisplayName(), Snackbar.LENGTH_SHORT);
            snackbar.show();
            //get firebaseUser profile and display in nav drawer
            useremail_nav.setText(firebaseUser.getEmail());
            username_nav.setText(firebaseUser.getDisplayName());
            //set DP image here

            if(username_nav.getText().length()<=0) //if no username or alias yet
            {
                showdialog();
            }
        } else {
            // No firebaseUser is signed in
            Toast.makeText(Main.this,"GUEST MODE",Toast.LENGTH_LONG).show();
        }

        Toast.makeText(Main.this,Locale.getDefault().getLanguage(),Toast.LENGTH_LONG).show();

        //start app with home fragment
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFrag());
        transaction.addToBackStack(null);
        transaction.commit();

    }
    public void DynamicPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE) && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an expanation to the User *asynchronously* -- don't block
                // this thread waiting for the User's response! After the User
                // sees the explanation, try again to request the permission.
                Snackbar snackbar = Snackbar
                        .make(drawer, "Requires phone permission to make Emergency Calls", Snackbar.LENGTH_LONG)
                        .setAction("Allow", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(drawer, "Requesting Access", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                                ActivityCompat.requestPermissions(Main.this,
                                        new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS},
                                        REQUEST_CODE_CALL);
                            }
                        });

                snackbar.show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS},
                        REQUEST_CODE_CALL);

            }
        }
        else
        {
            Toast.makeText(this,"Calling...",Toast.LENGTH_SHORT).show();
            Uri callui = Uri.parse(number);
            Intent callIntent = new Intent(Intent.ACTION_CALL, callui);
            //noinspection MissingPermission
            startActivity(callIntent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // calling-related task you need to do.
                    Toast.makeText(this,"Calling...",Toast.LENGTH_SHORT).show();
                    Uri callui = Uri.parse(number);
                    Intent callIntent = new Intent(Intent.ACTION_CALL, callui);
                    //noinspection MissingPermission
                    startActivity(callIntent);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Snackbar snackbar1 = Snackbar.make(drawer, "Permission Denied", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, Preferences.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainLogin.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        transaction = getSupportFragmentManager().beginTransaction();


        if (id == R.id.nav_home) {
            transaction.replace(R.id.container, new HomeFrag());
            transaction.addToBackStack(null);
            transaction.commit();
            //FAB
            collapsingToolbarLayout.setTitle("Enqath");
            fab.setImageResource(R.drawable.ic_phone_white_48dp); //call FAB
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Open Dialer", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(Intent.ACTION_DIAL); //open dialer
                    intent.setData(Uri.parse("tel:000"));
                    startActivity(intent);
                }
            });
        } else if (id == R.id.nav_profilepage) {
            transaction.replace(R.id.container, new ProfileFrag());
            transaction.addToBackStack(null);
            transaction.commit();
            //FAB
            collapsingToolbarLayout.setTitle("User Profile");//change appbar title
            fab.setImageResource(R.drawable.ic_mode_edit_white_48dp); //call FAB
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Edit profile", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    ProfileFrag.showEditButtons();//show edit buttons
                    appBarLayout.setExpanded(false,true); //hide appbar
                }
            });
        } else if (id == R.id.nav_medicalpage) {
            transaction.replace(R.id.container, new MedicalFrag());
            transaction.addToBackStack(null);
            transaction.commit();
            //FAB
            collapsingToolbarLayout.setTitle("Medical ID"); //change appbar title
            fab.setImageResource(R.drawable.ic_mode_edit_white_48dp); //call FAB
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Edit Medical ID", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    MedicalFrag.showEditButtons(); //show edit buttons
                    appBarLayout.setExpanded(false,true); //hide appbar
                }
            });
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /*TODO:: moved to HomeFrag.java*/
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnEnqath:
//                DynamicPermission(); //fixed permission request logic
//                break;
//            case R.id.btnFire:
//                //    startActivity(callIntent);
//                // startActivity(new Intent(this,Fire.class));
//
//                break;
//            case R.id.btnCar:
//                //    startActivity(callIntent);
//
//                break;
//            case R.id.btnTheft:
//                //     startActivity(callIntent);
//
//                break;
//            case R.id.btnDrawn:
//                //     startActivity(callIntent);
//
//                break;
//
//
//        }
//    }
    public void sendSMS(String [] phoneNo, String [] msg){
        try {


            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo[0], null, msg[0], null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    private void showdialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.CustomDialogTheme);
        final EditText edittext = new EditText(getApplicationContext());
        edittext.setTextColor(Color.parseColor("#f57f17"));
        alert.setMessage("Enter Alias or Name");
        alert.setTitle("Profile Setup");

        alert.setView(edittext);

        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                userdisplayname = edittext.getText().toString();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(userdisplayname)
                        .build();

                firebaseUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Firebase Profile", "User profile updated.");
                                }
                            }
                        });
            }
        });

        alert.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }
    private void showAlert()
    {
        new AlertDialog.Builder(this)
                .setTitle("User Error")
                .setMessage("Error Occurred , are you logged in ?")
                .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //close
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /*<---------------------------------------------/*TODO :: Implement fragment functions here *///--------------------------------------------->
    @Override
    public void pushUserProfile(UserProfile userProfile) //profile fragment
    {
        Log.d("Main Activity Event","updateUser called");
        //push to Firebase
        try {
            firebaseFunctions.pushProfileData(userProfile,firebaseUser.getUid());
            //REFERENCE ONLY: String fname, String lname, String phone, String dob, String nID, String nat
        } catch (NullPointerException e) {
            showAlert();
        }
        //confirm
        Snackbar snackbar = Snackbar.make(drawer, "Your profile has been saved", Snackbar.LENGTH_SHORT);
        snackbar.show();

    }
    @Override
    public void pushMedicalID(MedID medID)//medical ID fragment
    {
        Log.d("Main Activity Event","pushMedicalID called");
       //push to Firebase
        //REFERENCE ONLY: String blood, String allergies, String currentCondition, String extraInfo, String medications
        try {
            firebaseFunctions.pushMedID(medID,firebaseUser.getUid());
        } catch (NullPointerException e) {
            showAlert();
        }
        //confirm
        Snackbar snackbar = Snackbar.make(drawer, "Your Medical ID has been updated", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void quickCall() //home fragment
    {
        DynamicPermission(); //checks permission and initiates call
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void quickEmergency(String message, Intent intent) {
        Snackbar snackbar1 = Snackbar.make(drawer, message, Snackbar.LENGTH_SHORT);
        snackbar1.show();
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        Lat=(int)location.getLatitude();
        Lng=(int)location.getLongitude();
        accurcy=(int)location.getAccuracy();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
